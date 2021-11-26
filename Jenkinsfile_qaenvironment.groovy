pipeline {
    agent any
     parameters{
        //string(name: ENVIRONMENT_NAME , defaultValue:QA,description:"")
        string(name: 'ENVIRONMENT_NAME', defaultValue: '', description: '')
    }

    // ----------------

    stages {
        stage('Build Container') {
            steps {
                echo 'Building Container..'

                script {
                    if (env.ENVIRONMENT_NAME == 'QA') {
                        SERVERS = ["172.31.93.44","172.31.85.14"]
                    } else if (env.ENVIRONMENT_NAME == 'DEV') {
                        SERVERS = ["1.1.2.1","1.1.2.2","1.1.2.3"]
                    }
                    println SERVERS.size()
                    for(item in SERVERS){
                     println item
                     sh 'echo $item'
                     sh """
                        echo "download warfirle from s3"
                        echo "scp file to $item"
                        ssh -o stricthostkeychecking=no -i /tmp/nvirginia.pem ec2-user@$item "hostname"
                        """
                        }
                   // echo SERVERS
                }
                // echo 'Building Branch: ' + env.BRANCH_NAME
                // echo 'Build Number: ' + env.BUILD_NUMBER
                // echo 'Building Environment: ' + ENV_NAME

                // echo "Running your service with environemnt ${ENV_NAME} now"
            }
        }
    }
}