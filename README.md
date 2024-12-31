# MovieRater-API

###

<div>
  <img src="https://cdn.simpleicons.org/spring/6DB33F" height="40" alt="spring logo"  />
  <img width="12" />
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg" height="40" alt="java logo"  />
  <img width="12" />
  <img src="https://cdn.simpleicons.org/docker/2496ED" height="40" alt="docker logo"  />
</div>

###

## 🖥️About the Project

<p>The project is a case study on the creation of APIs, emphasizing the use of Java best practices with the Spring framework. These practices include customized exception handling to avoid excessive use of try/catch blocks, unit testing for methods defined in the service layer of the application, explicit relationships between database entities, and the use of Data Transfer Objects (DTOs) to prevent direct database access.</p> 
<p>The example chosen for this study was a movie rating system, featuring entities such as User and Movie, along with their relationships.</p>

## ⚙️Functionalities

<p>All the classes will represent entities (tables) in our database, so we have created classes to manipulate the data in those table, specifically Repository and Service.<p>
<p>The classes also need DTO classes, to avoid access the entity directly.</p>

### User
- The User class can receive the user's username, name, email and password, and the user class also create a UUID (Universal Unique Identifier) for the user.
- User will have these relationships: 

| **Relationship** | **Name**           | **Table**  |
|------------------|--------------------|------------|
| Many to Many     | user_watchedMovies | tb_movie   |
| Many to Many     | user_watchList     | tb_movie   |
| One to Many      | reviews            | tb_reviews |

### Movie

- The Movie class can receive the movie name, the studio name and a list of cast, the movie class also auto increment a Long type identifier.
- Movie final grade will be calculated every time a review was created.
- Movie will have these relationships:

| **Relationship** | **Name**       | **Table**  |
|------------------|----------------|------------|
| Many to One      | studio         | tb_studio  |
| Many to Many     | usersWatched   | tb_user    |
| Many to Many     | usersWatchList | tb_user    |
| Many to Many     | movies_cast    | tb_artist  |
| One to Many      | reviews        | tb_reviews |

### Artist
- The Artist class only can receive the artist name, the artist class also auto increment a Long type identifier.
- Artist will have these relationships:

| **Relationship** | **Name** | **Table** |
|------------------|----------|-----------|
| Many to Many     | movies   | tb_movie  |

### Studio
- The Studio class only can receive the studio name, the studio class also auto increment a Long type identifier.
- Studio will have these relationships:

| **Relationship** | **Name** | **Table** |
|------------------|----------|-----------|
| Many to Many     | movies   | tb_movie  |

### Review
- The Review class can receive a user username, a movie name, a grade from the film watched and a content of review, the studio class also auto increment a Long type identifier.
- Review will have these relationships:

| **Relationship** | **Name** | **Table** |
|------------------|----------|-----------|
| Many to One      | user     | tb_user   |
| Many to One      | movie    | tb_movie  |


## 💻Technologies

