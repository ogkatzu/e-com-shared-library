// vars/buildJavaService.groovy

def call(String serviceDir) {
    dir(serviceDir) {
        stage("Build ${serviceDir}") {
            // Build without running tests (-x test)
            sh 'gradle clean build -x test'
        }
    }
}