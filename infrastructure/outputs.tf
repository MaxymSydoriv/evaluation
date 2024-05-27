output "vpc_id" {
  description = "The ID of the VPC"
  value       = module.vpc.vpc_id
}

output "eks_cluster_id" {
  description = "The ID of the EKS cluster"
  value       = module.eks.cluster_id
}

output "eks_cluster_endpoint" {
  description = "The endpoint of the EKS cluster"
  value       = module.eks.cluster_endpoint
}

output "msk_cluster_arn" {
  description = "The ARN of the MSK cluster"
  value       = aws_msk_cluster.nfl_msk.arn
}

output "rds_endpoint" {
  description = "The endpoint of the RDS instance"
  value       = aws_db_instance.default.endpoint
}

output "rds_port" {
  description = "The port of the RDS instance"
  value = aws_db_instance.default.port
}

output "kafka_servers" {
  value = aws_msk_cluster.nfl_msk.bootstrap_brokers
}