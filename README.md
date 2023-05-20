# Conference management service
# Introduction
This webservice rest api allows the customer to, for example:
- view the conference schedule and book a place by providing login and email,
- delete a reservation,
- change the email of reservation,
- generate a statement for the organizer:
	- summary of lectures by interest
	- summary of thematic paths by interest

# Technologies
- JDK 17
- Spring Boot 3.0.6
- Maven
- Spring Data JPA

# How to run app
## 1. Using maven:
To run navigate to the root directory of the Spring Boot project and use `.\mvnw spring-boot:run` command. 
Maven will automatically build the project and run it on the built-in application server.

## 2. Using java -jar:
- navigate to the root directory of the Spring Boot project
- compile project by using `.\mvnw package`to create jar file
- last step is executing Spring Boot project with command: `java -jar target/conferenceservice-1.0.0.jar`

Now you can reach application at `http://localhost:8080`