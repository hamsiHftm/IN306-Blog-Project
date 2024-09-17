package ch.hftm.blog.boundry;

import ch.hftm.blog.dto.ErrorResponseDTO1;
import ch.hftm.blog.dto.ResponseDTO1;
import ch.hftm.blog.dto.comment.CommentCreateRequestDTO1;
import ch.hftm.blog.dto.comment.CommentResponseDTO1;
import ch.hftm.blog.dto.comment.CommentUpdateRequestDTO1;
import ch.hftm.blog.entity.Blog;
import ch.hftm.blog.entity.Comment;
import ch.hftm.blog.entity.User;
import ch.hftm.blog.service.BlogService;
import ch.hftm.blog.service.CommentService;
import ch.hftm.blog.service.UserService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.openapi.annotations.Operation;

@Path("comment")
public class CommentResource {

    @Inject
    CommentService commentService;

    @Inject
    UserService userService;

    @Inject
    BlogService blogService;

    @POST
    @RolesAllowed({"admin", "user"})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    @Operation(summary = "Create new comment for blog")
    public Response createComment(@Context SecurityContext securityContext,
                                  @Valid CommentCreateRequestDTO1 requestDTO) {
        Response.Status status = Response.Status.OK;
        Object dto = null;
        boolean isSuccess = true;
        try {
            String loggedInUser = securityContext.getUserPrincipal().getName();
            User currentUser = userService.getUserById(Long.valueOf(loggedInUser));

            // Check if the user is authenticated
            if (currentUser == null) {
                status = Response.Status.UNAUTHORIZED;
                dto = new ErrorResponseDTO1("User is not authenticated");
                isSuccess = false;
            } else if (!currentUser.getId().equals(requestDTO.userId())) {
                status = Response.Status.FORBIDDEN;
                dto = new ErrorResponseDTO1("Not authorized to create a comment for this user");
                isSuccess = false;
            } else {
                // Retrieve blog and user
                User user = userService.getUserById(requestDTO.userId());
                Blog blog = blogService.findBlogById(requestDTO.blogId());

                // Check if user and blog exist
                if (user == null) {
                    status = Response.Status.BAD_REQUEST;
                    dto = new ErrorResponseDTO1("User with id " + requestDTO.userId() + " not found");
                    isSuccess = false;
                } else if (blog == null) {
                    status = Response.Status.BAD_REQUEST;
                    dto = new ErrorResponseDTO1("Blog with id " + requestDTO.blogId() + " not found");
                    isSuccess = false;
                } else {
                    // Create comment
                    Comment comment = requestDTO.toComment(user, blog);
                    Comment createdComment = commentService.createComment(comment);
                    dto = new CommentResponseDTO1(createdComment);
                }
            }
        } catch (Exception e) {
            status = Response.Status.INTERNAL_SERVER_ERROR;
            dto = new ErrorResponseDTO1(e.getMessage());
            isSuccess = false;
        }
        return Response.status(status).entity(new ResponseDTO1(isSuccess, dto)).build();
    }

    @PUT
    @Path("{id}")
    @RolesAllowed({"admin", "user"})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    @Operation(summary = "Update comment content by ID")
    public Response updateComment(@Context SecurityContext securityContext,
                                  @PathParam("id") Long id,
                                  @Valid CommentUpdateRequestDTO1 requestDTO) {
        Response.Status status = Response.Status.OK;
        Object dto = null;
        boolean isSuccess = true;
        try {
            String loggedInUser = securityContext.getUserPrincipal().getName();
            User currentUser = userService.getUserById(Long.valueOf(loggedInUser));

            Comment foundComment = commentService.getCommentById(id);

            if (foundComment == null) {
                status = Response.Status.NOT_FOUND;
                dto = new ErrorResponseDTO1("Comment not found with id");
                isSuccess = false;
            } else if (!"admin".equals(currentUser.getRole()) && !foundComment.getUser().getId().equals(currentUser.getId())) {
                status = Response.Status.FORBIDDEN;
                dto = new ErrorResponseDTO1("Not authorized to update this comment");
                isSuccess = false;
            } else {
                // Update comment
                Comment updatedComment = commentService.updateComment(foundComment, requestDTO.content());
                dto = new CommentResponseDTO1(updatedComment);
            }
        } catch (Exception e) {
            status = Response.Status.INTERNAL_SERVER_ERROR;
            dto = new ErrorResponseDTO1(e.getMessage());
            isSuccess = false;
        }
        return Response.status(status).entity(new ResponseDTO1(isSuccess, dto)).build();
    }

    @DELETE
    @Path("{id}")
    @RolesAllowed({"admin", "user"})
    @Transactional
    @Operation(summary = "Delete comment by ID")
    public Response deleteComment(@Context SecurityContext securityContext,
                                  @PathParam("id") Long id) {
        Response.Status status = Response.Status.OK;
        Object dto = null;
        boolean isSuccess = true;
        try {
            String loggedInUser = securityContext.getUserPrincipal().getName();
            User currentUser = userService.getUserById(Long.valueOf(loggedInUser));

            Comment foundComment = commentService.getCommentById(id);

            if (foundComment == null) {
                status = Response.Status.NOT_FOUND;
                dto = new ErrorResponseDTO1("Comment not found with id");
                isSuccess = false;
            } else if (!"admin".equals(currentUser.getRole()) && !foundComment.getUser().getId().equals(currentUser.getId())) {
                status = Response.Status.FORBIDDEN;
                dto = new ErrorResponseDTO1("Not authorized to delete this comment");
                isSuccess = false;
            } else {
                commentService.deleteComment(foundComment);
            }
        } catch (Exception e) {
            status = Response.Status.INTERNAL_SERVER_ERROR;
            dto = new ErrorResponseDTO1(e.getMessage());
            isSuccess = false;
        }
        return Response.status(status).entity(new ResponseDTO1(isSuccess, dto)).build();
    }
}