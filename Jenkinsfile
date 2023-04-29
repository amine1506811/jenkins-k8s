podTemplate(
    inheritFrom: 'jenkins-agent',
    containers: [
        containerTemplate(name: 'maven', image: 'maven:3.8.6-openjdk-11-slim', ttyEnabled: true, command: 'cat'),
        //containerTemplate(name: 'docker', image: 'docker:dind', ttyEnabled: true, command: 'cat')
    ]
) {
    node(POD_LABEL) {
        stage('Checkout Source Code') {
            checkout scm
        }
        stage('Build and deploy JAR and Docker Image') {
            container('maven'){
                dir('my-veterinary-ms'){
                    withCredentials([file(credentialsId: 'mvn-settings-xml', variable: 'MAVEN_SETTINGS_XML'), usernamePassword(credentialsId: 'dockerhub-credentials', usernameVariable:'DOCKERHUB_CREDENTIALS_USR', passwordVariable:'DOCKERHUB_CREDENTIALS_PSW')]){
                        //sh 'mvn -s $MAVEN_SETTINGS_XML versions:set -DremoveSnapshot'
                        //sh 'mvn -s $MAVEN_SETTINGS_XML clean install -Djacoco.skip=true'
                        //sh 'mvn -s $MAVEN_SETTINGS_XML deploy -Djacoco.skip=true'
                        sh 'mvn -s $MAVEN_SETTINGS_XML versions:set -DremoveSnapshot clean compile jib:build'
                    }
                }
            }
        }
        stage('Update version and push to GitHub'){
            environment{
                     BRANCH_NAME = "${GIT_BRANCH.split("/")[1]}"
                }
            container('maven'){
                dir('my-veterinary-ms'){
                    withCredentials([file(credentialsId: 'mvn-settings-xml', variable: 'MAVEN_SETTINGS_XML')]){
                        sh 'mvn -s $MAVEN_SETTINGS_XML versions:set -DnextSnapshot'
                    }
                }
            }
            withCredentials([usernamePassword(credentialsId: 'github-cred', passwordVariable: 'GIT_PASSWORD', usernameVariable: 'GIT_USERNAME')]) {
                
                sh 'git checkout $BRANCH_NAME'
                sh 'git config user.email amine150@gmail.com'
                sh 'git config user.name $GIT_USERNAME'
                sh 'git add .'
                sh 'git commit -m "update version"'
                sh 'git push https://${GIT_USERNAME}:${GIT_PASSWORD}@github.com/${GIT_USERNAME}/ci-pipe.git  $BRANCH_NAME'
            }
        }
    }
}
