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
![Running the application](https://github.com/christian-sa/web-quiz-engine/blob/main/src/main/resources/gifs/run-application.gif) \
*Note: You have to navigate the shell to the project directory.* 

It will run on port `8080` by default. Currently, the application will create a fresh database
on every startup and wipe the existing one if necessary. If you would like to change these behaviours, you have to configure
`server.port=8080` and `spring.jpa.hibernate.ddl-auto=create` in the `application.properties` file.

### Security
Because most of the application is secured with HTTP Basic Auth, you have to authorize your requests with valid credentials.
You can either use the provided admin account for that to access all the functionality, or register yourself as a new user.
##### Admin account
Email: `superuser@mail.com` \
Password: `admin` 
# API Documentation
## Unauthorized
### Registering as a new user
```
POST /api/register
```

**Request JSON:**
| Parameter | Type     | Description                            |
| :-------- | :------- | :------------------------------------- |
| `email`   | `string` | Has to be a valid email address.       |
| `password`| `string` | Must be at least five characters long. |

**Example request:**
```json
POST /lohalhost:8080/api/register
```
```json
{
  "email": "testing@mail.com",
  "password": "secret"
}
```
![Registering](https://github.com/christian-sa/web-quiz-engine/blob/main/src/main/resources/gifs/registering-user.gif) 

#### Possible HTTP Status Codes
| Code       | Description                    | 
| :----------| :----------------------------- | 
| `HTTP 200` | OK.                            | 
| `HTPP 400` | Email is already taken.        | 
| `HTTP 400` | Email or password is invalid.  | 


## User
### Creating a new quiz

```
POST /api/quizzes
```

**Request JSON:**
| Parameter | Type     | Description                            |
| :-------- | :------- | :------------------------------------- |
| `title`   | `string` | **Required.**                          |
| `text`    | `string` | **Required.**                          |
| `options` | `array`  | **Required**, at least 2 options.      |
| `answer`  | `array`  | **Optional**, all answers can be wrong.|

**Example request:**
```json
POST /lohalhost:8080/api/quizzes
```
```json
{
  "title": "Minecrafts Creator",
  "text": "What is Markus Perssons nickname?",
  "options": ["Notch", "Crotch", "Cockroach"],
  "answer": [0]
}
```
*Note: In the case of all options being wrong, you would pass an empty array `"answer": []`.*

**Example response:**
```json
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

![Posting Quiz](https://github.com/christian-sa/web-quiz-engine/blob/main/src/main/resources/gifs/posting-quiz.gif) \
*Note: The answer is not included. This is also true for all the following GET requests.* 

#### Possible HTTP Status Codes
| Code       | Description                    | 
| :----------| :----------------------------- | 
| `HTTP 200` | OK.                            | 
| `HTPP 400` | Any key is invalid.            | 
| `HTTP 401` | Credentials are not valid.     | 

### Getting a quiz
```
GET /api/quizzes/{id}
```
**Path variable:**
| Variable  | Type     | Description                            |
| :-------- | :------- | :------------------------------------- |
| `id`      | `int`    | ID of the quiz.                        |

**Example request**
```json
GET /localhost:8080/api/quizzes/5
```

**Example response:**
```json
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
![Get by ID](https://github.com/christian-sa/web-quiz-engine/blob/main/src/main/resources/gifs/get-quiz-by-id.gif) 

#### Possible HTTP Status Codes
| Code       | Description                    | 
| :----------| :----------------------------- | 
| `HTTP 200` | OK.                            | 
| `HTPP 401` | Credentials are not valid.     | 
| `HTTP 404` | Quiz was not found.            | 

### Getting all quizzes (with paging)
```
GET /api/quizzes
```
**Query parameter:**
| Parameter | Type     | Description                            |
| :-------- | :------- | :------------------------------------- |
| `page`    | `int`    | **Default: 0**, index of the page.     |
| `pagesize`| `int`    | **Default: 10**, number of quizzes on a page.|

**Example request:**
```json
GET /localhost:8080/api/quizzes?page=0&pagesize=3
```
**Example response:**
```json
{
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
![Get all quizzes](https://github.com/christian-sa/web-quiz-engine/blob/main/src/main/resources/gifs/get-all-quizzes.gif) 

#### Possible HTTP Status Codes
| Code       | Description                    | 
| :----------| :----------------------------- | 
| `HTTP 200` | OK.                            | 
| `HTPP 401` | Credentials are not valid.     | 

### Solving a quiz
```
POST /api/quizzes/{id}/solve
```
**Path variable:**
| Variable  | Type     | Description                            |
| :-------- | :------- | :------------------------------------- |
| `id`      | `int`    | ID of the quiz.                        |

**Request JSON:**
| Parameter | Type     | Description                            |
| :-------- | :------- | :------------------------------------- |
| `answer`  | `array`  | Array containing indices of correct answers.|

**Example request:**
```json
POST /localhost:8080/api/quizzes/2/solve
```
```json
[0, 2]
```
*Note: If all options are wrong, you would pass an empty array `[]`.*

**Example response:**

If the answer was **correct**...
```json
{
  "success": true,
  "feedback": "Congratulations, you're right!"
}
```
If the answer was **wrong**...
```json
{
  "success": false,
  "feedback": "Wrong answer! Please, try again."
}
```
![Solving Quiz](https://github.com/christian-sa/web-quiz-engine/blob/main/src/main/resources/gifs/solving-quiz.gif) 

##### Possible HTTP Status Codes
| Code       | Description                    | 
| :----------| :----------------------------- | 
| `HTTP 200` | OK.                            | 
| `HTPP 401` | Credentials are not valid.     | 
| `HTPP 404` | Quiz was not found.            | 

### Updating a quiz
```
PUT /api/quizzes/{id}
```
**Path variable:**
| Variable  | Type     | Description                            |
| :-------- | :------- | :------------------------------------- |
| `id`      | `int`    | ID of the quiz.                        |

**Request JSON:**
| Parameter | Type     | Description                            |
| :-------- | :------- | :------------------------------------- |
| `title`   | `string` | **Required.**                          |
| `text`    | `string` | **Required.**                          |
| `options` | `array`  | **Required**, at least 2 options.      |
| `answer`  | `array`  | **Optional**, all answers can be wrong.|

*Note: You have to be the original creator of the quiz with the specified ID or have the admin role.*

**Example request:**
```json
PUT /localhost:8080/api/quizzes/2
```
```
{
  "title": "Coffee",
  "text": "Select only coffee",
  "options": ["Americano","Tea", "Latte", "Sprite"],
  "answer": [0, 1]
}
```
![Updating Quiz](https://github.com/christian-sa/web-quiz-engine/blob/main/src/main/resources/gifs/update-quiz.gif) \
*Note: In the case of all options being wrong, you would pass an empty array `"answer": []`.*

#### Possible HTTP Status Codes
| Code       | Description                    | 
| :----------| :----------------------------- | 
| `HTTP 200` | OK.                            | 
| `HTTP 400` | Any key is invalid.            | 
| `HTPP 401` | Credentials are not valid.     |
| `HTPP 403` | Not the creator or no admin role.|
| `HTPP 404` | Quiz was not found.            | 

### Deleting a quiz
```
DELETE /api/quizzes/{id}
```
*Note: You have to be the original creator of the quiz with the specified ID or have the admin role.*

**Path variable:**
| Variable  | Type     | Description                            |
| :-------- | :------- | :------------------------------------- |
| `id`      | `int`    | ID of the quiz.                        |

**Example request:**
```
DELETE /localhost:8080/api/quizzes/2
```
![Delete Quiz](https://github.com/christian-sa/web-quiz-engine/blob/main/src/main/resources/gifs/delete-quiz.gif) 

#### Possible HTTP Status Codes
| Code       | Description                    | 
| :----------| :----------------------------- | 
| `HTTP 204` | Quiz deleted.                  | 
| `HTPP 401` | Credentials are not valid.     |
| `HTPP 403` | Not the creator or no admin role.|
| `HTPP 404` | Quiz was not found.            | 

### Getting all quiz completions (with paging)
```
GET /api/quizzes/completed
```
**Query parameter:**
| Parameter | Type     | Description                            |
| :-------- | :------- | :------------------------------------- |
| `page`    | `int`    | **Default: 0**, index of the page.     |
| `pagesize`| `int`    | **Default: 10**, number of quizzes on a page.|

**Example request:**
```json
GET /localhost:8080/api/quizzes/completed?page=0&pagesize=5
```
*Note: You have to be sending the credentials of the user you completed the quizzes with.*

**Example response:**
```json
{
  "content": [
    {
      "createdAt": "2021-07-02T17:37:52.271991",
      "quiz_id": 3
    },
    {
      "createdAt": "2021-07-02T17:37:55.227274",
      "quiz_id": 8
    },
    {
      "createdAt": "2021-07-02T17:37:58.670042",
      "quiz_id": 5
    },
    {
      "createdAt": "2021-07-02T17:38:01.043768",
      "quiz_id": 10
    },
    {
      "createdAt": "2021-07-02T17:38:03.752342",
      "quiz_id": 12
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
![Get completions](https://github.com/christian-sa/web-quiz-engine/blob/main/src/main/resources/gifs/get-quizcompletions.gif) \
*Note: Its sorted by the time of completion (ascending).*

#### Possible HTTP Status Codes
| Code       | Description                    | 
| :----------| :----------------------------- | 
| `HTTP 204` | Quiz deleted.                  | 
| `HTPP 401` | Credentials are not valid.     |

## Admin
### Getting all registered users (with paging)
```
GET /api/admin/users
```
**Query parameter:**
| Parameter | Type     | Description                            |
| :-------- | :------- | :------------------------------------- |
| `page`    | `int`    | **Default: 0**, index of the page.     |
| `pagesize`| `int`    | **Default: 10**, number of quizzes on a page.|

**Example request:**
```json
GET /localhost:8080/api/admin/users?page=0&pagesize=5
```
**Example response:**
```json
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
      "email": "googler@mail.com",
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
![Get Users](https://github.com/christian-sa/web-quiz-engine/blob/main/src/main/resources/gifs/get-all-users.gif) 

#### Possible HTTP Status Codes
| Code       | Description                    | 
| :----------| :----------------------------- | 
| `HTTP 200` | Quiz deleted.                  | 
| `HTPP 401` | Credentials are not valid.     |
| `HTPP 403` | No admin role.                 |

### Deleting all quizzes
```
DELETE /api/admin/quizzes
```
**Example request:**
```json
DELETE /localhost:8080/api/admin/quizzes
```
![Delete all quizzes](https://github.com/christian-sa/web-quiz-engine/blob/main/src/main/resources/gifs/delete-all-quizzes.gif) 

#### Possible HTTP Status Codes
| Code       | Description                    | 
| :----------| :----------------------------- | 
| `HTTP 200` | Quiz deleted.                  | 
| `HTPP 401` | Credentials are not valid.     |
| `HTPP 403` | No admin role.                 |

### Getting all completed quizzes by user (with paging)
```
GET /api/admin/{username}/completed
```
**Path variable:**
| Variable  | Type     | Description                            |
| :-------- | :------- | :------------------------------------- |
| `username`| `string` | Username of the user.                  |

**Query parameter:**
| Parameter | Type     | Description                            |
| :-------- | :------- | :------------------------------------- |
| `page`    | `int`    | **Default: 0**, index of the page.     |
| `pagesize`| `int`    | **Default: 10**, number of quizzes on a page.|

**Example request:**
```json
GET /localhost:/api/admin/superuser@mail.com/completed?page=0&pagesize=5
```
**Example response:**
```json
{
  "content": [
    {
      "createdAt": "2021-07-02T17:56:11.134224",
      "quiz_id": 8
    },
    {
      "createdAt": "2021-07-02T17:56:14.922174",
      "quiz_id": 12
    },
    {
      "createdAt": "2021-07-02T17:56:16.883889",
      "quiz_id": 10
    },
    {
      "createdAt": "2021-07-02T17:56:19.545087",
      "quiz_id": 16
    },
    {
      "createdAt": "2021-07-02T17:56:22.072447",
      "quiz_id": 18
    }
  ],
  ...
  },
  "last": true,
  "totalPages": 1,
  "totalElements": 5,
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
![Get completions by User](https://github.com/christian-sa/web-quiz-engine/blob/main/src/main/resources/gifs/get-all-completed-by-user.gif) \
*Note: Its sorted by the time of completion (ascending).*

#### Possible HTTP Status Codes
| Code       | Description                    | 
| :----------| :----------------------------- | 
| `HTTP 200` | Quiz deleted.                  | 
| `HTPP 401` | Credentials are not valid.     |
| `HTPP 403` | No admin role.                 |
 
