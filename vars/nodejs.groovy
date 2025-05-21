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
                    sh 'unit test cases'
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
