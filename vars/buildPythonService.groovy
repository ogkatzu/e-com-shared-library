// vars/buildPythonService.groovy

def call(String serviceDir) {
    dir(serviceDir) {
        stage("Verify Python") {
            sh 'python3 --version && pip3 --version && pipenv --version'
        }
        stage("Install Dependencies") {
            sh 'echo "Installing Dependencies"'
        }
        stage("Test Runtime") {
            sh 'echo "Running Tests"'
        }
    }
} 