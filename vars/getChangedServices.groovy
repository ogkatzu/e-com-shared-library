  // vars/getChangedServices.groovy

  def call(Map config = [:]) {
      def serviceMapping = [
          'products-cna-microservice': 'products',
          'cart-cna-microservice': 'cart',
          'users-cna-microservice': 'users',
          'search-cna-microservice': 'search',
          'store-ui': 'store-ui'
      ]

      sh 'git fetch origin || true'

      def changedFiles = ''
      def isPR = env.CHANGE_ID != null

      if (isPR) {
          def targetBranch = env.CHANGE_TARGET ?: 'main'
          echo "PR build - comparing against origin/${targetBranch}"
          changedFiles = sh(
              script: "git diff --name-only origin/${targetBranch}...HEAD",
              returnStdout: true
          ).trim()
      } else {
          echo "Direct commit build - comparing against previous commit"
          changedFiles = sh(
              script: 'git diff --name-only HEAD~1 HEAD || git diff --name-only HEAD',
              returnStdout: true
          ).trim()
      }

      echo "Changed files:\n${changedFiles}"

      if (!changedFiles) {
          echo "No changes detected - building all services"
          return serviceMapping.values().toList()
      }

      def affectedServices = []

      changedFiles.split('\n').each { file ->
          if (file) {  // Skip empty lines
              serviceMapping.each { dir, serviceName ->
                  if (file.startsWith("${dir}/")) {
                      if (!affectedServices.contains(serviceName)) {
                          affectedServices.add(serviceName)
                      }
                  }
              }
          }
      }

      // Infrastructure changes = build all
      if (changedFiles.contains('infra/') || changedFiles.contains('Jenkinsfile')) {
          echo "Infrastructure changed - building all services"
          return serviceMapping.values().toList()
      }

      if (!affectedServices) {
          echo "No service directories affected - building all services as safeguard"
          return serviceMapping.values().toList()
      }

      echo "Affected services: ${affectedServices.join(', ')}"
      return affectedServices
  }