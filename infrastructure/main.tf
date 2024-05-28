provider "aws" {
  region = "us-west-2"
}

provider "kubernetes" {
  host                   = module.eks.cluster_endpoint
  cluster_ca_certificate = base64decode(module.eks.cluster_certificate_authority_data)
  token                  = data.aws_eks_cluster_auth.cluster_auth.token
}

provider "helm" {
  kubernetes {
    host                   = module.eks.cluster_endpoint
    cluster_ca_certificate = base64decode(module.eks.cluster_certificate_authority_data)
    token                  = data.aws_eks_cluster_auth.cluster_auth.token
  }
}

data "aws_caller_identity" "current" {}
# VPC
module "vpc" {
  source  = "terraform-aws-modules/vpc/aws"
  version = "5.8.1"

  name = "nfl-vpc"
  cidr = "10.0.0.0/16"

  azs             = ["us-west-2a", "us-west-2b", "us-west-2c"]
  private_subnets = ["10.0.1.0/24", "10.0.2.0/24", "10.0.3.0/24"]
  public_subnets  = ["10.0.101.0/24", "10.0.102.0/24", "10.0.103.0/24"]

  enable_nat_gateway   = true
  single_nat_gateway   = true
  enable_dns_hostnames = true

  private_subnet_tags = {
    "kubernetes.io/role/internal-elb" = "1"
  }

  public_subnet_tags = {
    "kubernetes.io/role/elb" = "1"
  }
}

resource "aws_security_group" "worker_group_mgmt_one" {
  name_prefix = "worker_group_mgmt_one"
  vpc_id      = module.vpc.vpc_id

  ingress {
    from_port = 22
    to_port   = 22
    protocol  = "tcp"

    cidr_blocks = [
      "10.0.0.0/8",
    ]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0", "::/0"]
  }
}

resource "aws_security_group" "all_worker_mgmt" {
  name_prefix = "all_worker_management"
  vpc_id      = module.vpc.vpc_id

  ingress {
    from_port = 22
    to_port   = 22
    protocol  = "tcp"

    cidr_blocks = [
      "10.0.0.0/8",
      "172.16.0.0/12",
      "192.168.0.0/16",
    ]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0", "::/0"]
  }
}

module "eks" {
  source       = "terraform-aws-modules/eks/aws"
  cluster_name    = "nfl-eks-cluster"
  cluster_version = "1.29"
  subnet_ids         = module.vpc.private_subnets
  cluster_endpoint_private_access = true
  cluster_endpoint_public_access  = true
  cluster_additional_security_group_ids = [aws_security_group.all_worker_mgmt.id]
  vpc_id = module.vpc.vpc_id
  enable_cluster_creator_admin_permissions = true
  enable_irsa = true

  cluster_addons = {
    eks-pod-identity-agent = {
      most_recent = true
    }
  }

  eks_managed_node_groups = {
    example = {
      min_size     = 1
      max_size     = 3
      desired_size = 2

      instance_types = ["t3.large"]
      capacity_type  = "SPOT"
      }
    }
  }

data "aws_eks_cluster_auth" "cluster_auth" {
  name = module.eks.cluster_name
}

resource "aws_iam_role" "alb_ingress_role" {
  depends_on = [
    module.eks
  ]
  name = "alb_ingress_role"

  assume_role_policy = jsonencode({
    Version = "2012-10-17",
    Statement = [
      {
        "Effect": "Allow",
        "Action": "sts:AssumeRoleWithWebIdentity",
        "Principal": {
          "Federated": "arn:aws:iam::${data.aws_caller_identity.current.account_id}:oidc-provider/${replace(module.eks.cluster_oidc_issuer_url, "https://", "")}"
        },
        "Condition": {
          "StringEquals": {
            "${replace(module.eks.cluster_oidc_issuer_url, "https://", "")}:aud": [
              "sts.amazonaws.com"
            ]
          }
        }
      }
    ]
  })
}

