# IN306 - Verteilte System (Blog Project)

## What does this application do?
This Blog Application is built using Quarkus and provides a simple REST API for managing blog posts. The application allows users to:
- **Retrieve all blog posts**: Users can fetch a list of all blog posts available in the system
- **Retrieve a single blog post**: Users can retrieve a specific blog post by its unique identifier.
- **Add new blog posts**: Users can create and add new blog posts to the system.
- **Manage users**: Users can register, login, and manage their accounts. 
- **Comment on blog posts**: Users can comment on blog posts and view comments on each post. 
- **Like blog posts**: Users can like blog posts to show appreciation or agreement with the content. 
- **Reply to comments**: Users can reply to comments on blog posts, facilitating discussions and interactions. 
- **Rate blog posts**: Users can rate blog posts based on their quality or relevance.

## Project Structure
The project follows a standard Quarkus structure:

```
├── java
│   └── ch
│       └── hftm
│           └── blog
│               ├── boundry
│               │   └── BlogResource.java
│               ├── entity
│               │   └── Blog.java
│               ├── repository
│               │   └── BlogRepository.java
│               └── service
│                   └── BlogService.java
└── resources
    └── application.properties

```
- **boundary**: Contains REST resource classes responsible for handling HTTP requests.
- **service**: Service class containing business logic for blogs operations.
- **repository**: Repository class for database operations.
- **entity**: Entity class representing various components of this application.
- **application.properties**: Configuration file for database and other settings.

## Entity classes
**Blog**: Represents a blog post in the application. Each blog post contains attributes such as title, content, author, and publication date.

**User**: Represents a user of the blog application. Users have attributes such as username, email, and password, and can perform actions like creating blog posts, commenting, and interacting with other users.

**Comment**: Represents a comment on a blog post. Each comment includes attributes such as the content of the comment, the user who posted it, and the timestamp. Comments allow users to engage in discussions and provide feedback on blog posts.

**Like**: Represents a like on a blog post. It indicates that a user has expressed appreciation or agreement with the content of the post.

**Reply**: Represents a reply to a comment on a blog post. Replies facilitate discussions and interactions among users, allowing them to respond to specific comments.

**Rating**: Represents a rating given to a blog post by a user. Ratings allow users to express their opinion on the quality or relevance of the content, helping other users to discover valuable posts.

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


3. **Run the Application**:
   Use Maven to start the application:
   ```sh
   ./mvnw quarkus:dev
   ```

   The application will be available at `http://localhost:8080`.


## Change History
1. **Project created**: Initial project setup.
2. **Blog Model**: Added the Blog model class.
3. **DB Connection (repository and service)**: Implemented database connection, repository, and service layers for blog.
4. **Project structure**: Defined the overall project structure.
5. **REST API paths added**: Added REST API endpoints for blog operations.

This README provides a comprehensive overview of the blog application, including its functionality, project structure, additional classes, ideas for future enhancements, and instructions for setting up and running the application.