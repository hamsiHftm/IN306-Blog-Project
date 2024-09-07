# IN306 - Verteilte System (Blog Project)

## What does this application do?
This Blog Application is built using Quarkus and provides a simple REST API for managing blog posts. The application allows users to:
- **Retrieve all blog posts**: Users can fetch a list of all blog posts available in the system
- **Retrieve a single blog post**: Users can retrieve a specific blog post by its unique identifier.
- **Add new blog posts**: Users can create and add new blog posts to the system.
- **Manage users**: Users can register, login. 


## Project Structure
The project follows a standard Quarkus structure:

```
└── java
    └── ch
        └── hftm
            └── blog
                ├── boundary
                │   ├── BlogLikeResource.java            // REST resource for handling blog likes
                │   ├── BlogResource.java                // REST resource for handling blogs
                │   ├── CommentLikeResource.java         // REST resource for handling comment likes
                │   ├── CommentResource.java             // REST resource for handling comments
                │   ├── RatingResource.java              // REST resource for handling ratings
                │   └── UserResource.java                // REST resource for handling users
                ├── dto
                │   ├── ErrorResponseDTO1.java            // DTO for error responses
                │   ├── ResponseDTO1.java                 // Generic DTO for response handling
                │   ├── blog
                │   │   ├── BlogCreateRequestDTO1.java     // Request DTO for creating a blog
                │   │   ├── BlogDetailResponseDTO1.java    // Response DTO for detailed blog response, which includes comments
                │   │   ├── BlogListResponseDTO1.java      // Response DTO for listing blogs
                │   │   ├── BlogResponseDTO1.java          // Base DTO for blog responses, which includes numberOfLikes and user info
                │   │   ├── BlogResponseDTO2.java          // DTO for blog responses, which has only title and id for simple lookup
                │   │   └── BlogUpdateRequestDTO1.java     // Request DTO for updating a blog
                │   ├── comment
                │   │   ├── CommentCreateRequestDTO1.java  // Request DTO for creating a comment
                │   │   ├── CommentResponseDTO1.java       // Base DTO for comment responses, with blog info
                │   │   ├── CommentResponseDTO2.java       // Additional DTO for comment responses, without blog info
                │   │   └── CommentUpdateRequestDTO1.java  // Request DTO for updating a comment
                │   ├── rating
                │   │   └── RatingResponseDTO1.java         // DTO for rating responses
                │   └── user
                │       ├── UserChangePasswordRequestDTO1.java  // Request DTO for changing user password
                │       ├── UserCreateRequestDTO1.java          // Request DTO for creating a user
                │       ├── UserDetailResponseDTO1.java         // Response DTO for detailed user response
                │       ├── UserLoginDTO1.java                  // Request DTO for user login
                │       └── UserUpdateRequestDTO1.java          // Request DTO for updating a user
                ├── entity
                │   ├── Blog.java                        // Entity class for blogs
                │   ├── BlogLike.java                    // Entity class for blog likes
                │   ├── Comment.java                     // Entity class for comments
                │   ├── CommentLike.java                 // Entity class for comment likes
                │   ├── Rating.java                      // Entity class for ratings
                │   └── User.java                        // Entity class for users
                ├── repository
                │   ├── BlogLikeRepository.java          // Repository interface for blog likes
                │   ├── BlogRepository.java              // Repository interface for blogs
                │   ├── CommentLikeRepository.java       // Repository interface for comment likes
                │   ├── CommentRepository.java           // Repository interface for comments
                │   ├── RatingRepository.java            // Repository interface for ratings
                │   └── UserRepository.java              // Repository interface for users
                └── service
                    ├── BlogLikeService.java             // Service class for blog like operations
                    ├── BlogService.java                 // Service class for blog operations
                    ├── CommentLikeService.java          // Service class for comment like operations
                    ├── CommentService.java              // Service class for comment operations
                    ├── RatingService.java               // Service class for rating operations
                    └── UserService.java                 // Service class for user operations
                    
└── resources
    ├── Blog.drawio.png        // Diagram or image related to blogs (assuming it's a diagram of some sort)
    └── data_query
        ├── Blog.sql           // SQL script for blog-related data
        ├── BlogLike.sql       // SQL script for blog like-related data
        ├── Comment.sql        // SQL script for comment-related data
        ├── CommentLike.sql    // SQL script for comment like-related data
        ├── Rating.sql         // SQL script for rating-related data
        └── User.sql           // SQL script for user-related data
```
- **boundary**: Contains REST resource classes (*Resource.java) that handle HTTP requests related to different components of the blog application (blogs, comments, likes, ratings, users).
- **dto**: Contains DTO (Data Transfer Object) classes (*DTO.java) used for representing data in API requests and responses.
  - Subpackages (blog, comment, rating, user) organize DTOs based on the entity they represent.
- **service**: Contains service classes (*Service.java) that encapsulate business logic for respective entities (blogs, comments, likes, ratings, users).
- **repository**: Contains repository interfaces (*Repository.java) that define methods for interacting with the database for each entity.
- **entity**: Contains entity classes (*.java) that represent data objects stored in the database.
- **application.properties**: Configuration file for application settings, including database connection details.


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

### Steps to Start the Project
#### Blog - Backend Component
1. **Clone the Repository**:
   ```sh
   git clone https://github.com/hamsiHftm/IN306-Blog-Project.git
   cd IN306-Blog-Project
   ```

2. **Configure the Database**:
   The application.properties file in your project includes all the necessary configurations to set up the database for the Blog Application. Ensure to replace the volume path with your preferred external location to prevent data loss:
   ```sh
   quarkus.datasource.devservices.volumes."{your-volume-path-for-db}"=/var/lib/mysql
   ```
   To prevent data loss during development, it's essential to specify an external volume path (quarkus.datasource.devservices.volumes) in application.properties. This ensures that the MySQL database data is stored outside the Docker container.

Example SQL Queries: To initialize the database with sample data, you can use the provided SQL scripts located in the resources/data_query directory. For example, you can execute Blog.sql to insert sample blog data.

3. **Run the Application**:
   Use Maven to start the application:
   ```sh
   ./mvnw quarkus:dev
   ```

   The application will be available at `http://localhost:8080`.

