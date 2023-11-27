@Library('shared-library') _

def projectlist = 'https://github.com/yuanshuai1122/top-omoms-server/raw/main/project-list.yaml'
k8sCluster(projectURL: projectlist)

pipeline {
	agent any
	stages {
		stage('初始化') {
			steps {
				script{
					runWrapper.loadJSON('/jenkins-project.json')
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
					runWrapper.runSteps('部署')
				}
			}
		}
	}
}