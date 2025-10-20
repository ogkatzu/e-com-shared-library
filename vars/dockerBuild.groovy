// vars/dockerBuild.groovy

def call(Map config = [:]) {
    def serviceDir = config.serviceDir
    def imageName = config.imageName ?: serviceDir
    def imageTag = config.imageTag ?: 'latest'

    dir(serviceDir) {
        stage("Build Docker Image: ${imageName}") {
            sh "docker build -t ${imageName}:${imageTag} ."

            // Also tag as latest
            sh "docker tag ${imageName}:${imageTag} ${imageName}:latest"

            echo "Successfully built Docker image: ${imageName}:${imageTag}"
            echo "Also tagged as: ${imageName}:latest"
        }
    }

    return "${imageName}:${imageTag}"
}