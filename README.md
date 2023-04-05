# Wishlist-Support Microservice API
This microservice is responsible for maintaining the necessary information for the functioning of the [Wishlist API microservice](https://github.com/raytotti/wishlist). It is built using Java 17, Spring Boot, Gradle and MongoDB, and is designed to be deployed using Kubernetes.

## Requirements

To run this microservice, you will need the following:

* [Docker: Required for deploy tho project](https://www.docker.com/)
* [Kubernetes: Required for deploy tho project](https://kubernetes.io/)

## Getting Started
To deploy and run the microservice in your Kubernetes, you will need to following these steps:

1. Apply the ConfigMap Mongo located at `./deploy/kubernetes`
    ```shell
     kubectl apply -f ./deploy/kubernetes/mongo-wl-sup-config.yml
   ```
2. Apply the Secret Mongo located at `./deploy/kubernetes`
    ```shell
     kubectl apply -f ./deploy/kubernetes/mongo-wl-sup-secret.yml
   ```
3. Apply the Deployment Mongo located at `./deploy/kubernetes`
    ```shell
   kubectl apply -f ./deploy/kubernetes/mongo-wl-sup-deployment.yml
   ```
4. Apply the Deployment Api located at `./deploy/kubernetes`
   ```shell
   kubectl apply -f ./deploy/kubernetes/deployment.yml
   ```
   
## Endpoints
The following endpoints are available in the microservice:

###
#### Client
* **POST /api/v1/clients** - Add a new client.
```shell
curl --location --request POST 'http://<applicationUrl>:<applicationPort>/api/v1/clients' \
--header 'Content-Type: application/json' \
--data-raw '{
    "cpf": "164.565.990-91",
    "name": "Jonh"
}'
```
* **DELETE /api/v1/clients/{clientId}** - Remove an informed clientId.
```shell
curl --location --request DELETE 'http://<applicationUrl>:<applicationPort>/api/v1/clients/<clientId>'
```
* **GET /api/v1/clients/{clientId}** - Retrieve an informed clientId.
```shell
curl --location --request GET 'http://<applicationUrl>:<applicationPort>/api/v1/clients/<clientId>'
```
* **GET /api/v1/clients/{clientId}/exists** - Checks if the informed client exists.
```shell
curl --location --request GET 'http://<applicationUrl>:<applicationPort>/api/v1/clients/<clientId>/exists'
```
* **GET /api/v1/clients** - Retrieve all clients.
```shell
curl --location --request GET 'http://<applicationUrl>:<applicationPort>/api/v1/clients'
```

###
#### Product
* **POST /api/v1/clients** - Add a new product.
```shell
curl --location --request POST 'http://<applicationUrl>:<applicationPort>/api/v1/products' \
--header 'Content-Type: application/json' \
--data-raw '{
    "code": "1",
    "description": "Description product one",
    "thumbnail": "url image",
    "price": 20,
    "category": "category product one",
    "brand": "brand product one",
    "additionalInfo": "more info about product one"
}'
```
* **DELETE /api/v1/products/{productId}** - Remove an informed product.
```shell
curl --location --request DELETE 'http://<applicationUrl>:<applicationPort>/api/v1/products/<productId>'
```
* **GET /api/v1/products/{productId}** - Retrieve an informed productId.
```shell
curl --location --request GET 'http://<applicationUrl>:<applicationPort>/api/v1/products/<productId>'
```
* **GET /api/v1/products** - Retrieve all products.
```shell
curl --location --request GET 'http://<applicationUrl>:<applicationPort>/api/v1/products'
```

## TODO
* As a future implementation, we are going to implement the communication of this microservice with a messaging structure. with the intention of ensuring the integrity of product and customer data.
