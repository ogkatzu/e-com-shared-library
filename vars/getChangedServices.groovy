  // vars/getChangedServices.groovy

  def call(Map config = [:]) {
      def baseBranch = config.baseBranch ?: 'origin/master'
      def serviceMapping = [
          'products-cna-microservice': 'products',
          'cart-cna-microservice': 'cart',
          'users-cna-microservice': 'users',
          'search-cna-microservice': 'search',
          'store-ui': 'store-ui'
      ]

      // Get list of changed files
      def changedFiles = sh(
          script: "git diff --name-only ${baseBranch}...HEAD",
          returnStdout: true
      ).trim()

      echo "Changed files:\n${changedFiles}"

      // Parse which services are affected
      def affectedServices = []

      changedFiles.split('\n').each { file ->
          serviceMapping.each { dir, serviceName ->
              if (file.startsWith("${dir}/")) {
                  if (!affectedServices.contains(serviceName)) {
                      affectedServices.add(serviceName)
                  }
              }
          }
      }

      // Check for shared infrastructure changes
      if (changedFiles.contains('infra/') || changedFiles.contains('Jenkinsfile')) {
          echo "Infrastructure or Jenkinsfile changed - will build all services"
          return serviceMapping.values().toList()
      }

      echo "Affected services: ${affectedServices.join(', ')}"
      return affectedServices
  }