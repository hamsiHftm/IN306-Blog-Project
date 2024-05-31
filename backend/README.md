Your README is well-structured and covers the essential information for your blog application project. Here’s a slightly polished version with a few minor adjustments for clarity and completeness:

# IN306 - Verteilte System (Blog Project)

## What does this application do?
This Blog Application is built using Quarkus and provides a simple REST API for managing blog posts. The application allows users to:
- Retrieve a list of all blog posts.
- Retrieve a single blog post by its ID.
- Add new blog posts.

## Project Structure
The project follows a standard Quarkus structure:

```
TODO tree structure
```

- **boundary**: REST resource class handling HTTP requests for this application.
- **service**: Service class containing business logic for operations.
- **repository**: Repository class for database operations.
- **entity**: Entity class representing this application.
- **application.properties**: Configuration file for database and other settings.

## How to Start the Project

### Prerequisites
- Ensure you have JDK 11 or later installed.
- Make sure you have Maven installed.

### Steps to Start the Project
1. **Clone the Repository**:
   ```sh
   git clone https://github.com/hamsiHftm/IN306-Blog-Project.git
   cd IN306-Blog-Project
   ```

2. **Configure the Database**:
TODO
   Update `src/main/resources/application.properties` with your database configuration:
   ```properties
   quarkus.datasource.db-kind=postgresql
   quarkus.datasource.username=<your_db_username>
   quarkus.datasource.password=<your_db_password>
   quarkus.datasource.jdbc.url=jdbc:postgresql://localhost:5432/<your_db_name>
   ```

3. **Run the Application**:
   Use Maven to start the application:
   ```sh
   ./mvnw quarkus:dev
   ```

   The application will be available at `http://localhost:8080`.

## Where are the Logs Saved in Quarkus and How to See Them

TODO

In Quarkus, logs are typically written to the console by default. When running the application in development mode (using `quarkus:dev`), you will see the logs directly in the terminal.

To configure and view logs:

1. **Configure Logging**:
   You can customize the logging configuration in `application.properties`:
   ```properties
   quarkus.log.category."com.example".level=INFO
   quarkus.log.console.enable=true
   quarkus.log.file.enable=true
   quarkus.log.file.path=logs/quarkus.log
   quarkus.log.file.level=INFO
   ```

2. **View Logs**:
    - **Console Logs**: Visible in the terminal where you run the application.
    - **File Logs**: If configured, logs will be saved to `logs/quarkus.log`.

## Change History
1. **Project created**: Initial project setup.
2. **Blog Model**: Added the Blog model class.
3. **DB Connection (repository and service)**: Implemented database connection, repository, and service layers for blog.
4. **Project structure**: Defined the overall project structure.
5. **REST API paths added**: Added REST API endpoints for blog operations.

This README provides clear instructions and an overview of your project, making it easy for others to understand and set up the application.