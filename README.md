# 📁 Get Issues from any repository using GitHub Api

This Spring boot application is responsible for retrieving the contributors and Issues from a given repository in GitHub via webhook after a given time (default set to 1 hour)

# 🛠️ How to run

Please follow the instructions to run this application:

* Used Maven 3.9.1 and JDK 19

Maven build:

        mvn clean
        mvn install

To run the integration, please run:

        mvn install -DskipITs=false

Go to the `\github-issues\target` directory and find the `github-issues-0.0.1-SNAPSHOT.jar` file built, then run:

        java -jar .\github-issues-0.0.1-SNAPSHOT.jar


Now, if everithing works, please check the `/about` endpoint at:

        http://localhost:8080/issues/about

You may see the following text:

        Github information API (Version 1.0) created by Alexandre Hauffe

#  How it works

### Endpoints:

There is one endpoint responsible for scheduling a POST to a remote api represented by a Webhook

* ### Schedule a async method to send the repository data to a remote Api via webhook

        GET:   http://localhost:8080/issues/{user_id}/{repo_name}

    No request body needed, returns the repository details from GitHub via webhook and a success or failed status.
  * ##### Return Example

      If the repo and user were found, it schedules a POST via webhook (https://webhook.site/)

          {
             "success": true,
             "message": "Request sent",
             "timeScheduled": "2023-04-21T19:03:25.450+00:00"
          }
    
      After 1 day, the webhook site gets the detailed information as the bellow example

          {
            "user": "Alexandre",
            "repository": "api_nlw",
            "issues": [],
            "contributors": [
                {
                  "nome": "Alexandre",
                  "user": "Hauffe",
                  "qtd_commits": 11
                }
            ] 
          }


# Final considerations

This exercise has async and remote request components. Those are the building blocks for developing this solution. I experimented several ways of putting the right code in the right spot and this is the best way I've found for now. 
Also, the builder pattern was created not for the moment necessity, but for enhancing the maintainability and scalability of the system, to make it easier to add new features in the future.
This was also the reason of creating external settings for the remote URIs and the "time to post", which are called by the UrlCaller.
There is room to improve, but this is the MVP for now, done in 1 week.