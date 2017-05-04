[![Build Status](https://travis-ci.org/bollerdominik/SpringAssignment.svg?branch=master)](https://travis-ci.org/bollerdominik/SpringAssignment)
# SpringAssignment

The SpringAssignment is web application providing a REST API and frontend to allow CRUD operations on employees entities. The employee has a many to many relation to its shift.

#### Technologies used: Spring Framework, Hibernate, OAuth2 (Keycloak), PostgreSQL and AngularJS 2

### Installation on Linux:

- Import the Realm App-Realm in to Keyclaok `bin/standalone.sh -Dkeycloak.migration.action=import -Dkeycloak.migration.provider=singleFile -Dkeycloak.migration.file=app-realm.json -Dkeycloak.migration.strategy=OVERWRITE_EXISTING`
- Start Keycloak on port 8080
- Create a PostreSQL database testx (specify login in application.properties)
- Go to the application directory and start it using `sudo mvn install` `sudo mvn spring-boot:run`
- Access the application at http://localhost:8090/
- Create a new user or login via Facebook social login

A live version of the application can be found at http://boller.site:8080/SpringApp (Login with user "q" password "q")

#### Resources:

https://github.com/keycloak/keycloak/tree/master/examples/demo-template/angular2-product-app/src/

https://spring.io/guides/tutorials/react-and-spring-data-rest/
