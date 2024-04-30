
node {

        stage('Git Clone') {
            cleanWs()
            git branch: 'main', url: 'https://github.com/MyGurukulam-P8/attendance-api.git'
        }

        stage('Compile') {
             sh 'pip install pyinstaller'
             sh '/var/lib/jenkins/.local/bin/pyinstaller *.py'
        }
  
}
