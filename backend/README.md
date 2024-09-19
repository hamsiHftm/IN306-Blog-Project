# IN306 - Verteilte System (Blog Project)

## What does this application do?
This Blog Application is built using Quarkus and provides a simple REST API for managing blog posts. The application allows users to:
- **Retrieve all blog posts**: Users can fetch a list of all blog posts available in the system
- **Retrieve a single blog post**: Users can retrieve a specific blog post by its unique identifier.
- **Add new blog posts**: Users can create and add new blog posts to the system.
- **Manage users**: Users can register, login, and manage their accounts. 
- **Comment on blog posts**: Users can comment on blog posts and view comments on each post. 
- **Like blog posts**: Users can like blog posts to show appreciation or agreement with the content. 
- **Rate blog posts**: Users can rate blog posts based on their quality or relevance.

## Roles and Permissions

1. **Guest (Not Logged In)**:
    - **Access**: Read-only access to view blog posts and comments.
    - **Actions**: Can view blog posts and comments but cannot interact (create, edit, or delete) with the content or use features like rating and liking.

2. **Registered User (Logged In) / Tech.name "user"**:
    - **Access**: Full access to their own content (blogs, comments) and interactive features.
    - **Actions**:
        - Can register, log in, and manage their account.
        - Can create, edit, and delete their own blog posts and comments.
        - Can like, dislike, and rate blog posts.
        - Can delete their account.
    - **Role Assignment**: When a user creates an account, they are automatically assigned the "Registered User" role.

3. **Admin / Tech.name "admin"**:
    - **Access**: Full control over the system, including all users and content.
    - **Actions**:
        - Can manage all users (e.g., delete accounts, assign roles).
        - Can edit or delete any content (posts, comments).
        - Has administrative control to ensure proper maintenance and support.
    - **Role Management**: Other admins can assign the "Admin" role to a registered user. The system always maintains at least one admin account to ensure continued oversight and management.


## Project Structure
The project follows a standard Quarkus structure:

```
└├── java
│   └── ch
│       └── hftm
│           └── blog
│               ├── boundry
│               │   ├── AuthResource.java
│               │   ├── BlogLikeResource.java
│               │   ├── BlogResource.java
│               │   ├── CommentLikeResource.java
│               │   ├── CommentResource.java
│               │   ├── RatingResource.java
│               │   └── UserResource.java
│               ├── dto
│               │   ├── ErrorResponseDTO1.java
│               │   ├── ResponseDTO1.java
│               │   ├── blog
│               │   │   ├── BlogCreateRequestDTO1.java
│               │   │   ├── BlogDetailResponseDTO1.java
│               │   │   ├── BlogListResponseDTO1.java
│               │   │   ├── BlogResponseDTO1.java
│               │   │   ├── BlogResponseDTO2.java
│               │   │   └── BlogUpdateRequestDTO1.java
│               │   ├── comment
│               │   │   ├── CommentCreateRequestDTO1.java
│               │   │   ├── CommentResponseDTO1.java
│               │   │   ├── CommentResponseDTO2.java
│               │   │   └── CommentUpdateRequestDTO1.java
│               │   ├── login
│               │   │   ├── LoginRequestDTO1.java
│               │   │   └── LoginResponseDTO1.java
│               │   ├── rating
│               │   │   └── RatingResponseDTO1.java
│               │   └── user
│               │       ├── UserChangePasswordRequestDTO1.java
│               │       ├── UserCreateRequestDTO1.java
│               │       ├── UserDetailResponseDTO1.java
│               │       └── UserUpdateRequestDTO1.java
│               ├── entity
│               │   ├── Blog.java
│               │   ├── BlogLike.java
│               │   ├── Comment.java
│               │   ├── CommentLike.java
│               │   ├── Rating.java
│               │   └── User.java
│               ├── repository
│               │   ├── BlogLikeRepository.java
│               │   ├── BlogRepository.java
│               │   ├── CommentLikeRepository.java
│               │   ├── CommentRepository.java
│               │   ├── RatingRepository.java
│               │   └── UserRepository.java
│               ├── service
│               │   ├── AuthService.java
│               │   ├── BlogLikeService.java
│               │   ├── BlogService.java
│               │   ├── CommentLikeService.java
│               │   ├── CommentService.java
│               │   ├── RatingService.java
│               │   └── UserService.java
│               └── util
│                   ├── ApplicationLifecycle.java
│                   └── KeyGeneratorHelper.java
└── resources
    ├── META-INF
    └── application.properties

```

