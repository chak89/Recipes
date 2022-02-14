# Recipes

## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)
* [APIs](#api)
* [Examples](#examples)

## General info
Multi-user web service with Spring Boot that allows storing, retrieving, updating, and deleting recipes from H2 database. Spring Boot Security for access, authentication and authorization. 
https://hyperskill.org/projects/180
	
## Technologies
Project is created with:
* Java 11.0.2
* Gradle 6.7.1
* Spring Boot Security
* REST API
* H2 database
* Project Lombok
* Other concepts useful for the backend
	
## Setup
1. Configure which port to use and where to save the H2 database in resources/application.properties file.
```
server.port={port number}
spring.datasource.url=jdbc:h2:file:{your chosen path}
```
2. Compile and start the Spring Boot Application, preferably build and run from your IDE. main class located in RecipesApplication.class

3. To access H2 console go to 
```
http://localhost:{port number}/h2
```

## APIs
``POST /api/register`` receives a JSON object with two fields: email (string), and password (string). No authentication required.

``POST /api/recipe/new`` receives a recipe as a JSON object and returns a JSON object with one id field.
PUT /api/recipe/{id}

``GET /api/recipe/{id}`` returns a recipe with a specified id as a JSON object.

``DELETE /api/recipe/{id}`` deletes a recipe with a specified id.

``GET /api/recipe/search`` takes one of the two mutually exclusive query parameters:  
``category`` – if this parameter is specified, it returns a JSON array of all recipes of the specified category. Search is case-insensitive, sort the recipes by date (newer first);  
``name`` – if this parameter is specified, it returns a JSON array of all recipes with the names that contain the specified parameter. Search is case-insensitive, sort the recipes by date (newer first).

``POST /actuator/shutdown`` Shutdown a Spring Boot Application. No authentication required.  

For more info consult https://hyperskill.org/projects/180  

## Examples
Use Postman or other tools that can test REST API  
**Example1:** ``POST /api/register`` request without authentication
````
{
   "email": "Elon@Musk.com",
   "password": "outofspace"
}
````

**Example2:** ``POST /api/recipe/new`` request with basic authentication; email (login): Elon@Musk.com, and password: outofspace
````
{
   "name": "Fresh Mint Tea",
   "category": "beverage",
   "description": "Light, aromatic and refreshing beverage, ...",
   "ingredients": ["boiled water", "honey", "fresh mint leaves"],
   "directions": ["Boil water", "Pour boiling hot water into a mug"]
}
````
Response:
````
{
   "id": 1
}
````