resource "aws_iam_role_policy_attachment" "alb_ingress_policy_attachment" {
  role       = aws_iam_role.alb_ingress_role.name
  policy_arn = "arn:aws:iam::aws:policy/AmazonEKSClusterPolicy"
}

resource "aws_iam_role_policy_attachment" "alb_ingress_service_policy_attachment" {
  role       = aws_iam_role.alb_ingress_role.name
  policy_arn = "arn:aws:iam::aws:policy/AmazonEKSServicePolicy"
}

resource "aws_iam_role_policy_attachment" "alb_ingress_elb_policy_attachment" {
  role       = aws_iam_role.alb_ingress_role.name
  policy_arn = "arn:aws:iam::aws:policy/service-role/AmazonEC2ContainerServiceforEC2Role"
}

resource "aws_iam_role_policy_attachment" "alb_ingress_lb_policy_attachment" {
  role       = aws_iam_role.alb_ingress_role.name
  policy_arn = "arn:aws:iam::aws:policy/ElasticLoadBalancingFullAccess"
}

resource "aws_iam_role_policy_attachment" "alb_ingress_shield_policy_attachment" {
  role       = aws_iam_role.alb_ingress_role.name
  policy_arn = "arn:aws:iam::aws:policy/service-role/AWSShieldDRTAccessPolicy"
}

resource "aws_iam_role_policy_attachment" "alb_ingress_waf_policy_attachment" {
  role       = aws_iam_role.alb_ingress_role.name
  policy_arn = "arn:aws:iam::aws:policy/AWSWAFFullAccess"
}

resource "helm_release" "alb_ingress_controller" {
  name       = "nfl"
  repository = "https://aws.github.io/eks-charts"
  chart      = "aws-load-balancer-controller"
  namespace  = "kube-system"

  depends_on = [
    kubernetes_service_account.aws_alb_ingress_controller_sa
  ]

  values = [
    <<EOF
clusterName: ${module.eks.cluster_name}
region: ${var.region}
vpcId: ${module.vpc.vpc_id}
serviceAccount:
  create: false
  name: aws-load-balancer-controller
EOF
  ]
}

resource "kubernetes_service_account" "aws_alb_ingress_controller_sa" {
  metadata {
    name      = "aws-load-balancer-controller"
    namespace = "kube-system"
    annotations = {
      "eks.amazonaws.com/role-arn" = aws_iam_role.alb_ingress_role.arn
    }
  }
}

resource "aws_msk_configuration" "kafka_config" {
  kafka_versions = ["3.5.1"]
  name = "kafka-configuration"

  server_properties = <<EOF
auto.create.topics.enable = true
delete.topic.enable = true
EOF
}

# MSK Cluster
resource "aws_msk_cluster" "nfl_msk" {
  cluster_name           = "nfl-cluster"
  kafka_version          = "3.5.1"
  number_of_broker_nodes = 3
  configuration_info {
    arn      = aws_msk_configuration.kafka_config.arn
    revision = aws_msk_configuration.kafka_config.latest_revision
  }

  broker_node_group_info {
    instance_type   = "kafka.m5.large"

    client_subnets = module.vpc.private_subnets
    security_groups = [module.vpc.default_security_group_id]
    storage_info {
      ebs_storage_info {
        volume_size = 10
      }
    }
  }

  encryption_info {
    encryption_in_transit {
      client_broker = "PLAINTEXT"
      in_cluster    = true
    }
  }
}

# RDS PostgreSQL Instance
resource "aws_db_instance" "default" {
  allocated_storage    = 10
  engine               = "postgres"
  instance_class       = "db.m5d.large"
  db_name              = "nfldatabase"
  username             = "postgres"
  password             = "postgres"
  skip_final_snapshot  = true
  publicly_accessible  = false
  vpc_security_group_ids = [module.vpc.default_security_group_id]
  db_subnet_group_name = aws_db_subnet_group.default.name
}

resource "aws_db_subnet_group" "default" {
  name       = "nfl_db_subnet_group"
  subnet_ids = module.vpc.private_subnets

  tags = {
    Name = "NFL DB subnet group"
  }
}