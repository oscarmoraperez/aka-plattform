# Payment-Service

SpringBoot based Application to manage the payments in the AKA: Activities for Kids and Adults.

It provides a REST endpoint to create payments and save them in a postgresql DB.

### Requirements:
- Java 22
- RancherDesktop 1.3.1
- Maven 3.2.5

### Run the application:

Start the application run:

```
$ mvn spring-boot:run
```

After that, browse the following URL to access to [Swagger](http://localhost:8080/payment-service/swagger-ui/index.html) 

Notice that the PostgreSQL instance is automatically launched by the spring-boot-docker-compose

### Build and deploy

To build and install the application:

```
$ mvn clean install
```