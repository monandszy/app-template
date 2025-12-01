ROOT_PROJECT_NAME=project-base
PROJECT_NAME=app-template
VERSION=latest

JAR_PATH="${PROJECT_NAME}-${VERSION}.jar"
PROD_IMAGE_TAG="${ROOT_PROJECT_NAME}/${PROJECT_NAME}:1.0.0  -SNAPSHOT"
IMAGE_TAG="${ROOT_PROJECT_NAME}/${PROJECT_NAME}-dev:${VERSION}"

cd ./../
gradle build
cd docker

cp -r ./../build/libs/${JAR_PATH} .

#docker build \
#    --build-arg "JAR_PATH=./${JAR_PATH}" \
#    -t "${PROD_IMAGE_TAG}" \
#    -q \
#    -f ./Dockerfile \
#    .
#
#echo "Image: ${PROD_IMAGE_TAG}"

docker build \
    --build-arg "JAR_PATH=./${JAR_PATH}" \
    -t "${IMAGE_TAG}" \
    -q \
    -f ./Dockerfile \
    .

echo "Image: ${IMAGE_TAG}"

docker-compose -p ${PROJECT_NAME}-dev -f compose-dev.yml up -d
#docker-compose -p ${PROJECT_NAME} -f compose-prod.yml up -d