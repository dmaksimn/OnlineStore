# Online Store Application

#### A full-stack Online Shop web application using Spring Boot 2 and Angular 7. 
This is a Single Page Application with client-side rendering. It includes [server part](C:\Users\Maksim\Downloads\FPOnlineStore\server) and [client interface](C:\Users\Maksim\Downloads\FPOnlineStore\ui) two separate projects on different branches.
The frontend client makes API calls to the backend server when it is running.

#### Live Demo: []()

> This project is my final project cours JAVA ENTERPRISE
## Screenshot
![]()

## Features
- REST API
- Docker
- Docker Compose
- JWT authentication
- Cookie based visitors' shopping cart
- Persistent customers' shopping cart
- Cart & order management
- Checkout
- Catalogue
- Order management
- Pagination
## Technology Stacks
**Backend**
  - Java 11
  - Spring Boot 2.2
  - Spring Security
  - JWT Authentication
  - Spring Data JPA
  - Hibernate
  - PostgreSQL
  - Maven

**Frontend**
  - Angular 7
  - Angular CLI
  - Bootstrap

## Database Schema

![]()

## How to  Run

Start the backend server before the frontend client.  

**Backend**

  1. Install [PostgreSQL](https://www.postgresql.org/download/) 
  2. Configure datasource in `application.yml`.
  3. `cd server`.
  4. Run `mvn install`.
  5. Run `mvn spring-boot:run`.
  6. Spring Boot will import mock data into database by executing `import.sql` automatically.
  7. The backend server is running on [localhost:8080]().

**Frontend**
  1. Install [Node.js and npm](https://www.npmjs.com/get-npm)
  2. `cd ui`.
  3. Run `npm install`.
  4. Run `ng serve`
  5. The frontend client is running on [localhost:4200]().
  
Note: The backend API url is configured in `src/environments/environment.ts` of the ui project. It is `localhost:8080/api` by default.
  
#### Run in Docker
You can build the image and run the container with Docker. 
1. Build server project
```bash
cd server
mvn package
```
2. Build ui project
```bash
cd ui
npm install
ng build --prod
```
3. Build images and run containers
```bash
docker-compose up --build
```

