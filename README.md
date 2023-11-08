# ENIGMA_RECRUITMENT_TASKS

This Spring Boot project uses two different databases depending on the profile: dev (H2) and prod (PostgreSQL and Redis in Docker containers).

## Running the Application
To run this application, follow these steps:
```
git clone https://github.com/pStrachota/ENIGMA_RECRUITMENT_TASKS.git
```
Navigate to the project directory:
```
cd cloned_repository_directory
```

## Configuring the Profile

You can configure the active profile either in the main `application.properties` file or when running the application in IntelliJ IDEA.

### Using `application.properties`

To set the active profile in the `application.properties` file, open the file and locate the following line:

```
spring.profiles.active= [dev/prod]
```

### Using IntelliJ IDEA
1. Open your project in IntelliJ IDEA.
2. In the top-right corner of the window, you'll find a dropdown menu with a "Run Configuration." Click on it.
3. Select the configuration for running your Spring Boot application.
4. In the "Active Profiles" field, enter the desired profile (dev or prod).
5. Save the configuration.

Now, when you run your application using this configuration in IntelliJ IDEA, it will use the specified profile.

### If you want to run the application in the prod profile, make sure you have Docker installed on your system. Then, execute the following commands:

#### Build the PostgreSQL and Redis Docker container image:

```
docker-compose -f docker-postgres-redis.yml build
```

#### Run the Docker containers:

```
docker-compose -f docker-postgres-redis.yml up -d
```
Wait for a moment for the PostgreSQL and Redis databases to start.

## Accessing Documentation
### You can access the API documentation using Swagger UI:

For the dev profile, open your web browser and go to http://localhost:8080/swagger-ui/index.html.

For the prod profile, TLS is implemented, visit https://localhost:443/swagger-ui/index.html.
