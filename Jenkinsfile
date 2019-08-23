pipeline {

  environment {
    PROJECT = "eunbyul"
    APP_NAME = "spring-boot-journal"
    CLUSTER = "jenkins-cd"
    CLUSTER_ZONE = "us-east1-d"
    IMAGE_TAG = "gcr.io/${PROJECT}/${APP_NAME}:${env.BRANCH_NAME}.${env.BUILD_NUMBER}"
    JENKINS_CRED = "${PROJECT}"
  }

  agent {
    kubernetes {
      label 'spring-boot-journal'
      defaultContainer 'jnlp'
      yaml """
apiVersion: v1
kind: Pod
metadata:
labels:
  component: ci
spec:
  # Use service account that can deploy to all namespaces
  serviceAccountName: cd-jenkins
  containers:
  - name: gcloud
    image: gcr.io/cloud-builders/gcloud
    command:
    - cat
    tty: true
  - name: kubectl
    image: gcr.io/cloud-builders/kubectl
    command:
    - cat
    tty: true
  - name: mvn
    image: gcr.io/cloud-builders/mvn
    command:
    - cat
    tty: true
"""
}
  }
  stages {
      stage('Build'){
          steps{
             container('mvn'){
                 sh "mvn package -DskipTests"
             }
          }
      }
     stage('Build and push image with Container Builder') {
       steps {
         container('gcloud') {
           sh """
            ln -s `pwd` /spring-boot-journal
            cd /spring-boot-journal
           """
           sh "PYTHONUNBUFFERED=1 gcloud builds submit -t ${IMAGE_TAG} ."
         }
       }
     }
    stage('Deploy') {
      // master
      when { branch 'master' }
      steps{
        container('kubectl') {
        // Change deployed image in canary to the one we just built
          sh("sed -i.bak 's#gcr.io/eunbyul/spring-boot-journal:v1#${IMAGE_TAG}#' spring-boot-journal.yaml")
          step([$class: 'KubernetesEngineBuilder',namespace:'journal', projectId: env.PROJECT, clusterName: env.CLUSTER, zone: env.CLUSTER_ZONE, manifestPattern: 'spring-boot-journal.yaml', credentialsId: env.JENKINS_CRED, verifyDeployments: false])
       }
      }
    }
  }
}

