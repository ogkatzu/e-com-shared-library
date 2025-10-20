// vars/getServiceVersion.groovy

def call(Map config = [:]) {
    def serviceDir = config.serviceDir
    def serviceLang = config.serviceLang
    def useGitCommit = config.useGitCommit ?: false
    def serviceVersion = ""

    dir(serviceDir) {
        if (useGitCommit) {
            // Use git commit SHA (short)
            serviceVersion = sh(script: "git rev-parse --short HEAD", returnStdout: true).trim()
        } else if (serviceLang == "node") {
            // Read version from package.json
            def packageJson = readJSON file: 'package.json'
            serviceVersion = packageJson.version ?: "1.0.0"
        } else if (serviceLang == "java") {
            // Read version from build.gradle
            def gradleFile = readFile('build.gradle')
            def matcher = (gradleFile =~ /version\s*=\s*['"](.+?)['"]/)
            if (matcher) {
                serviceVersion = matcher[0][1]
            } else {
                serviceVersion = "1.0.0"
            }
        } else if (serviceLang == "python") {
            // Try to read from setup.py or use default
            if (fileExists('version.txt')) {
                def setup = readFile('version.txt')
                def matcher = (setup =~ /version\s*=\s*['"](.+?)['"]/)
                if (matcher) {
                    serviceVersion = matcher[0][1]
                } else {
                    serviceVersion = "1.0.0"
                }
            } else {
                serviceVersion = "1.0.0"
            }
        } else {
            serviceVersion = "1.0.0"
        }
    }

    // Append build number for uniqueness
    def fullVersion = "${serviceVersion}-${env.BUILD_NUMBER}"

    echo "Service version for ${serviceDir}: ${fullVersion}"
    return fullVersion
}