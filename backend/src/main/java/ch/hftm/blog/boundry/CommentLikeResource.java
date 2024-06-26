package ch.hftm.blog.boundry;

import ch.hftm.blog.dto.ErrorResponseDTO1;
import ch.hftm.blog.dto.ResponseDTO1;
import ch.hftm.blog.entity.Comment;
import ch.hftm.blog.entity.CommentLike;
import ch.hftm.blog.entity.User;
import ch.hftm.blog.service.CommentLikeService;
import ch.hftm.blog.service.CommentService;
import ch.hftm.blog.service.UserService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
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
    @Path("/{userId}/{commentId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    @Operation(summary = "Add like to comment by a user")
    public Response addLikeToComment(@PathParam("userId") long userId,
                                  @PathParam("commentId") long commentId) {
        Response.Status status = Response.Status.OK;
        Object dto = null;
        boolean isSuccess = true;
        try {
            User user = userService.getUserById(userId);
            Comment comment = commentService.getCommentById(commentId);
            if (user == null) {
                status = Response.Status.NOT_FOUND;
                dto = new ErrorResponseDTO1("User not found");
                isSuccess = false;
            } else if (comment == null){
                status = Response.Status.NOT_FOUND;
                dto = new ErrorResponseDTO1("Comment not found");
                isSuccess = false;
            } else {
                commentLikeService.addLikeToComment(comment, user);
            }
        } catch (Exception e) {
            status = Response.Status.INTERNAL_SERVER_ERROR;
            dto = new ErrorResponseDTO1(e.getMessage());
            isSuccess = false;
        }
        return Response.status(status).entity(new ResponseDTO1(isSuccess, dto)).build();
    }

    @DELETE
    @Path("/{userId}/{commentId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    @Operation(summary = "Remove like from comment by a user")
    public Response removeLikeToComment(@PathParam("userId") long userId,
                                     @PathParam("commentId") long commentId) {
        Response.Status status = Response.Status.OK;
        Object dto = null;
        boolean isSuccess = true;
        try {
            User user = userService.getUserById(userId);
            Comment comment = commentService.getCommentById(commentId);
            if (user == null) {
                status = Response.Status.NOT_FOUND;
                dto = new ErrorResponseDTO1("User not found");
                isSuccess = false;
            } else if (comment == null){
                status = Response.Status.NOT_FOUND;
                dto = new ErrorResponseDTO1("Comment not found");
                isSuccess = false;
            } else {
                CommentLike commentLike = commentLikeService.getCommentLikeByCommentAndUserID(commentId, userId);
                commentLikeService.removeLikeToComment(commentLike);
            }
        } catch (Exception e) {
            status = Response.Status.INTERNAL_SERVER_ERROR;
            dto = new ErrorResponseDTO1(e.getMessage());
            isSuccess = false;
        }
        return Response.status(status).entity(new ResponseDTO1(isSuccess, dto)).build();
    }
}
