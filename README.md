# SpringAssignment

The SpringAssignment is web application that allows CRUD operations on employees entities. The employee has a many to many relation to its shift.

#### Technologies used: Spring Framework, Hibernate, OAuth2 (Keycloak), PostgreSQL and AngularJS 2

### Installation (only tested on Linux):

- Start Keycloak on port 8080
- Import the Realm App-Realm in to Keyclaok
- Create a PostreSQL database testx (specify login in application.properties)
- Go to the application directory and start it using `sudo mvn install` `sudo mvn spring-boot:run`
- Access the application at http://localhost:8090/
- Create a new user or login via Facebook social login
- A live version of the application can be found at boller.site/SpringAssignment

#### ToDo
- Automated tests of the business logic
- Upload live version to boller.site
- Export and add Keycloak Realm
