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
- H2 database

# How to run app
## 1. Using maven:
To run navigate to the root directory of the Spring Boot project and use `.\mvnw spring-boot:run` command. 
Maven will automatically build the project and run it on the built-in application server.

## 2. Using java -jar:
- navigate to the root directory of the Spring Boot project
- compile project by using `.\mvnw package`to create jar file
- last step is executing Spring Boot project with command: `java -jar target/conferenceservice-1.0.0.jar`

Now you can reach application at `http://localhost:8080`.
You can also run unit tests by typing `mvn test` in console.

# Example of requests with preloaded edgecases in database
You can test endpoints by provided documentation by swagger at 
`http://localhost:8080/swagger-ui/index.html`, or you can enter requests written below in postman.

## User endpoint
### 1. Get all users - GET
Describe: Getting all existing users from database

URL: `/api/v1/users`

### Correct case

example cURL: `curl --location 'http://localhost:8080/api/v1/users'`

Response header:
- 200 OK

Response body:

<img width="250" alt="p1" src="https://github.com/adamjurcz/conference-service/assets/60117128/ec8d84c5-4997-46cb-9bfb-c1b5b09eb0d8">

---------------------
### 2. Get user reservations - GET
Describe: Getting all reservations made by one user

URL: `/api/v1/users/{login}/reservations`

### Correct case

example cURL: `curl --location 'http://localhost:8080/api/v1/users/mieszekleszek/reservations'`

Response header:
- 200 OK

Response body: 

<img width="350" alt="p2" src="https://github.com/adamjurcz/conference-service/assets/60117128/46253629-bd19-481b-a7ef-955541006c45">

------------------------
### 3. Edit user email - PUT
Describe: User can edit his email by providing login and new email

URL: `/api/v1/users/{login}/edits/{newEmail}`

### Correct case

example cURL: `curl --location --request PUT 'http://localhost:8080/api/v1/users/mieszekleszek/edits/adam@wp.pl'`

Response header:
- 200 OK

Response body: 

<img width="250" alt="p3" src="https://github.com/adamjurcz/conference-service/assets/60117128/0eea703b-6b63-4163-a8f3-e55fe334c25f">

### Incorrect email format

example cURL: `curl --location --request PUT 'http://localhost:8080/api/v1/users/mieszekleszek/edits/adamw.pl'`

Response header:
- 406 NOT ACCEPTABLE

Response body:

<img width="390" alt="p4" src="https://github.com/adamjurcz/conference-service/assets/60117128/611e8312-3b68-476f-a051-be263d6d1d46">

### New email is already taken

example cURL: `curl --location --request PUT 'http://localhost:8080/api/v1/users/mieszekleszek/edits/adassmieszek@onet.pl'`

Response header:
- 409 CONFLICT

Response body:

<img width="420" alt="p5" src="https://github.com/adamjurcz/conference-service/assets/60117128/0ceef518-4972-4b37-9d71-b7c3167087c9">

### Login is not existing

example cURL: `curl --location --request PUT 'http://localhost:8080/api/v1/users/costamcostam/edits/adamos@wp.pl'`

Response header:
- 404 NOT FOUND

Response body:

<img width="370" alt="p6" src="https://github.com/adamjurcz/conference-service/assets/60117128/ad6022ed-160a-45cb-978f-6f0f33815216">

--------------------------------
## Lecture endpoint
### 1. Get lectures interest - GET
Describe: The organizer can collect the percentage of participation in lectures, values of participation is rounded to 2 decimal numbers

URL: `/api/v1/lectures/interests`

### Correct case

example cURL: `curl --location 'http://localhost:8080/api/v1/lectures/interests'`

Response header:
- 200 OK

Response body:

<img width="750" alt="p7" src="https://github.com/adamjurcz/conference-service/assets/60117128/09de693f-4777-4fef-983f-49aeb6db6a99">

-----------------------------------
### 2. Get lecture paths interest - GET
Describe: The organizer can collect the percentage of participation in lecture paths, values of participation is rounded to 2 decimal numbers

URL: `/api/v1/lectures/interests/paths`

#### Correct case

example cURL: `curl --location 'http://localhost:8080/api/v1/lectures/interests/paths'`

