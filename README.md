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

Customer:

- retrieving all customers
- retrieving customer for id

Film:

- retrieving all videos
- retrieving video for id

Order:

- retrieving all orders
- retrieving order for id
- renting one ore several film

Rental:

- retrieving all rentals
- retrieving rental for orderId and rentalId
- returning films to store



#### Limitations:
Service does not support following features:

- defining price currency
- verifying double or multi rentals the same film
- calculating status of order(ONGOING, CLOSED)
- caching

### Running it locally
There are two ways application can be run locally,

#### Using Maven
    mvn spring-boot:run

#### Using IntelliJ
    1. Configure the gateway main Spring boot application to Run as Spring-boot application

   To check if the server is up, navigate from your browser to the URLs:
   http://localhost:8080/v1/films
   http://localhost:8080/v1/customers
