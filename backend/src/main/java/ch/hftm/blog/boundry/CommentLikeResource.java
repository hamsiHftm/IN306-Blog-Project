package ch.hftm.blog.boundry;

import ch.hftm.blog.dto.ErrorResponseDTO1;
import ch.hftm.blog.dto.ResponseDTO1;
import ch.hftm.blog.entity.Comment;
import ch.hftm.blog.entity.CommentLike;
import ch.hftm.blog.entity.User;
import ch.hftm.blog.service.CommentLikeService;
import ch.hftm.blog.service.CommentService;
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

@Path("comment/like")
public class CommentLikeResource {

    @Inject
    CommentLikeService commentLikeService;

    @Inject
    CommentService commentService;

    @Inject
    UserService userService;

    @POST
    @RolesAllowed({"admin", "user"})
    @Path("/{userId}/{commentId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    @Operation(summary = "Add like to comment by a user")
    public Response addLikeToComment(@Context SecurityContext securityContext,
                                     @PathParam("userId") long userId,
                                     @PathParam("commentId") long commentId) {
        Response.Status status = Response.Status.OK;
        Object dto = null;
        boolean isSuccess = true;

        try {
            // Get logged-in user ID from SecurityContext
            String loggedInUser = securityContext.getUserPrincipal().getName();
            User currentUser = userService.getUserById(Long.valueOf(loggedInUser));

            // Check if logged-in user is the same as the path userId or has admin rights
            if (currentUser == null) {
                status = Response.Status.UNAUTHORIZED;
                dto = new ErrorResponseDTO1("User is not authenticated");
                isSuccess = false;
            } else if (!currentUser.getId().equals(userId) && !currentUser.getRole().equals("admin")) {
                status = Response.Status.FORBIDDEN;
                dto = new ErrorResponseDTO1("Not authorized to like comments for this user");
                isSuccess = false;
            } else {
                // Proceed with like addition if authentication is valid
                User user = userService.getUserById(userId);
                Comment comment = commentService.getCommentById(commentId);

                if (user == null) {
                    status = Response.Status.NOT_FOUND;
                    dto = new ErrorResponseDTO1("User not found");
                    isSuccess = false;
                } else if (comment == null) {
                    status = Response.Status.NOT_FOUND;
                    dto = new ErrorResponseDTO1("Comment not found");
                    isSuccess = false;
                } else {
                    commentLikeService.addLikeToComment(comment, user);
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
    @RolesAllowed({"admin", "user"})
    @Path("/{userId}/{commentId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    @Operation(summary = "Remove like from comment by a user")
    public Response removeLikeToComment(@Context SecurityContext securityContext,
                                        @PathParam("userId") long userId,
                                        @PathParam("commentId") long commentId) {
        Response.Status status = Response.Status.OK;
        Object dto = null;
        boolean isSuccess = true;

        try {
            // Get logged-in user ID from SecurityContext
            String loggedInUser = securityContext.getUserPrincipal().getName();
            User currentUser = userService.getUserById(Long.valueOf(loggedInUser));

            // Check if logged-in user is the same as the path userId or has admin rights
            if (currentUser == null) {
                status = Response.Status.UNAUTHORIZED;
                dto = new ErrorResponseDTO1("User is not authenticated");
                isSuccess = false;
            } else if (!currentUser.getId().equals(userId) && !currentUser.getRole().equals("admin")) {
                status = Response.Status.FORBIDDEN;
                dto = new ErrorResponseDTO1("Not authorized to remove likes for this user");
                isSuccess = false;
            } else {
                User user = userService.getUserById(userId);
                Comment comment = commentService.getCommentById(commentId);

                if (user == null) {
                    status = Response.Status.NOT_FOUND;
                    dto = new ErrorResponseDTO1("User not found");
                    isSuccess = false;
                } else if (comment == null) {
                    status = Response.Status.NOT_FOUND;
                    dto = new ErrorResponseDTO1("Comment not found");
                    isSuccess = false;
                } else {
                    CommentLike commentLike = commentLikeService.getCommentLikeByCommentAndUserID(commentId, userId);
                    if (commentLike == null) {
                        status = Response.Status.NOT_FOUND;
                        dto = new ErrorResponseDTO1("Like not found");
                        isSuccess = false;
                    } else {
                        commentLikeService.removeLikeToComment(commentLike);
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