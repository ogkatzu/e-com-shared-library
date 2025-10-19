// vars/buildReactService.groovy

def call(String serviceDir = 'store-ui') {
    dir(serviceDir) {
        stage("Install Dependencies") {
            sh 'npm install'
        }
        stage("Unit Testing") {
            sh 'echo "Skipping Tests for Now!"'
        }
        stage("Build") {
            def packageJson = readJSON file: 'package.json'
            if (packageJson.scripts?.build) {
                sh 'npm run build'
            }
        }
    }
}