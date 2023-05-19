# Conference management service
# Introduction
This webservice rest api allows the customer to, for example:
- view the conference schedule and book a place by providing login and email,
- delete a reservation,
- change the email of reservation,
- generate a statement for the organizer:
	- summary of lectures by interest
	- summary of thematic paths by interest

Service was made by following SOLID design principles and also project is based on Clean Code architecture which was introduced by [Uncle Bob.](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)

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

# Example of requests - URLs
## User endpoint
### 1. Get all users
URL: `/api/v1/users`

Describe: Getting all users from database

# TODO REST



 
