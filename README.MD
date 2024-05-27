
# Project Stack

- Java 17
- Spring Boot 3.3.0
- Apache Kafka
- PostgreSQL
- Terraform

## Building the project

``./gradlew clean build``
## Generating docker images
``./gradlew buildImage``
## Deployment to EKS
``export DOCKER_REPO=<set user name>``

run deploy.sh script on root dir
