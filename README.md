# price-service

**Prerequisites**:

Git, Gradle, Java 17 SDK, any web browser.


**ARCHITECTURE**

Implementing hexagonal / ports and adapters arch style for the microservice application.

More info on the logic behind implementation [here](https://netflixtechblog.com/ready-for-changes-with-hexagonal-architecture-b315ec967749) and [here](https://www.baeldung.com/hexagonal-architecture-ddd-spring)


**DATABASE**

We're using h2, cached in-memory with the excercise provided data loading automatically on startup.

Table is mapped to the *price* entity under model.


**SWAGGER**

By default, Swagger is located on port 8080/swagger-ui/index.html
Available endpoints are a simple healthcheck for the application and the requested price consulting endpoint.


**RUNNING THE APPLICATION**

Application is built with Gradle boot-run command. 

Running price-service will startup Swagger which is the visual entrypoint.

Application can be locally accessed through

http://localhost:8080/swagger-ui/index.html#/
