pipeline {

    agent any

    parameters {
        string(defaultValue: "https://195360077735.dkr.ecr.eu-west-1.amazonaws.com", description: "", name: "ECRRepo")
        string(defaultValue: "195360077735.dkr.ecr.eu-west-1.amazonaws.com/cicdapp", description: "", name: "dockerImageName")
        string(defaultValue: "latest", description: '', name: "dockerImageTag")
        string(defaultValue: "cicdapp", description: '', name: "kubeName")
    }

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
                gatlingArchive()
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
                    docker.build(params.dockerImageName)
                    docker.withRegistry(params.ECRRepo, "ecr:eu-west-1:bebe28a3-ed63-4acb-8a04-9e9174628b5f") {
                      docker.image(params.dockerImageName).push(params.dockerImageTag)
                    }
                }
                withKubeConfig([credentialsId: 'kubernetes-deployer', serverUrl: 'https://api-cicd-k8s-local-gtgus3-1195838833.eu-west-1.elb.amazonaws.com']) {
                    sh "kubectl delete services ${params.kubeName} || true"
                    sh "kubectl delete deployment ${params.kubeName} || true"
                    sh "docker rmi \$(docker images -f dangling=true -q) || true"
                    sh "\$(aws ecr get-login --region eu-west-1 --no-include-email)"
                    sh "kubectl run cicdapp --image=${params.dockerImageName}:${params.dockerImageTag} --port=80"
                    sh "kubectl expose deployment ${params.kubeName} --type='LoadBalancer'"
                }
            }
        }
    }
}