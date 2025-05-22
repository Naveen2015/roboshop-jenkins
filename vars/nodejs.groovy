def call(){
    pipeline {
        agent {
            node {
                label 'workstation'
            }
        }

        options {
            ansiColor('xterm')
        }
        stages {
            stage('code quality') {
                steps {
                    sh 'echo code quality'
                }
            }
            stage('UNIT TEST') {
                steps {
                    sh 'echo unit test cases'
                }
            }
            stage('Release Application') {
                steps {
                    sh 'env'
                    sh 'echo release application'
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