- **boundary**: Contains REST resource classes (*Resource.java) that handle HTTP requests related to different components of the blog application (blogs, comments, likes, ratings, users).
- **dto**: Contains DTO (Data Transfer Object) classes (*DTO.java) used for representing data in API requests and responses.
  - Subpackages (blog, comment, rating, user) organize DTOs based on the entity they represent.
- **service**: Contains service classes (*Service.java) that encapsulate business logic for respective entities (blogs, comments, likes, ratings, users).
- **repository**: Contains repository interfaces (*Repository.java) that define methods for interacting with the database for each entity.
- **entity**: Contains entity classes (*.java) that represent data objects stored in the database.
- **application.properties**: Configuration file for application settings, including database connection details.
- **util**: Helper classes for this project


- **resources**: Directory for non-Java resources. 
  - **Blog.drawio.png**: Diagram or image related to blogs (assuming it's a diagram of some sort).
  - **data_query**: Directory containing SQL scripts (*.sql) for database operations related to blogs, comments, likes, ratings, and users.

## Entity classes
![class diagramm](../resources/Blog.drawio.png)
- **User:** Represents a user with basic account details and a profile picture.
- **Blog:** Stores blog posts created by users with titles, content, creation/update timestamps, associated user, comments, likes, and ratings.
- **Comment:** Represents comments on blog posts with content, creation/update timestamps, associated blog, user, and likes.
- **Like:** Tracks likes on blog posts by users.
- **CommentLikes:** Tracks likes on comments by users.
- **Rating:** Stores numeric ratings (e.g., 1-5 stars) given by users to blog posts.

## Response Schema
The application follows a consistent response schema for handling HTTP responses:
- ResponseDTO1: A generic response DTO containing:
  - isSuccess: Boolean indicating the success or failure of the operation. 
  - data: Object containing the response data. In case of success, it contains the corresponding DTO (e.g., BlogResponseDTO, CommentResponseDTO). In case of an error, it contains an instance of ErrorResponseDTO1.

### ErrorResponseDTO1
- ErrorResponseDTO1:
  - errorMsg: String containing an error message describing the reason for the failure.

## How to Start the Project

### Prerequisites
- Ensure you have JDK 11 or later installed.
- Make sure you have Maven installed.
- Ensure Docker is installed on your machine.
- Have MySQL installed or use a MySQL container.

### Steps to Start the Project - DEV mode
#### Blog - Backend Component
1. **Clone the Repository**:
   ```sh
   git clone https://github.com/hamsiHftm/IN306-Blog-Project.git
   cd IN306-Blog-Project
   ```

2. **Configure the Database**:
   - The application.properties file in your project includes all the necessary configurations to set up the database for the Blog Application. Ensure to replace the volume path with your preferred external location to prevent data loss:
   ```sh
   quarkus.datasource.devservices.volumes."{your-volume-path-for-db}"=/var/lib/mysql
   ```
   To prevent data loss during development, it's essential to specify an external volume path (quarkus.datasource.devservices.volumes) in application.properties. This ensures that the MySQL database data is stored outside the Docker container.

  - Configure MySQL in MySQL Workbench:
    ```
        Port: 51995
	    URL: localhost (or the IP address of your MySQL server)
        Usrname: quarkus
	    Password: quarkus
    ```
    
3. **Initialize the Database**:
   Run SQL scripts to set up the database schema and initial data:
   - Use MySQL Workbench or a similar tool to execute the SQL files in resources/data_query/
   - Alternatively, you can create data manually using the /user POST endpoint and other endpoints.
   ```sh
   ./mvnw quarkus:dev
   ```

4. **Run the Application**:
   Use Maven to start the application:
   ```sh
   ./mvnw quarkus:dev
   ```

The application will be available at http://localhost:8080.

## Authenticating API Endpoints Using Swagger UI

To access protected endpoints in the Blog Application, you need to authenticate using a JWT (JSON Web Token). Here’s how you can do it using Swagger UI:

1. **Obtain Login Credentials**:
    - If you have used the SQL scripts from `resources/data_query/` to set up the initial data, you can use the provided credentials for login.
        ```Normal User
      {
        "username": "jan.doe@example.com",
        "password": "password1"
      }
      ```
      
        ```Admin User
      {
        "username": "james.wilson@example.com",
        "password": "password12"
      }
      ```
    - Alternatively, you can create a new user using the `/user` POST endpoint. Ensure you have created a user with a username and password.

2. **Login to Obtain a JWT Token**:
    - In Swagger UI, you can use the `/login` endpoint to authenticate and obtain a JWT token.
    - Locate the **/login** endpoint in the list of endpoints.
    - Click on the **Try it out** button for the `/login` endpoint.
    - Provide your username and password in the request body. Example:
      ```json
      {
        "username": "your-username",
        "password": "your-password"
      }
      ```
    - Click **Execute** to send the request.
    - The response will include a JWT token.

3. **Authorize Using Swagger UI**:
    - After obtaining the JWT token, click the **Authorize** button at the top of the Swagger UI page.
    - In the authorization dialog, paste the JWT token into the **Value** field in the format `Bearer your-token-here`.
    - Click **Authorize** to apply the token to all subsequent requests.

4. **Access Protected Endpoints**:
    - With the token authorized, you can now access protected endpoints that require authentication.
    - Swagger UI will automatically include the JWT token in the authorization header of your requests.


## Change History
1. **Project created**: Initial project setup.
2. **Blog Model**: Added the Blog model class.
3. **DB Connection (repository and service)**: Implemented database connection, repository, and service layers for blog.
4. **Project structure**: Defined the overall project structure.
5. **REST API paths added**: Added REST API endpoints for blog operations.
6. **Entity**: I tried adding a new entity, but it didn't work. I need help fixing this or removing the entity entirely.
7. **Entities**: All entity classes have been added for the project. 
8. **OpenAPI**: An OpenAPI specification has been added to the resources folder, but it is not yet complete. Due to time constraints, I was unable to finish it.
9. **Blog HTTP requests**: Implemented PUT, POST, GET, and DELETE routes for the Blog entity.
10. **Error-Response**: Added thorough error handling in the way responses are managed.
11. **Validation**: Validation added for entities.
12. **User Repository**: Necessary User Repository added.
13. **CreateBlogRequestDTO Schema:** Schema for creating blog request added
14. **Schema improved**: Schema for get all blogs added
15. **application.properties**: Property file updated for DB data
16. **User HTTP requests**: Http Route fully implemented for user model with schema
17. **Blog HTTP requests**: Http Route fully implemented for blog model with schema
18. **Comment HTTP requests**: Http Route fully implemented for comment model with schema
19. **BlogLike HTTP requests**: Http Route fully implemented for blogLike model with schema
20. **CommentLike HTTP requests**: Http Route fully implemented for commentLike model with schema
21. **Rating HTTP requests**: Http Route fully implemented for rating model with schema
22. **KeyCloak Integration**: Keycloak integration is not working. I am still having issues creating roles. I don’t understand the attributes and descriptions with placeholders. Additionally, the Keycloak Admin UI is not displaying in the Dev UI.
23. **Blog HTTP requests with user**: The HTTP requests for blogs with user roles are not working as expected. They return authentication errors. I need to define the roles and permissions properly.
24. **Authentication concept removed**: For flutter project
25. **JWT Token**: Jwt Token added and implemented authentication logic for blog
26. **Authentication logic added for User Resource**: Authentication logic added for user
27. **Quarkus Test added**: Automation test added for both Auth and Blog
28. **Authenticatio Logic added**
29. **Continuous Testing implemented**