package ch.hftm.blog.boundry;

import ch.hftm.blog.entity.Blog;
import ch.hftm.blog.service.BlogService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("blogs")
public class BlogResource {

    @Inject
    BlogService blogService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Blog> getAllBlogs() {
        return blogService.getAllBlogs();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Blog getBlogById(@PathParam("id") Long id) {
        return blogService.getBlogById(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Blog addBlog(Blog blog) {
        return blogService.addBlog(blog);
    }
}




