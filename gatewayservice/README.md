# Gateway-Service

SpringBoot based Application (Spring Cloud Gateway) to manage the access to the AKA platform and the microservices ecosystem. 

It provides a prexisting route to the Catalog service.  

### Requirements:
- Java 22
- RancherDesktop 1.3.1
- Maven 3.2.5

### Run the application:

Start the application run:

```
$ mvn spring-boot:run
```

After that, browse the following URL you can access the catalog service via: GET to http://localhost/catalogservice/activity 

Notice it also provides the actuator enpoint.

### Build and deploy

To build and install the application:

```
$ mvn clean install
```