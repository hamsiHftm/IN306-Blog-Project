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
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
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
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    @Operation(summary = "Create new comment for blog")
    public Response createComment(@Valid CommentCreateRequestDTO1 requestDTO) {
        Response.Status status = Response.Status.OK;
        Object dto = null;
        boolean isSuccess = true;
        try {
            User user = userService.getUserById(requestDTO.userId());
            Blog blog = blogService.findBlogById(requestDTO.blogId());
            if (user == null) {
                status = Response.Status.NOT_FOUND;
                dto = new ErrorResponseDTO1("User not found");
                isSuccess = false;
            } else if (blog == null){
                status = Response.Status.NOT_FOUND;
                dto = new ErrorResponseDTO1("Blog not found");
                isSuccess = false;
            } else {
                Comment comment = requestDTO.toComment(user, blog);
                comment = commentService.createComment(comment);
                dto = new CommentResponseDTO1(comment);
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
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    @Operation(summary = "Update comment content by ID")
    public Response updateComment(@PathParam("id") Long id, @Valid CommentUpdateRequestDTO1 requestDTO) {
        Response.Status status = Response.Status.OK;
        Object dto = null;
        boolean isSuccess = true;
        try {
            Comment foundComment = commentService.getCommentById(id);
            if (foundComment == null) {
                status = Response.Status.NOT_FOUND;
                dto = new ErrorResponseDTO1("Comment not found with id");
                isSuccess = false;
            } else {
                Comment comment = commentService.updateComment(foundComment, requestDTO.content());
                dto = new CommentResponseDTO1(comment);
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
    @Transactional
    @Operation(summary = "Delete comment by ID")
    public Response deleteComment(@PathParam("id") Long id) {
        Response.Status status = Response.Status.OK;
        Object dto = null;
        boolean isSuccess = true;
        try {
            Comment foundComment = commentService.getCommentById(id);
            if (foundComment == null) {
                status = Response.Status.NOT_FOUND;
                dto = new ErrorResponseDTO1("Comment not found with id");
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
