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

        stage('Build & Test (with coverage)') {
            steps {
                sh 'mvn clean verify'
            }
        }

        stage('Package') {
            steps {
                sh 'mvn package -DskipTests'
            }
        }
    }

    post {
        success {
            echo 'Build SUCCESS! Coverage ≥ 80%'
        }
        failure {
            echo 'Build FAILED! Test coverage is below 80%'
        }
    }
}
