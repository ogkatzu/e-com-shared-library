// vars/buildPythonService.groovy

def call(String serviceDir) {
    dir(serviceDir) {
        stage("Install Dependencies") {
            sh 'echo "Installing Dependencies"'
        }
        stage("Test Runtime") {
            sh 'echo "Running Tests"'
        }
    }
} 