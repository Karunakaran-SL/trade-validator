# Trade Validator Service

Sample application using Spring Boot and Angular 5 to demo validation of Trade Validation.

UI is very basic, just a simple form. 

# Building Project
1. Clone the project from git hub
2. cd /trade-validator/trade-validator-client
3. npm install
4. ng build -prod
5. Move tp Spring Boot as cd ../trade-validator-service/
6. Build Spring boot using "mvn clean install"
7. Start the application "java -jar target/trade-validator-service-0.0.1-SNAPSHOT.jar"

# Application Quality
1. This application is developed based on TDD approach, we can find all test cases related to TDD using MockMVC
2. BDD is not yet done
3. Checkstyle is integrated with build. Any violation build will fail.
4. SonarCube report : https://sonarcloud.io/dashboard?id=com.coding.trade%3Atrade-validator-service 
5. Code Coverage: Has 96% coverage.
6. Since developed using TDD, any refactoring of code will take less time and no need of regression.
7. REST Documentation: Swagger report will be avaiable on url http://localhost:8080/swagger-ui.html

