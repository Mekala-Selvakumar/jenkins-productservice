pipeline {
    agent any

    tools {
        jdk 'openjdk-17'        // Name should match Global Tool Configuration
        maven 'MVN'             // Your configured Maven version
    }

    environment {
        DB_HOST = 'localhost'
        DB_PORT = '3306'
        DB_NAME = 'nessdb'
        DB_USER = 'root'
        DB_PASS = 'root'
    }

    stages {
        stage('Checkout') {
            steps {
                echo '📥 Cloning repository...'
                git branch: 'main', url: 'https://github.com/Mekala-Selvakumar/jenkins-productservice.git'
            }
        }

       /* stage('Start MySQL (Docker)') {
            steps {
                echo '🐬 Starting MySQL container...'
                sh '''
                docker run --name mysql-test-db -e MYSQL_ROOT_PASSWORD=${DB_PASS} \
                    -e MYSQL_DATABASE=${DB_NAME} -p ${DB_PORT}:3306 -d mysql:8.0
                sleep 25
                '''
            }
        }*/

        stage('Build') {
            steps {
                echo '🔨 Building Spring Boot application...'
                sh 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                echo '🧪 Running unit/integration tests...'
                sh 'mvn test'
            }
        }

        stage('Package') {
            steps {
                echo '📦 Packaging app into JAR...'
                sh 'mvn package -DskipTests'
            }
        }
    }

    post {
        always {
            echo '🧹 Cleaning up...'
            sh 'docker rm -f mysql-test-db || true'
        }
        success {
            echo '✅ Build and tests succeeded!'
        }
        failure {
            echo '❌ Build failed. Please check logs.'
        }
    }
}
