pipeline {

    agent any

    tools {
        maven 'Maven'
    }

    stages {

        stage('Environment') {

            steps {

                sh '''
                    echo "========== JAVA =========="
                    java -version

                    echo "========== MAVEN =========="
                    mvn -version

                    echo "========== WORKSPACE =========="
                    pwd

                    echo "========== FILES =========="
                    ls -la
                '''
            }
        }

        stage('Build') {

            steps {

                echo 'Building OrangeHRM project...'

                sh 'mvn clean compile'
            }
        }

        stage('Test') {

            steps {

                echo 'Running OrangeHRM tests...'

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