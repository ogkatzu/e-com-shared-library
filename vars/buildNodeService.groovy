// vars/buildNodeService.groovy

def call(String serviceDir = 'products-cna-microservice') {
    dir(serviceDir) {
        stage("Install Dependencies") {
            sh """
            npm ci
            """
        }
        stage("Lint") {
            sh 'npm run lint || true'
        }
        stage("Build") {
            def packageJson = readJson file: 'package.json'
            if (packageJson.scripts?.build) {
                sh 'npm run build'
            }
        }
    }
}