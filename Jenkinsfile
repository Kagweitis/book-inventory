pipeline {
    agent any
    
    stages {
        stage('Checkout') {
            steps {
                // Checkout the source code from the repository
                git 'https://github.com/Kagweitis/book-inventory.git'
            }
        }
        
        stage('Build') {
            steps {
                // Use Maven to build the Spring Boot application
                sh 'mvn clean package'
            }
        }
        
        stage('Test') {
            steps {
                // Run tests if needed
                sh 'mvn test'
            }
        }
    }
}
