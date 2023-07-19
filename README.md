# DISCLAIMER-API

## Endpoints

| Controller             | Method | Endpoint                          |
|------------------------|--------|-----------------------------------|
| disclaimer-controller  | GET    | /api/v1/disclaimers/{disclaimerId} |
|                        | PUT    | /api/v1/disclaimers/{disclaimerId} |
|                        | DELETE | /api/v1/disclaimers/{disclaimerId} |
|                        | GET    | /api/v1/disclaimers?text=something |
|                        | POST   | /api/v1/disclaimers                |
| acceptance-controller  | GET    | /api/v1/acceptances?userId=something |
|                        | POST   | /api/v1/acceptances                |

## Instructions for Docker usage

1. Sign in to your AWS account and navigate to the DynamoDB service.
   1.1. Create a table with the name "disclaimer" and the Partition key "disclaimerId".
   1.2. Create a table with the name "acceptance" and the Partition key "disclaimerId".

2. Navigate to the root of the project.

Option 1:  
3. Generate the JAR file: `mvn clean package`.  
4. Build the Docker image: `docker build -t pablonap/disclaimer:latest .`.  

Option 2:  
3. Download the Docker image from Docker Hub: `docker pull pablonap/disclaimer:latest`.  

4. Create a file named "env" and add the following environment variables:  
AWS_ACCESS_KEY=[your AWS access key]  
AWS_SECRET_KEY=[your AWS secret key]  
AWS_DYNAMODB_SERVICE_ENDPOINT=dynamodb.[your region].amazonaws.com  
AWS_REGION=[your region]  
5. Run the container: 
docker run -d -p 8080:8080 --name disclaimer-api --env-file env pablonap/disclaimer:latest

Now your DISCLAIMER-API should be up and running on port 8080.

## Technologies involved
* Spring boot 3
* Java 17
* AWS DynamoDb
* Docker
* Junit 5
* Mockito
* Swagger (http://localhost:8080/v2/swagger-ui/index.html)

## Captures
[![disclaimer table](https://i.postimg.cc/L5Ww9GZD/Screenshot-from-2023-07-19-15-33-22.png)](https://postimg.cc/LgzNvCDg)
[![acceptance table](https://i.postimg.cc/KcpdD3sT/Screenshot-from-2023-07-19-15-35-01.png)](https://postimg.cc/py8G2rvV)
[![from swagger](https://i.postimg.cc/sxxbR311/Screenshot-from-2023-07-19-15-35-50.png)](https://postimg.cc/fS48cnch)
[![from swagger](https://i.postimg.cc/Qt0vPF8R/Screenshot-from-2023-07-19-15-36-55.png)](https://postimg.cc/340L0JDB)
