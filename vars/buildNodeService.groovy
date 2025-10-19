// vars/buildNodeService.groovy

def call(Map config) {
    stage("Install Dependencies") {
        sh 'ls -lah'
        sh 'npm ci'
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