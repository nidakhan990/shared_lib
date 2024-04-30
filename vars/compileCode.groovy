def call(String repoUrl, String branch) {
    node {
        try {
            stage('Python Version') {
                cleanWs() // Ensure a clean workspace before executing this stage
                sh 'python3 --version'
            }

            stage('Git Clone') {
                 checkout([$class: 'GitSCM', branches: [[name: "${branch}"]], userRemoteConfigs: [[url: "${repoUrl}"]]])

            }

            stage("Dast") {
                sh ' wget https://github.com/zaproxy/zaproxy/releases/download/v2.14.0/ZAP_2.14.0_Linux.tar.gz'
                sh ' tar -xf ZAP_2.14.0_Linux.tar.gz'
                sh 'chmod 777 ZAP_2.14.0'
                sh 'ZAP_2.14.0/zap.sh -cmd -port 8086 -quickurl http://3.21.100.62:8080/api/v1/attendance/health -quickprogress -quickout ${WORKSPACE}/out2.html'
            }

        } finally {
            stage('Report') {
            publishHTML([allowMissing: false, alwaysLinkToLastBuild: false, keepAll: false, reportDir: '${WORKSPACE}', reportFiles: 'out2.html', reportName: 'HTML Report', reportTitles: '', useWrapperFileDirectly: true])
            }
        }
    }
}
