# Movie Rental Service

This is a movie rental RESTful API built with Java and using PostgreSQL as the Database

## Access

The service has been deployed on Heroku and can be reached via this [url](https://rcmovierental.herokuapp.com/)


## Login

To login a POST request must be sent to the following endpoint: https://rcmovierental.herokuapp.com/login
The payload must contain:
```json
{
    "username":"username",
    "password":"password"
}
```
For testing purposes, 2 users have already been created to login with different roles:

username: admin
password: admin

username: user
password: user

the response will contain a token with a 15 minute time limit. This token must be sent for some endpoints. It will need to be sent as a header parameter under the key name AUTHORIZATION with value BEARER {TOKEN} 

## Movie Administration

The following methods can require an ADMIN role.

###Add a movie

Endpoint: https://rcmovierental.herokuapp.com/api/v1/movies/
Description: Creates a new movie record
Type: POST 
Payload example:
```json
{
    "title": "Movie name",
    "description": "Movie Description",
    "stock": 2,
    "rentalPrice": 4.2,
    "salePrice": 6.0,
    "availability": "y",
    "images": [
        {
            "image": "https://urlOfImage.jpg"
        }
    ]
}
```
Notes:
-images collection may be sent as an empty array.
-availability values can be 'y' or 'n'
-a log entry will also be written on the LOG table
Response: JSON with movie details and a movie ID confirming creation

###Update a movie

Endpoint: https://rcmovierental.herokuapp.com/api/v1/movies/{id}
Description: Updates an existing movie
Parameter: id - ID of an existing movie
Type: PUT 
Payload example:
```json
{
    "title": "Movie name",
    "description": "Movie Description",
    "stock": 2,
    "rentalPrice": 4.2,
    "salePrice": 6.0,
    "availability": "y",
 
}
```
Notes:
-a log entry will also be written on the LOG table
-images are updated through a separate method.
Response: JSON with updated movie details and a movie ID confirming update

###Remove a movie

Endpoint: https://rcmovierental.herokuapp.com/api/v1/movies/aval/{id}
Description: changes only the availability field of an existing movie
Parameter: id - ID of an existing movie
Type: PATCH
Payload example:
```json
{
    "availability": "n"
}
```
Notes:
Response: JSON with updated movie details and a movie ID confirming update

###Delete a movie

Endpoint: https://rcmovierental.herokuapp.com/api/v1/movies/{id}
Description: Deletes an existing movie and all of its images
Parameter: id - ID of an existing movie
Type:DELETE
Notes:
Response: JSON with a message confirming movie deletion

###Add an image

Endpoint: https://rcmovierental.herokuapp.com/api/v1/movies/img/{id}
Description: adds an image to an existing movie
Parameter: id - ID of an existing movie
Type: PATCH
Payload example:
```json
{
    "images": [
        {
            "image": "https://url1.jpg"
        },
        {
            "image": "https://url2.jpg"
        }
    ]
}
```
Notes:
Response: JSON with updated movie details and a movie ID confirming update

###Delete an image

Endpoint: https://rcmovierental.herokuapp.com/api/v1/movies/img/{id}
Description: Deletes an existing image
Parameter: id - ID of an existing image
Type:DELETE
Notes:
Response: JSON with a message confirming movie deletion

## User Actions

The following methods can require a USER role.

###Like a movie

Endpoint: https://rcmovierental.herokuapp.com/api/v1/movies/like
Description: Adds a LIKE record to an existing movie
Parameter:
Type: POST 
Payload example:
```json
{
	"userId": 3,
    "movieId": 2

}
```
Response: JSON with entered details confirming proper record creation.

###Rent a movie

Endpoint: https://rcmovierental.herokuapp.com/api/v1/movies/rent
Description: Rents a movie and adds a record to RENT table
Parameter:
Type: POST 
Payload example:
```json
{
    "userId": 1,
    "movieId": 7,
    "rentDate": "2020-10-31T18:25",
    "dateReturn": "2020-11-05T18:25"
}
```
Notes:
-date values must have format "yyyy-mm-ddThh:ss" in order to avoid improper date conversion
-subtotal and total values are calculated
-a response with empty values means a dateReturn has a date that occurs before the rentDate or the movie is no longer in stock
-everytime a movie is rented, its stock decreases by one
Response: JSON with RENT record created with an ID.

###Return a rented a movie

Endpoint: https://rcmovierental.herokuapp.com/api/v1/movies/rent
Description: updates a rented movie
Parameter:
Type: PUT 
Payload example:
```json
{
    "rentId": 7,
    "userId": 1,
    "movieId": 7,
    "rentDate": "2020-10-31T18:25",
    "dateReturn": "2020-11-05T18:25",
    "actualReturn": "2020-11-05T18:25"
}
```
Notes:
-date values must have format "yyyy-mm-ddThh:ss" in order to avoid improper date conversion
-subtotal and total values are calculated
-everytime a movie is returned, its stock increases by one
Response: JSON with RENT record updated.

###Return a rented a movie

Endpoint: https://rcmovierental.herokuapp.com/api/v1/movies/rent
Description: updates a rented movie
Parameter:
Type: PUT 
Payload example:
```json
{
    "rentId": 7,
    "userId": 1,
    "movieId": 7,
    "rentDate": "2020-10-31T18:25",
    "dateReturn": "2020-11-05T18:25",
    "actualReturn": "2020-11-05T18:25"
}
```
Notes:
-date values must have format "yyyy-mm-ddThh:ss" in order to avoid improper date conversion
-subtotal and total values are calculated
-everytime a movie is returned, its stock increases by one
Response: JSON with RENT record updated.

## Author
Ronald Castellon