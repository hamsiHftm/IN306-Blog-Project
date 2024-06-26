package ch.hftm.blog.boundry;

import ch.hftm.blog.dto.ErrorResponseDTO;
import ch.hftm.blog.dto.ResponseDTO;
import ch.hftm.blog.entity.Blog;
import ch.hftm.blog.entity.BlogLike;
import ch.hftm.blog.entity.User;
import ch.hftm.blog.service.BlogLikeService;
import ch.hftm.blog.service.BlogService;
import ch.hftm.blog.service.UserService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;

@Path("blog/like")
public class BlogLikeResource {
    @Inject
    BlogLikeService blogLikeService;

    @Inject
    BlogService blogService;

    @Inject
    UserService userService;

    @POST
    @Path("/{userId}/{blogId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    @Operation(summary = "Add like to blog by a user")
    public Response addLikeToBlog(@PathParam("userId") long userId,
                                  @PathParam("blogId") long blogId) {
        Response.Status status = Response.Status.OK;
        Object dto = null;
        boolean isSuccess = true;
        try {
            User user = userService.getUserById(userId);
            Blog blog = blogService.findBlogById(blogId);
            if (user == null) {
                status = Response.Status.NOT_FOUND;
                dto = new ErrorResponseDTO("User not found");
                isSuccess = false;
            } else if (blog == null){
                status = Response.Status.NOT_FOUND;
                dto = new ErrorResponseDTO("Blog not found");
                isSuccess = false;
            } else {
                blogLikeService.addLikeToBlog(blog, user);
            }
        } catch (Exception e) {
            status = Response.Status.INTERNAL_SERVER_ERROR;
            dto = new ErrorResponseDTO(e.getMessage());
            isSuccess = false;
        }
        return Response.status(status).entity(new ResponseDTO(isSuccess, dto)).build();
    }

    @DELETE
    @Path("/{userId}/{blogId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    @Operation(summary = "Remove like from blog by a user")
    public Response removeLikeToBlog(@PathParam("userId") Long userId,
                                     @PathParam("blogId") Long blogId) {
        Response.Status status = Response.Status.OK;
        Object dto = null;
        boolean isSuccess = true;
        try {
            User user = userService.getUserById(userId);
            Blog blog = blogService.findBlogById(blogId);
            if (user == null) {
                status = Response.Status.NOT_FOUND;
                dto = new ErrorResponseDTO("User not found");
                isSuccess = false;
            } else if (blog == null){
                status = Response.Status.NOT_FOUND;
                dto = new ErrorResponseDTO("Blog not found");
                isSuccess = false;
            } else {
                BlogLike blogLike = blogLikeService.getBlogLikeByBlogAndUserID(blogId, userId);
                blogLikeService.removeLikeToBlog(blogLike);
            }
        } catch (Exception e) {
            status = Response.Status.INTERNAL_SERVER_ERROR;
            dto = new ErrorResponseDTO(e.getMessage());
            isSuccess = false;
        }
        return Response.status(status).entity(new ResponseDTO(isSuccess, dto)).build();
    }
}
