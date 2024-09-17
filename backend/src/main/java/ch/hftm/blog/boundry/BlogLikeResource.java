package ch.hftm.blog.boundry;

import ch.hftm.blog.dto.ErrorResponseDTO1;
import ch.hftm.blog.dto.ResponseDTO1;
import ch.hftm.blog.entity.Blog;
import ch.hftm.blog.entity.BlogLike;
import ch.hftm.blog.entity.User;
import ch.hftm.blog.service.BlogLikeService;
import ch.hftm.blog.service.BlogService;
import ch.hftm.blog.service.UserService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
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
    @RolesAllowed({"admin", "user"})
    @Path("/{userId}/{blogId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    @Operation(summary = "Add like to blog by a user")
    public Response addLikeToBlog(@Context SecurityContext securityContext,
                                  @PathParam("userId") long userId,
                                  @PathParam("blogId") long blogId) {
        Response.Status status = Response.Status.OK;
        Object dto = null;
        boolean isSuccess = true;

        try {
            String loggedInUserId = securityContext.getUserPrincipal().getName();
            long loggedInUserIdLong = Long.parseLong(loggedInUserId);

            if (userId != loggedInUserIdLong) {
                status = Response.Status.FORBIDDEN;
                dto = new ErrorResponseDTO1("User is not authorized to like this blog");
                isSuccess = false;
            } else {
                User user = userService.getUserById(userId);
                Blog blog = blogService.findBlogById(blogId);
                if (user == null) {
                    status = Response.Status.NOT_FOUND;
                    dto = new ErrorResponseDTO1("User not found");
                    isSuccess = false;
                } else if (blog == null) {
                    status = Response.Status.NOT_FOUND;
                    dto = new ErrorResponseDTO1("Blog not found");
                    isSuccess = false;
                } else {
                    blogLikeService.addLikeToBlog(blog, user);
                }
            }
        } catch (Exception e) {
            status = Response.Status.INTERNAL_SERVER_ERROR;
            dto = new ErrorResponseDTO1(e.getMessage());
            isSuccess = false;
        }
        return Response.status(status).entity(new ResponseDTO1(isSuccess, dto)).build();
    }

    @DELETE
    @Path("/{userId}/{blogId}")
    @RolesAllowed({"admin", "user"})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    @Operation(summary = "Remove like from blog by a user")
    public Response removeLikeToBlog(@Context SecurityContext securityContext,
                                     @PathParam("userId") Long userId,
                                     @PathParam("blogId") Long blogId) {
        Response.Status status = Response.Status.OK;
        Object dto = null;
        boolean isSuccess = true;

        try {
            String loggedInUserId = securityContext.getUserPrincipal().getName();;
            long loggedInUserIdLong = Long.parseLong(loggedInUserId);

            if (userId != loggedInUserIdLong) {
                status = Response.Status.FORBIDDEN;
                dto = new ErrorResponseDTO1("User is not authorized to remove like from this blog");
                isSuccess = false;
            } else {
                User user = userService.getUserById(userId);
                Blog blog = blogService.findBlogById(blogId);
                if (user == null) {
                    status = Response.Status.NOT_FOUND;
                    dto = new ErrorResponseDTO1("User not found");
                    isSuccess = false;
                } else if (blog == null) {
                    status = Response.Status.NOT_FOUND;
                    dto = new ErrorResponseDTO1("Blog not found");
                    isSuccess = false;
                } else {
                    BlogLike blogLike = blogLikeService.getBlogLikeByBlogAndUserID(blogId, userId);
                    if (blogLike == null) {
                        status = Response.Status.NOT_FOUND;
                        dto = new ErrorResponseDTO1("Like not found");
                        isSuccess = false;
                    } else {
                        blogLikeService.removeLikeToBlog(blogLike);
                    }
                }
            }
        } catch (Exception e) {
            status = Response.Status.INTERNAL_SERVER_ERROR;
            dto = new ErrorResponseDTO1(e.getMessage());
            isSuccess = false;
        }
        return Response.status(status).entity(new ResponseDTO1(isSuccess, dto)).build();
    }
}