// vars/buildPythonService.groovy

def call(String serviceDir) {
    dir(serviceDir) {
        stage("Install Dependencies") {
            sh 'pipenv install'
        }
        stage("Test Runtime") {
            sh """
            pipenv shell
            python app.py 
            """
        }
    }
} 