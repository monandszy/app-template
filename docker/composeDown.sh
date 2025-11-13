PROJECT_NAME=app-template

docker-compose -p ${PROJECT_NAME}-dev -f compose-dev.yml down
docker-compose -p ${PROJECT_NAME} -f compose-prod.yml down