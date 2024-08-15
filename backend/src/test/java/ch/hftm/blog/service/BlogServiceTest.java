package ch.hftm.blog.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import ch.hftm.blog.entity.Blog;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

@QuarkusTest
public class BlogServiceTest {
    @Inject
    BlogService blogService;

//    @Test
//    void listingAndAddingBlogs() {
        // Arrange
//        Blog blog = new Blog("Testing Blog", "This is my testing blog");
//        int blogsBefore;
//        List<Blog> blogs;
//
//        // Act
//        blogsBefore = blogService.getAllBlogs(null, null, 10, 0).size();
//        blogService.addBlog(blog);
//        blogs = blogService.getAllBlogs(null, null, 10, 0);
//
//        // Assert
//        assertEquals(blogsBefore + 1, blogs.size());
//        assertEquals(blog, blogs.get(blogs.size() - 1));
//    }
}