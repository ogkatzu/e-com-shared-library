def call() {
      return [
          'products': [
              dir: 'products-cna-microservice',
              lang: 'node',
              imageName: 'products',
              agent: 'any'
          ],
          'cart': [
              dir: 'cart-cna-microservice',
              lang: 'java',
              imageName: 'cart',
              agent: 'jdk-17'
          ],
          'users': [
              dir: 'users-cna-microservice',
              lang: 'python',
              imageName: 'users',
              agent: 'any'
          ],
          'search': [
              dir: 'search-cna-microservice',
              lang: 'node',
              imageName: 'search',
              agent: 'any'
          ],
          'store-ui': [
              dir: 'store-ui',
              lang: 'node',
              imageName: 'store-ui',
              agent: 'any'
          ]
      ]
  }