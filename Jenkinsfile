pipeline {

    agent any

    stages {
        stage('Build') {
            environment {
                scannerHome = tool 'SonarQubeScanner'
            }
            steps {
                jacoco()
                sh "gradle build"
                withSonarQubeEnv('sonar') {
                    sh "${scannerHome}/bin/sonar-scanner"
                }
                timeout(time: 10, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
        stage('Test: Integration and Quality') {
            steps {
                echo 'Insert something here'
            }
        }
        stage('Test: Functional') {
            steps {
                echo 'Insert something here'
            }
        }
        stage('Test: Load & Security') {
            steps {
                sh "gradle gatlingRun"
            }
        }
        stage('Approval') {
            steps {
                echo 'Insert something here'
            }
        }
        stage('Deploy: Prod') {
            steps {
                script {
                    docker.build("195360077735.dkr.ecr.eu-west-1.amazonaws.com/cicdapp")
                    docker.withRegistry("https://195360077735.dkr.ecr.eu-west-1.amazonaws.com", "ecr:eu-west-1:bebe28a3-ed63-4acb-8a04-9e9174628b5f") {
                      docker.image("195360077735.dkr.ecr.eu-west-1.amazonaws.com/cicdapp").push("latest")
                    }
                }
                //put delete command lines in comments if deploying image for the first time
                withKubeConfig([credentialsId: 'kubernetes-deployer', serverUrl: 'https://api-cicd-k8s-local-gtgus3-1195838833.eu-west-1.elb.amazonaws.com']) {
                    sh "kubectl get services"
                    sh "kubectl delete services cicdapp"
                    sh "kubectl delete deployment cicdapp"
                    sh "docker rmi \$(docker images -f dangling=true -q)"
                    sh "\$(aws ecr get-login --region eu-west-1 --no-include-email)"
                    sh "kubectl run cicdapp --image=195360077735.dkr.ecr.eu-west-1.amazonaws.com/cicdapp:latest --port=80"
                    sh "kubectl expose deployment cicdapp --type='LoadBalancer'"
                }
            }
        }
    }
}