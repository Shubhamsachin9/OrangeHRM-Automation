pipeline {

    agent any

    tools {
        maven 'Maven'
    }

    stages {

        stage('Checkout') {
            steps {
                echo "Checking out OrangeHRM Automation Project..."

                git(
                    branch: 'main',
                    changelog: false,
                    credentialsId: 'github-credentials',
                    poll: false,
                    url: 'https://github.com/Shubhamsachin9/OrangeHRM-Automation.git'
                )
            }
        }

        stage('Environment') {
            steps {

                sh '''
                    echo "JAVA "
                    java -version

                    echo "MAVEN "
                    mvn -version

                    echo "WORKSPACE "
                    pwd

                    echo "FILES "
                    ls -lah
                '''
            }
        }

        stage('Build') {
            steps {

                echo "Building OrangeHRM project..."
                sh 'mvn clean compile'
            }
        }
    }

    post {

        always {

            echo "Publishing test results for Build ${env.BUILD_NUMBER}"
        }

        success {

            echo "OrangeHRM tests passed"
            echo "Build Number: ${env.BUILD_NUMBER}"
        }

        failure {

            echo "OrangeHRM tests failed"
            echo "Build Number: ${env.BUILD_NUMBER}"
            echo "Checking console output and reports"
        }
    }
}
