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
            stage('code compile') {
                steps {
                    sh 'mvn compile'
                }
            }

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
                    sh 'mvn package; cp target/${component}-1.0.jar ${component}.jar'
                    sh 'echo $TAG_NAME > VERSION'
                    sh 'zip -r ${component}-${TAG_NAME}.zip ${component}.jar VERSION ${schema_dir}'
                    sh 'curl -v -u admin:admin --upload-file ${component}-${TAG_NAME}.zip http://172.31.42.37:8081/repository/${component}/${component}-${TAG_NAME}.zip'
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
