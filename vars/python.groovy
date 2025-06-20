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
                when {
                    expression {
                        env.TAG_NAME ==~ ".*"
                    }
                }
                steps {
                    sh 'echo $TAG_NAME > VERSION'
                    //sh 'zip -r ${component}-${TAG_NAME}.zip *.ini *.py *.txt VERSION ${schema_dir}'
                    //sh 'curl -v -u admin:admin --upload-file ${component}-${TAG_NAME}.zip http://172.31.42.37:8081/repository/${component}/${component}-${TAG_NAME}.zip'
                    sh 'aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin 071312222500.dkr.ecr.us-east-1.amazonaws.com'
                    sh 'docker build -t  071312222500.dkr.ecr.us-east-1.amazonaws.com/${component}:${TAG_NAME} .'
                    sh 'docker push 071312222500.dkr.ecr.us-east-1.amazonaws.com/${component}:${TAG_NAME}'
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
