
cd infrastructure
terraform init
terraform apply

KAFKA_BOOTSTRAP_SERVERS="$(terraform output -raw kafka_servers)"
POSTGRES_URL="$(terraform output -raw rds_endpoint)"
POSTGRES_PORT="$(terraform output -raw rds_port)"

cd ..
./gradlew clean build
./gradlew buildImage

docker push --platform linux/amd64 ${DOCKER_REPO}/nfl-integration-service:latest
docker push --platform linux/amd64 ${DOCKER_REPO}/nfl-game-service:latest

cd  nfl-integration-service/deployment

aws eks --region us-west-2 update-kubeconfig --name nfl-eks-cluster

envsubst < nfl-int-service-deployment.yaml | kubectl apply -f -
kubectl apply -f nfl-int-service-svc.yaml
kubectl apply -f nfl-int-service-ingress.yaml

cd ..
cd ..
cd nfl-game-service/deployment
envsubst < nfl-game-service-deployment.yaml | kubectl apply -f -
kubectl apply -f nfl-game-service-svc.yaml
kubectl apply -f nfl-game-service-ingress.yaml