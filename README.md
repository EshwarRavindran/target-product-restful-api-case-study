# target-product-restful-api-case-study
Target myRetail RESTful service Case Study

## **Introduction**
API that aggregates product details from multiple sources (Cassandra Database & External Redsky API). Database updates are enabled on PUT.

##Tech
* Spring Boot(v2.4.5)
* Java1.8
* Maven
* Cassandra
* JUnit5
* RestAssured

####Pre-requisities
* Java1.8
* Cassandra


### Clone Repository
 ```
 git clone https://github.com/EshwarRavindran/target-product-restful-api-case-study.git
 ```
### Product API details

#### GET Product with price details
```
http://localhost:8080/api/1/products/13860428
```
#### Update Price Details in Cassandra
```
{
    "id": "13860428",
    "name": "The Big Lebowski (Blu-ray)",
    "current_price": {
        "value": "243455.33",
        "currency_code": "USD"
    }
}
```
```
http://localhost:8080/api/1/products/13860428
```