Response header:
- 200 OK

Response body:

<img width="240" alt="p8" src="https://github.com/adamjurcz/conference-service/assets/60117128/c92b013e-c930-42e8-a6be-f8e4f86ee397">

-------------------------------------------------
### 3. Get all lectures - GET
Describe: Getting all lectures from database

URL: `/api/v1/lectures`

### Correct case

example cURL: `curl --location 'http://localhost:8080/api/v1/lectures'`

Response header:
- 200 OK

Response body:

<img width="331" alt="p9" src="https://github.com/adamjurcz/conference-service/assets/60117128/94e18d21-17b1-4118-8ecf-9d51c81c7f5e">

----------------------------------------------
### 4. Create lecture reservation - POST
Describe: User can book a lecture/only if there is seat avaible and he didn't book the same hour. If reservation will be successful, an email will be sent (saving to file)

URL: `/api/v1/lectures/{lectureId}/reservations`
Request body: `{"login": "{LOGIN}", "email": "{EMAIL}"}`

### Correct case

example cURL:
```
curl --location 'http://localhost:8080/api/v1/lectures/8/reservations' \
--header 'Content-Type: application/json' \
--data-raw '{
    "login": "mieszekleszek",
    "email": "mieszekleszek@wp.pl"
}'
```

Response header:
- 200 OK

Response Body:

<img width="290" alt="p10" src="https://github.com/adamjurcz/conference-service/assets/60117128/c5c5a720-e36a-4d21-9a8c-bb45fc8eb80e">

### Lecture has maximum listeners

example cURL:
```
curl --location 'http://localhost:8080/api/v1/lectures/9/reservations' \
--header 'Content-Type: application/json' \
--data-raw '{
    "login": "mieszekleszek",
    "email": "mieszekleszek@wp.pl"
}'
```

Response header:
- 409 CONFLICT

Response body:

<img width="350" alt="p11" src="https://github.com/adamjurcz/conference-service/assets/60117128/bbf9e07b-60b7-4685-b5a8-8052b6742607">

### User already has booked this hour

example cURL:
```
curl --location 'http://localhost:8080/api/v1/lectures/1/reservations' \
--header 'Content-Type: application/json' \
--data-raw '{
    "login": "mieszekleszek",
    "email": "mieszekleszek@wp.pl"
}'
```

Response header:
- 406 NOT ACCEPTABLE

Response body:

<img width="430" alt="p12" src="https://github.com/adamjurcz/conference-service/assets/60117128/e4ec1464-ba35-4481-9ce1-92a7d8868720">

### Login is already used by someone

example cURL:
```
curl --location 'http://localhost:8080/api/v1/lectures/8/reservations' \
--header 'Content-Type: application/json' \
--data-raw '{
    "login": "mieszekleszek",
    "email": "toja@wp.pl"
}'
```

Response header:
- 409 CONFLICT

Response body:

<img width="380" alt="p13" src="https://github.com/adamjurcz/conference-service/assets/60117128/2d15a35c-0d4c-4c2f-b974-492ac73ae609">

------------------
### 5. Delete lecture reservation
Describe: User can delete lecture reservation by providing his login and lecture_id

URL: `/{lectureId}/reservations/{login}`

### Correct case

example cURL: `curl --location --request DELETE 'http://localhost:8080/api/v1/lectures/2/reservations/mieszekleszek'`

Response header:
- 200 OK

Response body:
- Empty

### Login is not existing

example cURL: `curl --location --request DELETE 'http://localhost:8080/api/v1/lectures/2/reservations/fdajifdaji'`

Response header:
- 404 NOT FOUND

Response body:

<img width="360" src="https://github.com/adamjurcz/conference-service/assets/60117128/72c1692f-a3a2-47de-9728-f74913b1b198">

### User did not book this lecture

example cURL: `curl --location --request DELETE 'http://localhost:8080/api/v1/lectures/9/reservations/mieszekleszek'`

Response header:
- 404 NOT FOUND

Response body:

<img width="446" src="https://github.com/adamjurcz/conference-service/assets/60117128/d3dd547d-1eea-42b9-9bf1-2c4f46a7163f">












 
