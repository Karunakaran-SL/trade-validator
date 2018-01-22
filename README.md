# Trade Validator Service

Sample application using Spring Boot and Angular 5 to demo validation of Trade Validation.

As main focus is on REST Service, and Swagger is integrated which provide better UI.
Additionally UI is implemented in very basic level, just a simple form. 
Sample application is deployed in Heroku cloud https://trade-validation-service.herokuapp.com

# Building Project
 # Project Setup
   1. Clone the project from git hub
   2. cd /trade-validator/trade-validator-client
 # UI Build
   1. npm install
   2. ng build -prod
 # Spring Boot Build  
   1. Move tp Spring Boot as cd ../trade-validator-service/
   2. Build Spring boot using "mvn clean install"
   3. Start the application "java -jar target/trade-validator-service-0.0.1-SNAPSHOT.jar"
 # Deploy Application in Cloud
   1. Login to heroku create application name and generate key.
   2. Open pom.xml and change the application name to registered once.
   1. Build Using command "HEROKU_API_KEY=<Heroku_Key> mvn heroku:deploy"

# Application Quality
1. This application is developed based on TDD approach, we can find all test cases related to TDD using MockMVC
2. BDD is not yet done
3. Checkstyle is integrated with build. Any violation build will fail.
4. SonarCube report : https://sonarcloud.io/dashboard?id=com.coding.trade%3Atrade-validator-service 
5. Code Coverage: Has 96% coverage.
6. Since developed using TDD, any refactoring of code will take less time and no need of regression.
7. REST Documentation: Swagger report will be avaiable on url https://trade-validation-service.herokuapp.com/swagger-ui.html

# Code detail
   # Shutdown
      1. Enabled Spring actuator for gracefull application shutdown.
      2. Implemented /api/shutdown to handle gracefull application shutdown.
  # Stats
      1. Enabled Spring actuator for metrics, Where application metrics like liveRequest also included /metrics
      2. Additionally details stats implemented under /stats
      
# Assumptions
   1. Application is designed using Single Responsibility Principle by SOLID. So additional details are created as separate service such as TradeInfoService, HolidayService etc.., which provides hardcoded values. In-real this need to enchanced with database or 3rd Party API.
