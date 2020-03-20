# Second Variant
This simple app download data about posts from https://jsonplaceholder.typicode.com/posts​ and connect them with data about users https://jsonplaceholder.typicode.com/users

## GET /countUsersPosts
* Count how much posts wrote users and return number of them as List of String "user_name" napisał count postów.

## GET /checkUniquePosts
* Check if title of post is unique and return list of repeated titles

## GET /nearestUser
* For all users find other user who lives closest to him

## How to run
* mvn spring-boot:run
* go to: [http://localhost:8080/countUsersPosts](http://localhost:8080/countUsersPosts) to see result for all user or
    [http://localhost:8080/checkUniqePosts](http://localhost:8080/checkUniqePosts) to check if title of post is unique and return list of repeated titles
    [http://localhost:8080/nearestUser](http://localhost:8080/nearestUser) For all users find other user who lives closest to him

## Used Technologies
* Spring Boot and Spring Web
* Retrofit2
* Gradle
* JUnit4