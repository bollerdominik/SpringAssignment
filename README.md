# SpringAssignment

The SpringAssignment is web application that allows CRUD operations on employees entities. The employee has a many to many relation to its shift.

#### Technologies used: Spring Framework, Hibernate, OAuth2 (Keycloak), PostgreSQL and AngularJS 2

### Installation (only tested on Linux):

- Start Keycloak on port 8080
- Import the Realm App-Realm in to Keyclaok
- Go to the application directory and start it using sudo mvn install sudo mvn spring-boot:run
- Access the application at http://localhost:8090/
- Create a new user or login via Facebook social login
- A live version of the application can be found at
