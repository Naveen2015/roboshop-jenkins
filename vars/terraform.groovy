def call(){
    pipeline {
        agent {
            node {
                label 'workstation'
            }
        }
        parameters {
            choice(name: 'env', choices: ['dev', 'prod'], description: 'Pick environment')
            choice(name: 'action', choices: ['apply', 'destroy'], description: 'Pick environment')
        }
        options {
            ansiColor('xterm')
        }
        stages {
            stage('Terraform INIT') {
                steps {
                    sh 'terraform init -backend-config=env-${env}/state.tfvars'
                }
            }
            stage('Terraform action') {
                steps {
                    sh 'terraform ${action} -auto-approve -var-file=env-${env}/main.tfvars'
                }
            }

        }
        post {
            always {
                cleanWs()
            }
        }
    }
}
