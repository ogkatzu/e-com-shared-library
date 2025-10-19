// vars/buildNodeService.groovy

def call(String serviceDir) {
    dir(serviceDir) {
        stage("Install Dependencies") {
            sh """
            npm install
            """
        }
        // stage("Lint") {
        //     sh 'npm run lint || true'
        // }
        stage("Build") {
            def packageJson = readJSON file: 'package.json'
            if (packageJson.scripts?.build) {
                sh 'npm run build'
            }
        }
    }
}