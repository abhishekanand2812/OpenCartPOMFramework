pipeline{
    agent any
    stages{
        stage("Build"){
            steps{
                echo("build the project")
            }
        }
        stage("Run UTs"){
            steps{
                echo("run unit testcases")
            }
        }
        stage("Deploy to dev"){
            steps{
                echo("deplyoing to dev env")
            }
        }
        stage("Deploy to qa"){
            steps{
                echo("deplyoing to qa env")
            }
        }
        stage("Run the automation regression testcases"){
            steps{
                echo("Run the automation regression testcases")
            }
        }
    
        stage("Deploy to stage"){
            steps{
                echo("deplyoing to stage env")
            }
        }
            stage("Run sanity automation testcases"){
            steps{
                echo("Running sanity automation testcases")
            }
        }
        stage("Deploy to prod"){
            steps{
                echo("deplyoing to production env")
            }
        }
    }
}
