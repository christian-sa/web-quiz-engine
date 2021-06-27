# Web Quiz Engine
This project is a Web Quiz API built with Spring Boot. I developed it for the JetBrains Academy
[HyperSkill](https://hyperskill.org/) and plan to expand it in the future. Currently, the API is very bare-bones without any Frontend Implementation. All operations, except registering a user, are protected by basic authentication. You can use [Reqbin](https://reqbin.com/) to test out the API. You always need to include Basic Auth in your Request with the provided admin account, or your custom user account. Admin account to access all features: email = "superuser@mail.com", password = "admin".
#### Requirements for submitting a valid Quiz
- JSON Body:
  >{ \
     "title": "Coffee drinks", \
     "text": "Select only coffee drinks.", \
     "options": ["Americano","Tea","Cappuccino","Sprite"], \
     "answer": [0, 2] \
   }
- Title & text can't be null
- Options array must have at least two values
- Answer array can be null (all options are wrong)

## Features & Endpoints
#### Unauthorized
- Connect to H2 Console
  > POST: localhost:8080/h2-console \
  > username = "sa", password = "" (empty)
- Register yourself as a User
  > POST: localhost:8080/api/register \
  > JSON Body with keys *email* & *password*
#### User
- Post Quiz
  > POST: localhost:8080/api/quizzes \
  > JSON Body with valid Quiz
- Solve Quiz
  > POST: localhost:8080/api/quizzes/{id}/solve \
  > JSON Body with valid Quiz
- Get Quiz by ID
  > GET: localhost:8080/api/quizzes/{id} 
- Get all Quizzes
  > GET: localhost:8080/api/quizzes \
  > Query Strings: page, pagesize 
- Delete Quiz by ID
  > DELETE: localhost:8080/api/quizzes/{id} \
  > Need to be creator of the Quiz
- Update Quiz by ID
  > PUT: localhost:8080/api/quizzes/{id} \
  > JSON Body with valid Quiz \
  > Need to be creator of the Quiz
- Get all completed Quizzes by me
  > PUT: localhost:8080/api/quizzes/completed \
  > Query Strings: page, pagesize                                   
#### Admin
- Get all registered Users
  > GET: localhost:8080/api/admin/users
- Get all completed Quizzes by specified user
  > GET: localhost:/api/admin/{username}/completed \
  > Query Strings: page, pagesize     

## To do:
- Improve documentation & clean up code
- Improve feature of updating a quiz: currently the Quiz' ID that is being updated isn't
being translated to the new Quiz: It has a different ID, even though it replaces the old quiz which
gets deleted. Need to create a custom ID Generator for that.
- Implement Frontend (HTML, CSS, JS)
