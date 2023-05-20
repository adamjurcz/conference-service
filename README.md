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

# Example of requests with preloaded edgecases in database
## User endpoint
### 1. Get all users - GET
Describe: Getting all existing users from database

URL: `/api/v1/users`

-----------------------
#### Correct case

example cURL: `curl --location 'http://localhost:8080/api/v1/users'`

Response header:
- 200 OK

Response body:

<img width="235" alt="p1" src="https://github.com/adamjurcz/conference-service/assets/60117128/ec8d84c5-4997-46cb-9bfb-c1b5b09eb0d8">

---------------------

### 2. Get user reservations - GET
Describe: Getting all reservations made by one user

URL: `/api/v1/users/{login}/reservations`

-------------------------
#### Correct case

example cURL: `curl --location 'http://localhost:8080/api/v1/users/mieszekleszek/reservations'`

Response header:
- 200 OK

Response body: 

<img width="331" alt="p2" src="https://github.com/adamjurcz/conference-service/assets/60117128/46253629-bd19-481b-a7ef-955541006c45">

-------------------

### 3. Edit user email - PUT
Describe: User can edit his email by providing login and new email

URL: `/api/v1/users/{login}/edits/{newEmail}`

-----------------------
#### Correct case

example cURL: `curl --location --request PUT 'http://localhost:8080/api/v1/users/mieszekleszek/edits/adam@wp.pl'`

Response header:
- 200 OK

Response body: 

<img width="187" alt="p3" src="https://github.com/adamjurcz/conference-service/assets/60117128/0eea703b-6b63-4163-a8f3-e55fe334c25f">

----------------------
#### Incorrect email

example cURL: `curl --location --request PUT 'http://localhost:8080/api/v1/users/mieszekleszek/edits/adamw.pl'`

Response header:
- 406 NOT ACCEPTABLE

Response body:

<img width="339" alt="p4" src="https://github.com/adamjurcz/conference-service/assets/60117128/611e8312-3b68-476f-a051-be263d6d1d46">

-------------------





 
