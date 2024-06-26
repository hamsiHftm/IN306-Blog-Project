package ch.hftm.blog.boundry;

import ch.hftm.blog.dto.ErrorResponseDTO;
import ch.hftm.blog.dto.ResponseDTO;
import ch.hftm.blog.dto.rating.RatingResponseDTO;
import ch.hftm.blog.entity.*;
import ch.hftm.blog.service.BlogService;
import ch.hftm.blog.service.RatingService;
import ch.hftm.blog.service.UserService;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;

@Path("rating")
public class RatingResource {
    @Inject
    RatingService ratingService;

    @Inject
    BlogService blogService;

    @Inject
    UserService userService;

    @POST
    @Path("/{userId}/{blogId}/{rating}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    @Operation(summary = "Add rating to blog by a user")
    public Response addRating(@PathParam("userId") long userId,
                                  @PathParam("blogId") long blogId,
                                  @PathParam("rating") @Min(1) @Max(5) int ratingNr) {
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
                Rating rating = new Rating(ratingNr, blog, user);
                ratingService.addRating(rating);
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
    @Operation(summary = "Remove rating from blog by a user")
    public Response removeRating(@PathParam("userId") Long userId,
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
                Rating rating = ratingService.getRatingWithBlogAndUserID(blog, user);
                if (rating == null) {
                    status = Response.Status.NOT_FOUND;
                    dto = new ErrorResponseDTO("Rating not found");
                    isSuccess = false;
                } else {
                    ratingService.removeRating(rating);
                }
            }
        } catch (Exception e) {
            status = Response.Status.INTERNAL_SERVER_ERROR;
            dto = new ErrorResponseDTO(e.getMessage());
            isSuccess = false;
        }
        return Response.status(status).entity(new ResponseDTO(isSuccess, dto)).build();
    }


    @PUT
    @Path("{userId}/{blogId}/{rating}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    @Operation(summary = "Update rating for a blog by user")
    public Response updateRating(@PathParam("userId") long userId,
                                 @PathParam("blogId") long blogId,
                                 @PathParam("rating") @Min(1) @Max(5) int ratingNr) {
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
                Rating rating = ratingService.getRatingWithBlogAndUserID(blog, user);
                if (rating == null) {
                    status = Response.Status.NOT_FOUND;
                    dto = new ErrorResponseDTO("Rating not found");
                    isSuccess = false;
                } else {
                    rating.setRating(ratingNr);
                    ratingService.editRating(rating);
                }
            }
        } catch (Exception e) {
            status = Response.Status.INTERNAL_SERVER_ERROR;
            dto = new ErrorResponseDTO(e.getMessage());
            isSuccess = false;
        }
        return Response.status(status).entity(new ResponseDTO(isSuccess, dto)).build();
    }

    @GET
    @Path("{blogId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    @Operation(summary = "Get average rating from a blog")
    public Response getAverageRating(@PathParam("blogId") long blogId) {
        Response.Status status = Response.Status.OK;
        Object dto = null;
        boolean isSuccess = true;
        try {
            Blog blog = blogService.findBlogById(blogId);
            if (blog == null){
                status = Response.Status.NOT_FOUND;
                dto = new ErrorResponseDTO("Blog not found");
                isSuccess = false;
            } else {
                double average = ratingService.getAverageRating(blog);
                dto = new RatingResponseDTO(blog, average);
            }
        } catch (Exception e) {
            status = Response.Status.INTERNAL_SERVER_ERROR;
            dto = new ErrorResponseDTO(e.getMessage());
            isSuccess = false;
        }
        return Response.status(status).entity(new ResponseDTO(isSuccess, dto)).build();
    }

}
