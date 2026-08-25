pipeline {

    agent any

    tools {
            maven 'Maven-3.9'
        }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test'
            }
        }
    }

    post {
        always {
            echo 'Test execution completed'
        }

        success {
            echo 'OrangeHRM tests passed'
        }

        failure {
            echo 'OrangeHRM tests failed'
        }
    }
}