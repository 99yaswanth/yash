pipeline{
    agent any
    
    parameters { 
        string(name: 'SERVERIPS', defaultValue: '', description: '') 
        string(name: 'BRANCH', defaultValue: '', description: '')
        string(name: 'BUILD', defaultValue: '', description: '')
    }
    stages{
        stage("Multiple servers")
        {
            steps {
                sh '''
                    aws s3 cp s3://yashwanth12/${BRANCH}/${BUILD}/hello-${BUILD}.war .
                    ls -l
                    IFS=',' read -ra ADDR <<< "${SERVERIPS}"
                    for ip in \"${ADDR[@]}\"; 
                    do
                    echo $ip
                    echo "Here we can use scp command"
                    scp -o stricthostkeychecking=no -i /tmp/nvirginia.pem hello-${BUILD}.war ec2-user@$ip:/var/lib/tomcat/webapps
                    ssh -o stricthostkeychecking=no -i /tmp/nvirginia.pem ec2-user@$ip "hostname"
                     # process "$i"
                    done
                   
                   
                   '''
            }
        }
    }
}