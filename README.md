# Web Quiz Engine
This project is a Web Quiz API built with Java 11 and Spring Boot. I developed it for the JetBrains Academy
[HyperSkill](https://hyperskill.org/) and plan to expand it in the future.
Currently, the API is very bare-bones without any Frontend Implementation.
I would advise using [Reqbin](https://reqbin.com/) or similar services to test out the API online. Alternative you could
also use a Unix Shell (i.e., Bash) on your computer and utilize `curl`. However, that is a bit cumbersome when passing a lot of data.
### Running the application
**Bash**
```
./gradlew bootRun
```
**CMD**
```
gradlew bootRun
```
It will run on port `8080` by default. Please also note that currently, the application will create a fresh database
on every startup and wipe the existing one if necessary. If you would like to change these behaviours, you have to configure
`server.port=8080` and `spring.jpa.hibernate.ddl-auto=create` in the `application.properties` file.

## Features
### Security
Because most of the application is secured with HTTP Basic Auth, you have to authorize your requests with valid credentials.
You can either use the provided admin account for that to access all the functionality, or register yourself as a new user.
##### Admin account
Email: `superuser@mail.com` \
Password: `admin` 

---
### Unauthorized
#### Registering as a new user
```
POST http://localhost:8080/api/register
```

**Request JSON:**
- `email` (string) - gets checked for validity.
- `password` (string) - must be at least five characters long.

**Example request:**
```
{
  "email": "testing@mail.com",
  "password": "secret"
}
```
##### Possible HTTP Status Codes
- `HTTP 200` user was registered successfully.
- `HTTP 400` email is already taken by another user.
- `HTTP 400` either email or password are invalid.
---
### User
#### Creating a new quiz
```
POST http://localhost:8080/api/quizzes
```

**Request JSON:**
- `title` (string) - required
- `text` (string) - required
- `options` (array) - required, must contain at least two options.
- `answer` (array) - optional, since all options can be wrong. Contains indices of correct options.

**Example request:**
```
{
  "title": "Minecrafts Creator",
  "text": "What is Markus Perssons nickname?",
  "options": ["Notch", "Crotch", "Cockroach"],
  "answer": [0]
}
```
*Note: In the case of all options being wrong, you would pass an empty array `"answer": []`.*

**Example response:**
```
{
  "id": 2,
  "version": 1,
  "createdAt": "2021-07-01T16:08:05.8023569",
  "updatedAt": "2021-07-01T16:08:05.8023569",
  "title": "Minecrafts Creator",
  "text": "What is Markus Perssons nickname?",
  "options": ["Notch", "Crotch", "Cockroach"]
}
```
*Note: The answer is not included. This is also true for all the following GET requests.*
##### Possible HTTP Status Codes
- `HTTP 200` quiz was created successfully.
- `HTTP 400` any key is invalid.
- `HTTP 401` you are not sending valid credentials.
---
#### Getting a quiz
```
GET http://localhost:8080/api/quizzes/{id}
```
**Path variable:**
- `id` (int) - ID of the quiz you want to get.

**Example request**
```
http://localhost:8080/api/quizzes/5
```

**Example response:**
```
{
  "quiz_id": 5,
  "version": 1,
  "createdAt": "2021-06-30T16:00:23.9989441",
  "updatedAt": "2021-06-30T16:00:23.9989441",
  "title": "Minecrafts Creator",
  "text": "Which is the nickname of Minecrafts creator?",
  "options": ["Notch", "Crotch", "Cockroach"]
}
```
##### Possible HTTP Status Codes
- `HTTP 200` OK.
- `HTTP 401` you are not sending valid credentials.
- `HTTP 404` quiz with the specified ID was not found.
---
#### Getting all quizzes (with paging)
```
GET http://localhost:8080/api/quizzes
```
**Query parameter:**
- `page`(int) - index of the page (default is 0).
- `pagesize` (int) - how many quizzes are on a single page (default is 10).

**Example request:**
```
http://localhost:8080/api/quizzes?page=0&pagesize=3
```
**Example response:**
```{
     "content": [
       {
         "quiz_id": 1,
         "version": 1,
         "createdAt": "2021-06-30T16:33:19.214617",
         "updatedAt": "2021-06-30T16:33:19.214617",
         "title": "Minecrafts Creator",
         "text": "Which is the nickname of Minecrafts creator?",
         "options": ["Notch", "Crotch", "Cockroach"]
       },
       {
         "quiz_id": 2,
         "version": 1,
         "createdAt": "2021-06-30T16:35:17.110354",
         "updatedAt": "2021-06-30T16:35:17.110354",
         "title": "Cat Breeds",
         "text": "Which is not a cat breed?",
         "options": ["Persian", "Siamese", "Poodle", "Sphynx"]
       },
       {
         "quiz_id": 3,
         "version": 2,
         "createdAt": "2021-06-30T16:36:31.781461",
         "updatedAt": "2021-06-30T17:58:36.185289",
         "title": "Android Logo",
         "text": "What color does the robot have?",
         "options": ["White"," Green"]
       }
     ],
     ...
     },
     "last": true,
     "totalPages": 1,
     "totalElements": 3,
     "size": 3,
     "number": 0,
     ...
     },
     "first": true,
     "numberOfElements": 3,
     "empty": false
   }

```
*Note: Some elements of the response were excluded.*
##### Possible HTTP Status Codes
- `HTTP 200` OK.
- `HTTP 401` you are not sending valid credentials.
---
#### Solving a quiz
```
POST http://localhost:8080/api/quizzes/{id}/solve
```
**Path variable:**
- `id` (int) - ID of the quiz you want to solve.

**Request JSON:**
- `answer` (array) - array of integers containing the correct answers.

**Example request:**
```
http://localhost:8080/api/quizzes/5/solve
```
```
[0, 2]
```
*Note: If all options are wrong, you would pass an empty array `[]`.*

**Example response:**

If the answer was **correct**...
```
{
  "success": true,
  "feedback": "Congratulations, you're right!"
}
```
If the answer was **wrong**...
```
{
  "success": false,
  "feedback": "Wrong answer! Please, try again."
}
```
##### Possible HTTP Status Codes
- `HTTP 200` OK.
- `HTTP 401` you are not sending valid credentials.
- `HTTP 404` quiz with the specified ID was not found.
---
#### Updating a quiz
```
PUT http://localhost:8080/api/quizzes/{id}
```
**Path variable:**
- `id` (int) - ID of the quiz you want to update.

**Request JSON:**
- `title` (string) - required
- `text` (string) - required
- `options` (array) - required, must contain at least two options.
- `answer` (array) - optional, since all options can be wrong. Contains indices of correct options.

*Note: You have to be the original creator of the quiz with the specified ID or have the admin role.*

**Example request:**
```
http://localhost:8080/api/quizzes/5
```
```
{
  "title": "Coffee",
  "text": "Select only coffee",
  "options": ["Americano","Tea", "Latte", "Sprite"],
  "answer": [0, 1]
}
```
*Note: In the case of all options being wrong, you would pass an empty array `"answer": []`.*
##### Possible HTTP Status Codes
- `HTTP 200` quiz changed successfully.
- `HTTP 400` any key is invalid.
- `HTTP 401` you are not sending valid credentials.
- `HTTP 403` you are not the creator or don't have the admin role.
- `HTTP 404` quiz with the specified ID was not found.
---
#### Deleting a quiz
```
DELETE http://localhost:8080/api/quizzes/{id}
```
**Path variable:**
- `id` (int) - ID of the quiz you want to delete.

*Note: You have to be the original creator of the quiz with the specified ID or have the admin role.*

**Example request:**
```
http://localhost:8080/api/quizzes/5
```
##### Possible HTTP Status Codes
- `HTTP 204` quiz deleted successfully.
- `HTTP 401` you are not sending valid credentials.
- `HTTP 403` you are not the creator or don't have the admin role.
- `HTTP 404` quiz with the specified ID was not found.
---
#### Getting all quiz completions (with paging)
```
GET http://localhost:8080/api/quizzes/completed
```
**Query parameter:**
- `page`(int) - index of the page (default is 0).
- `pagesize` (int) - how many completions are on a single page (default is 10).

**Example request:**
```
http://localhost:8080/api/quizzes/completed?page=0&pagesize=5
```
*Note: You have to be sending the credentials of the user you completed the quizzes with.*

**Example response:**
```
{
  "content": [
    {
      "createdAt": "2021-07-01T17:13:28.160674",
      "quiz_id": 5
    },
    {
      "createdAt": "2021-07-01T17:13:30.923315",
      "quiz_id": 9
    },
    {
      "createdAt": "2021-07-01T17:13:33.228391",
      "quiz_id": 4
    },
    {
      "createdAt": "2021-07-01T17:13:35.902169",
      "quiz_id": 7
    },
    {
      "createdAt": "2021-07-01T17:13:44.637853",
      "quiz_id": 3
    }
  ],
  ...
  },
  "last": true,
  "totalPages": 1,
  "totalElements": 5,
  "size": 10,
  "number": 0,
  "sort": {
    "sorted": true,
    "unsorted": false,
    "empty": false
  },
  "first": true,
  "numberOfElements": 5,
  "empty": false
}
```
*Note: Its sorted by the time of completion (ascending). Some elements of the response were excluded.*
##### Possible HTTP Status Codes
- `HTTP 200` OK.
- `HTTP 401` you are not sending valid credentials.
---
### Admin
#### Getting all registered users (with paging)
```
GET http://localhost:8080/api/admin/users
```
**Query parameter:**
- `page`(int) - index of the page (default is 0).
- `pagesize` (int) - how many users are on a single page (default is 10).

**Example request:**
```
http://localhost:8080/api/admin/users?page=0&pagesize=5
```
**Example response:**
```
{
  "content": [
    {
      "id": 1,
      "email": "superuser@mail.com",
      "password": "$2a$10$vNrUOUsPtey1Dx83uz8y5OEVvWIOcXZMHtNFz0PJkt7XCvYrpVjSG",
      "roles": "ROLE_ADMIN,ROLE_USER",
      "enabled": true
    },
    {
      "id": 2,
      "email": "testing@mail.com",
      "password": "$2a$10$rNRp.VV05fC7hgwS4JlDHuhagqfyazpc6Wi1UVEhFH0aRI.pVeZ4S",
      "roles": "ROLE_USER",
      "enabled": true
    },
    {
      "id": 3,
      "email": "secretagent@mail.com",
      "password": "$2a$10$KkcJyzLo1NzUH0y6Nu8c4OAQAYB9kJoSnfKmU8hIL7sUvAZ.CXpcS",
      "roles": "ROLE_USER",
      "enabled": true
    },
    {
      "id": 4,
      "email": "googlert@mail.com",
      "password": "$2a$10$X1Hf/PSUq1wSW85r47sUdO37krh8FM88KiffwwPhtQdjn890EmLx.",
      "roles": "ROLE_USER",
      "enabled": true
    },
    {
      "id": 5,
      "email": "hackerman@mail.com",
      "password": "$2a$10$CHn.zcgNLuPYh3.mmU8qbu9g51DR323tobZoIRIHrTc0LKKMmWvMy",
      "roles": "ROLE_USER",
      "enabled": true
    }
  ],
  ...
  },
  "last": false,
  "totalElements": 6,
  "totalPages": 2,
  "size": 5,
  "number": 0,
  "sort": {
    "sorted": true,
    "unsorted": false,
    "empty": false
  },
  "first": true,
  "numberOfElements": 5,
  "empty": false
}
```
*Note: Some elements of the response were excluded.*
##### Possible HTTP Status Codes
- `HTTP 200` OK.
- `HTTP 401` you are not sending valid credentials.
- `HTTP 403` you don't have the admin role.
---
#### Deleting all quizzes
```
DELETE http://localhost:8080/api/admin/quizzes
```
**Example request:**
```
http://localhost:8080/api/admin/quizzes
```
##### Possible HTTP Status Codes
- `HTTP 200` OK.
- `HTTP 401` you are not sending valid credentials.
- `HTTP 403` you don't have the admin role.
---
#### Getting all completed quizzes by user (with paging)
```
GET http://localhost:/api/admin/{username}/completed
```
**Path variable:**
- `username`(string) - username of the user

**Query parameter:**
- `page`(int) - index of the page (default is 0).
- `pagesize` (int) - how many completions are on a single page (default is 10).

**Example request:**
```
http://localhost:/api/admin/superuser@mail.com/completed?page=0&pagesize=5
```
**Example response:**
```
{
  "content": [
    {
      "createdAt": "2021-07-01T17:13:28.160674",
      "quiz_id": 5
    },
    {
      "createdAt": "2021-07-01T17:13:30.923315",
      "quiz_id": 9
    },
    {
      "createdAt": "2021-07-01T17:13:33.228391",
      "quiz_id": 4
    },
    {
      "createdAt": "2021-07-01T17:13:35.902169",
      "quiz_id": 7
    },
    {
      "createdAt": "2021-07-01T17:13:44.637853",
      "quiz_id": 3
    }
  ],
  ...
  },
  "last": true,
  "totalPages": 1,
  "totalElements": 5,
  "size": 10,
  "number": 0,
  "sort": {
    "sorted": true,
    "unsorted": false,
    "empty": false
  },
  "first": true,
  "numberOfElements": 5,
  "empty": false
}
```
*Note: Its sorted by the time of completion (ascending). Some elements of the response were excluded.*
##### Possible HTTP Status Codes
- `HTTP 200` OK.
- `HTTP 401` you are not sending valid credentials.
- `HTTP 403` you don't have the admin role.
