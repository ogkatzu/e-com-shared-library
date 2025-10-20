def call(String serviceName, Map serviceConfig) {
    def version = getServiceVersion(
        serviceDir: serviceConfig.dir,
        serviceLang: serviceConfig.lang
    )

    // Build based on language
    if (serviceConfig.lang == 'node') {
        buildNodeService(serviceConfig.dir)
    } else if (serviceConfig.lang == 'java') {
        buildJavaService(serviceConfig.dir)
    } else if (serviceConfig.lang == 'python') {
        buildPythonService(serviceConfig.dir)
    }

    // Build Docker image
    def imageTag = dockerBuild(
        serviceDir: serviceConfig.dir,
        imageName: serviceConfig.imageName,
        imageTag: version
    )

    return [version: version, imageTag: imageTag]
}