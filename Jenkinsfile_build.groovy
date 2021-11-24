//Declerative pipeline
pipeline{
    agent any
    parameters{
        string(name: 'BRANCH_NAME', defaultValue: 'master', description: 'give branch name')
       // string(name: 'BUILD_NUMBER', defaultValue: '', description: 'enter build number')
    }
    stages{
        stage("clone the code"){
            steps{
                println "cloning code from github"
            }
        }
        stage("building the code"){
            steps{
                println "build the code"
                sh "mvn clean package"
                sh "ls -l"
            }
        }
        stage("uploading artifacts to s3"){
            steps{
                println "upload artifacts to s3 bucket"
                sh "echo $BUILD_NUMBER"
                sh "aws s3 cp target/hello-${BUILD_NUMBER}.war s3://yashwanth12/"

            }
        }
    }
}