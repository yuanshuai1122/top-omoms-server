@Library('shared-library') _

pipeline {
	agent any
	stages {
		stage('初始化') {
			steps {
				script{
					runWrapper.loadJSON('/omoms-server/jenkins-project.json')
					runWrapper.runSteps('初始化')
				}
			}
		}
		stage('编译构建') {
			steps {
				script{
					runWrapper.runSteps('编译构建')
				}
			}
		}
		stage('部署') {
			steps {
				script{
				    docker.withRegistry('https://registry.hub.docker.com', 'docker-hub-credentials') {
                        runWrapper.runSteps('部署')
                    }

				}
			}
		}
	}
}