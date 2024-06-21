package ch.hftm.blog.boundry;

import ch.hftm.blog.dto.CreateBlogRequestDTO;
import ch.hftm.blog.entity.Blog;
import ch.hftm.blog.service.BlogService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import java.util.List;

@Path("blogs")
public class BlogResource {

    @Inject
    BlogService blogService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBlogs(@QueryParam("searchTitle") String searchTitle,
                                @QueryParam("limit") @DefaultValue("10") int limit,
                                @QueryParam("offset") @DefaultValue("0") int offset) {
        try {
            List<Blog> blogs = blogService.getAllBlogs(searchTitle, limit, offset);
            return Response.ok(blogs).build();
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity("Error retrieving blogs: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBlogById(@PathParam("id") Long id) {
        try {
            Blog blog = blogService.getBlogById(id, true);
            if (blog == null) {
                return Response.status(Status.NOT_FOUND).entity("Blog not found").build();
            }
            return Response.ok(blog).build();
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity("Error retrieving blog: " + e.getMessage()).build();
        }
    }

    @GET
    @Path("favourites/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFavoriteBlogsByUserId(@PathParam("userId") Long userId,
                                               @QueryParam("searchTitle") String searchTitle,
                                               @QueryParam("limit") @DefaultValue("10") int limit,
                                               @QueryParam("offset") @DefaultValue("0") int offset) {
        try {
            List<Blog> blogs = blogService.getFavoriteBlogsByUserId(userId, searchTitle, limit, offset);
            return Response.ok(blogs).build();
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity("Error retrieving favourite blogs: " + e.getMessage()).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response addBlog(@Valid CreateBlogRequestDTO blogDTO) {
        try {
            var blog = blogDTO.toBlog();
            Blog createdBlog = blogService.addBlog(blog);
            return Response.status(Status.CREATED).entity(createdBlog).build();
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity("Error adding blog: " + e.getMessage()).build();
        }
    }

    @PATCH
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBlog(@PathParam("id") Long id, @Valid Blog blog) {
        try {
            Blog updatedBlog = blogService.updateBlog(id, blog);
            if (updatedBlog == null) {
                return Response.status(Status.NOT_FOUND).entity("Blog not found").build();
            }
            return Response.ok(updatedBlog).build();
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity("Error updating blog: " + e.getMessage()).build();
        }
    }

    @DELETE
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteBlog(@PathParam("id") Long id) {
        try {
            blogService.deleteBlog(id);
            return Response.noContent().build();
        } catch (Exception e) {
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity("Error deleting blog: " + e.getMessage()).build();
        }
    }
}