The technologies used were:
- [Java](https://www.java.com/pt-BR/)
- [Spring](https://spring.io)
- [Spring Data JPA](https://docs.spring.io/spring-data/jpa/reference/index.html)
- [JUnit](https://junit.org/junit5/)
- [Mockito](https://site.mockito.org)
- [Docker](https://www.docker.com)
- [MySQL](https://www.mysql.com)

## 🛠️Installation

1. Clone repository:
````git bash
git clone https://github.com/devMarceloCimadon/MovieRater-API.git
````
2. Install dependencies with Maven.
3. Start Docker application:
````yaml
docker compose up
````

## 🔧Usage

1. Start dependencies with Maven.
2. The API will be accessible at http://localhost:8080

## 🌐API Endpoints

All the paths from API start with: ` /v1 `

## User

| **Actions**          | **Type** | **Path**                  |
|----------------------|----------|---------------------------|
| Create User          | Post     | /user                     |
| Get User by name     | Get      | /user/name/{userName}     |
| Get User by username | Get      | /user/name/{userUsername} |
| List Users           | Get      | /user/users               |
| Update User by ID    | Put      | /user/{userId}            |
| Delete User by ID    | Delete   | /user/{userId}            |

### Create User 
Body:

````json
{
  "username": "devMarceloCima2",
  "name": "Marcelo Cimadon",
  "email": "marcelo@gmail.com",
  "password": "password123"
}
````

Status:

| **Code** | **Status**  | **Reason**                              |
|----------|-------------|-----------------------------------------|
| 201      | Created     | The user was successfully created.      |
| 400      | Bad Request | User not created.                       |
| 409      | Conflict    | User with this username already exists. |

### Get User by name
Response body:
````json
[
  {
    "username": "devMarceloCima2",
    "name": "Dev Marcelo",
    "email": "marcelo@gmail.com",
    "watchedMovies": [],
    "watchList": [],
    "reviews": [
      {
        "user": {
            "username": "devMarceloCima2",
            "name": "Dev Marcelo"
        },
        "movie": {
          "name": "Demolition",
          "description": "Davis (Jake Gyllenhaal) seemingly has the perfect life until a tragic car accident leaves him struggling to cope with the loss of his wife. When Davis takes a literal approach to his father-in-law’s (Chris Cooper) metaphorically-intended words of support, he begins demolishing the pieces of his life, one item at a time, searching for what’s truly important",
          "studio": {
            "name": "Sony Pictures"
          },
          "cast": [
            {
              "name": "Jake Gyllenhaal"
            }
          ],
          "finalGrade": 7.0
        },
        "grade": 7.0,
        "content": "content",
        "creationTimestamp": "2024-12-28T13:37:35.771395Z"
      }
    ]
  }
]
````
Status:

| **Code** | **Status** | **Reason**                                                    |
|----------|------------|---------------------------------------------------------------|
| 200      | Ok         | A list of Users with the same name was successfully returned. |
| 404      | Not found  | User was not found.                                           |

### Get User by username
Response body:
````json
{
  "username": "devMarceloCima2",
  "name": "Dev Marcelo",
  "email": "marcelo@gmail.com",
  "watchedMovies": [],
  "watchList": [],
  "reviews": [
    {
      "user": {
        "username": "devMarceloCima2",
        "name": "Dev Marcelo"
      },
      "movie": {
        "name": "Demolition",
        "description": "Davis (Jake Gyllenhaal) seemingly has the perfect life until a tragic car accident leaves him struggling to cope with the loss of his wife. When Davis takes a literal approach to his father-in-law’s (Chris Cooper) metaphorically-intended words of support, he begins demolishing the pieces of his life, one item at a time, searching for what’s truly important",
        "studio": {
          "name": "Sony Pictures"
        },
        "cast": [
          {
            "name": "Jake Gyllenhaal"
          }
        ],
        "finalGrade": 7.0
      },
      "grade": 7.0,
      "content": "content",
      "creationTimestamp": "2024-12-28T13:37:35.771395Z"
    }
  ]
}
````
Status:

| **Code** | **Status** | **Reason**                          |
|----------|------------|-------------------------------------|
| 200      | Ok         | The User was successfully returned. |
| 404      | Not found  | User was not found.                 |

### List Users
Response body:
````json
[
  {
    "username": "devMarceloCima2",
    "name": "Dev Marcelo",
    "email": "marcelo@gmail.com",
    "watchedMovies": [],
    "watchList": [],
    "reviews": [
      {
        "user": {
          "username": "devMarceloCima2",
          "name": "Dev Marcelo"
        },
        "movie": {
          "name": "Demolition",
          "description": "Davis (Jake Gyllenhaal) seemingly has the perfect life until a tragic car accident leaves him struggling to cope with the loss of his wife. When Davis takes a literal approach to his father-in-law’s (Chris Cooper) metaphorically-intended words of support, he begins demolishing the pieces of his life, one item at a time, searching for what’s truly important",
          "studio": {
            "name": "Sony Pictures"
          },
          "cast": [
            {
              "name": "Jake Gyllenhaal"
            }
          ],
          "finalGrade": 7.0
        },
        "grade": 7.0,
        "content": "content",
        "creationTimestamp": "2024-12-28T13:37:35.771395Z"
      }
    ]
  }
]
````
Status:

| **Code** | **Status** | **Reason**                                 |
|----------|------------|--------------------------------------------|
| 200      | Ok         | A list of Users was successfully returned. |
| 404      | Not found  | No one User was found.                     |

### Update User by ID
Body:
````json
{
  "name" : "Dev Marcelo",
  "password" : "newPassword123"
}
````
Status:

| **Code** | **Status** | **Reason**                     |
|----------|------------|--------------------------------|
| 200      | Ok         | User was successfully updated. |
| 404      | Not found  | User was not found.            |

### Delete User by ID
Status:

| **Code** | **Status** | **Reason**                     |
|----------|------------|--------------------------------|
| 200      | Ok         | User was successfully deleted. |
| 404      | Not found  | User was not found.            |

## Movie

| **Actions**       | **Type** | **Path**           |
|-------------------|----------|--------------------|
| Create Movie      | Post     | /movie             |
| Get Movie by name | Get      | /movie/{movieName} |
| List Movies       | Get      | /movie/movies      |

### Create Movie

Body:
````json
{
    "name": "Demolition",
    "description": "Davis (Jake Gyllenhaal) seemingly has the perfect life until a tragic car accident leaves him struggling to cope with the loss of his wife. When Davis takes a literal approach to his father-in-law’s (Chris Cooper) metaphorically-intended words of support, he begins demolishing the pieces of his life, one item at a time, searching for what’s truly important",
    "studio": "Sony Pictures",
    "cast": ["Jake Gyllenhaal"]
}
````

Status:

| **Code** | **Status**  | **Reason**                      |
|----------|-------------|---------------------------------|
| 201      | Created     | Movie was successfully created. |
| 400      | Bad Request | Movie not created.              |

### Get Movie by name

Response body:

````json
{
    "name": "Demolition",
    "description": "Davis (Jake Gyllenhaal) seemingly has the perfect life until a tragic car accident leaves him struggling to cope with the loss of his wife. When Davis takes a literal approach to his father-in-law’s (Chris Cooper) metaphorically-intended words of support, he begins demolishing the pieces of his life, one item at a time, searching for what’s truly important",
    "studio": {
        "name": "Sony Pictures"
    },
    "cast": [
        {
            "name": "Jake Gyllenhaal"
        }
    ],
    "finalGrade": 7.0
}
````

Status:

| **Code** | **Status** | **Reason**                       |
|----------|------------|----------------------------------|
| 200      | Ok         | Movie was successfully returned. |
| 404      | Not found  | Movie was not found.             |

### List Movies

Response body:
````json
[
    {
        "name": "Demolition",
        "description": "Davis (Jake Gyllenhaal) seemingly has the perfect life until a tragic car accident leaves him struggling to cope with the loss of his wife. When Davis takes a literal approach to his father-in-law’s (Chris Cooper) metaphorically-intended words of support, he begins demolishing the pieces of his life, one item at a time, searching for what’s truly important",
        "studio": {
            "name": "Sony Pictures"
        },
        "cast": [
            {
                "name": "Jake Gyllenhaal"
            }
        ],
        "finalGrade": 7.0
    }
]
````
Status:

| **Code** | **Status** | **Reason**                                  |
|----------|------------|---------------------------------------------|
| 200      | Ok         | A list of Movies was successfully returned. |
| 404      | Not found  | No one Movie was found.                     |

## Artist

| **Actions**        | **Type** | **Path**             |
|--------------------|----------|----------------------|
| Create Artist      | Post     | /artist              |
| Get Artist by name | Get      | /artist/{artistName} |
| List Artists       | Get      | /artist/artists      |

### Create Artist

Body:
````json
{
    "name": "Jake Gyllenhaal"
}
````
Status:

| **Code** | **Status**  | **Reason**                       |
|----------|-------------|----------------------------------|
| 201      | Created     | Artist was successfully created. |
| 400      | Bad Request | Artist not created.              |

### Get Artist by name

Response body:

````json
{
    "name": "Jake Gyllenhaal",
    "movies": [
        {
            "name": "Demolition",
            "description": "Davis (Jake Gyllenhaal) seemingly has the perfect life until a tragic car accident leaves him struggling to cope with the loss of his wife. When Davis takes a literal approach to his father-in-law’s (Chris Cooper) metaphorically-intended words of support, he begins demolishing the pieces of his life, one item at a time, searching for what’s truly important",
            "studio": {
                "name": "Sony Pictures"
            },
            "cast": [
                {
                    "name": "Jake Gyllenhaal"
                }
            ],
            "finalGrade": 7.0
        }
    ]
}
````

Status:

| **Code** | **Status** | **Reason**                        |
|----------|------------|-----------------------------------|
| 200      | Ok         | Artist was successfully returned. |
| 404      | Not found  | Artist was not found.             |

### List Artists

Response body:

````json
[
    {
        "name": "Jake Gyllenhaal",
        "movies": [
            {
                "name": "Demolition",
                "description": "Davis (Jake Gyllenhaal) seemingly has the perfect life until a tragic car accident leaves him struggling to cope with the loss of his wife. When Davis takes a literal approach to his father-in-law’s (Chris Cooper) metaphorically-intended words of support, he begins demolishing the pieces of his life, one item at a time, searching for what’s truly important",
                "studio": {
                    "name": "Sony Pictures"
                },
                "cast": [
                    {
                        "name": "Jake Gyllenhaal"
                    }
                ],
                "finalGrade": 7.0
            }
        ]
    }
]
````

Status:

| **Code** | **Status** | **Reason**                                   |
|----------|------------|----------------------------------------------|
| 200      | Ok         | A list of Artists was successfully returned. |
| 404      | Not found  | No one Artist was found.                     |

## Studio

| **Actions**        | **Type** | **Path**             |
|--------------------|----------|----------------------|
| Create Studio      | Post     | /studio              |
| Get Studio by name | Get      | /studio/{studioName} |
| List Studios       | Get      | /studio/studios      |

### Create Studio

Body:

````json
{
    "name": "Sony Pictures"
}
````

Status:

| **Code** | **Status**  | **Reason**                       |
|----------|-------------|----------------------------------|
| 201      | Created     | Studio was successfully created. |
| 400      | Bad Request | Studio not created.              |

### Get Studio by name

Response body:

````json
{
    "name": "Sony Pictures",
    "movies": [
        {
            "name": "Demolition",
            "description": "Davis (Jake Gyllenhaal) seemingly has the perfect life until a tragic car accident leaves him struggling to cope with the loss of his wife. When Davis takes a literal approach to his father-in-law’s (Chris Cooper) metaphorically-intended words of support, he begins demolishing the pieces of his life, one item at a time, searching for what’s truly important",
            "studio": {
                "name": "Sony Pictures"
            },
            "cast": [
                {
                    "name": "Jake Gyllenhaal"
                }
            ],
            "finalGrade": 7.0
        }
    ]
}
````

Status:

| **Code** | **Status** | **Reason**                        |
|----------|------------|-----------------------------------|
| 200      | Ok         | Studio was successfully returned. |
| 404      | Not found  | Studio was not found.             |

### List Studios

Response body:

````json
[
    {
        "name": "Sony Pictures",
        "movies": [
            {
                "name": "Demolition",
                "description": "Davis (Jake Gyllenhaal) seemingly has the perfect life until a tragic car accident leaves him struggling to cope with the loss of his wife. When Davis takes a literal approach to his father-in-law’s (Chris Cooper) metaphorically-intended words of support, he begins demolishing the pieces of his life, one item at a time, searching for what’s truly important",
                "studio": {
                    "name": "Sony Pictures"
                },
                "cast": [
                    {
                        "name": "Jake Gyllenhaal"
                    }
                ],
                "finalGrade": 7.0
            }
        ]
    }
]
````

Status:

| **Code** | **Status** | **Reason**                                   |
|----------|------------|----------------------------------------------|
| 200      | Ok         | A list of Studios was successfully returned. |
| 404      | Not found  | No one Studio was found.                     |

## Review

| **Actions**             | **Type** | **Path**           |
|-------------------------|----------|--------------------|
| Create Review           | Post     | /review            |
| List Reviews by User ID | Get      | /review/{userId}   |
| Update Review by ID     | Put      | /review/{reviewId} |
| Delete Review by ID     | Delete   | /review/{reviewId} |

### Create Review

Body:

````json
{
    "userUsername": "devMarceloCima2",
    "movieName": "Demolition",
    "grade": 7.0,
    "content": "content"
}
````

Status:

| **Code** | **Status**  | **Reason**                       |
|----------|-------------|----------------------------------|
| 201      | Created     | Review was successfully created. |
| 400      | Bad Request | Review not created.              |

### List Reviews by User ID

Response body:

````json
[
  {
    "user": {
      "username": "devMarceloCima2",
      "name": "Dev Marcelo"
    },
    "movie": {
      "name": "Demolition",
      "description": "Davis (Jake Gyllenhaal) seemingly has the perfect life until a tragic car accident leaves him struggling to cope with the loss of his wife. When Davis takes a literal approach to his father-in-law’s (Chris Cooper) metaphorically-intended words of support, he begins demolishing the pieces of his life, one item at a time, searching for what’s truly important",
      "studio": {
        "name": "Sony Pictures"
      },
      "cast": [
        {
          "name": "Jake Gyllenhaal"
        }
      ],
      "finalGrade": 7.16667
    },
    "grade": 7.0,
    "content": "content",
    "creationTimestamp": "2024-12-28T13:37:35.771395Z"
  }
]
````

Status:

| **Code** | **Status** | **Reason**                                   |
|----------|------------|----------------------------------------------|
| 200      | Ok         | A list of Reviews was successfully returned. |
| 404      | Not found  | Not found Reviews with this User Id.         |

### Update Review by ID

Body:

````json
{
    "grade": 8.0,
    "content": "content"
}
````

Status:

| **Code** | **Status** | **Reason**                       |
|----------|------------|----------------------------------|
| 200      | Ok         | Review was successfully updated. |
| 404      | Not found  | Review was not found.            |

### Delete Review by ID

Status:

| **Code** | **Status** | **Reason**                       |
|----------|------------|----------------------------------|
| 200      | Ok         | Review was successfully deleted. |
| 404      | Not found  | Review was not found.            |

## ☝️🤓Author

Developed by [Marcelo Cimadon](https://github.com/devMarceloCimadon)