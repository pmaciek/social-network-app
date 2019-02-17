Social Network APP
==================

### Technology stack

Social Network service has been built using the following technology stack:

- spring boot
- java8
- lombok

and uses in-memory data storage.

### API

API exposes operation for:

#### Posting message
url : /api/v1/users/{userId}/posts

method : POST

Data params : A message object , example : {"message": "message to post"}

#### Displaying posted messages
url : /api/v1/users/{userId}/posts

method : GET

#### Following users
url : /api/v1/users/{userId}/tracks/{userIdToFollow}

method : POST

Data params : none

#### Displaying followed posts
url : /api/v1/users/{userId}/tracks

method : GET


#### Limitations:
Service does not support following features:

- registering users: a user is created as soon as they post their first message
- user authentication

### Running it locally
There are two ways application can be run locally,

#### Using Maven
    mvn spring-boot:run

#### Using IntelliJ
    Run as Spring-boot application
