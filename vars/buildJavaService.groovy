// vars/buildJavaService.groovy

def call(String serviceDir) {
    dir(serviceDir) {
        stage("Build") {
            sh 'gradle clean build'
        }
        stage("Test") {
            sh 'gradle test'
        }
    }
}