pipeline {
    agent any

    tools {
        maven 'Maven-3.9'
    }

    environment {
        SONAR_TOKEN = credentials('300213705055e901f7e02846794c2d3362775b75')
    }

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build, Test & Coverage') {
            steps {
                sh 'mvn clean verify'
            }
        }

        stage('SonarCloud Analysis') {
            steps {
                sh '''
                mvn sonar:sonar \
                  -Dsonar.host.url=https://sonarcloud.io \
                  -Dsonar.login=$SONAR_TOKEN
                '''
            }
        }


        stage('Quality Gate') {
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
    }

    post {
        success {
            echo '✅ Build SUCCESS – Coverage meets Quality Gate'
        }
        failure {
            echo '❌ Build FAILED – Coverage or Quality Gate failed'
        }
    }
}
