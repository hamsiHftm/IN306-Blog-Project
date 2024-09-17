package ch.hftm.blog.boundry;

import ch.hftm.blog.dto.ErrorResponseDTO1;
import ch.hftm.blog.dto.ResponseDTO1;
import ch.hftm.blog.dto.rating.RatingResponseDTO1;
import ch.hftm.blog.entity.*;
import ch.hftm.blog.service.BlogService;
import ch.hftm.blog.service.RatingService;
import ch.hftm.blog.service.UserService;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
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
    @RolesAllowed({"admin", "user"})
    @Path("/{blogId}/{rating}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    @Operation(summary = "Add rating to blog by a user")
    public Response addRating(@Context SecurityContext securityContext,
                              @PathParam("blogId") long blogId,
                              @PathParam("rating") @Min(1) @Max(5) int ratingNr) {
        Response.Status status = Response.Status.OK;
        Object dto = null;
        boolean isSuccess = true;

        try {
            // Get current user from SecurityContext
            String loggedInUser = securityContext.getUserPrincipal().getName();
            User currentUser = userService.getUserById(Long.valueOf(loggedInUser));

            if (currentUser == null) {
                status = Response.Status.UNAUTHORIZED;
                dto = new ErrorResponseDTO1("User is not authenticated");
                isSuccess = false;
            } else {
                Blog blog = blogService.findBlogById(blogId);
                if (blog == null) {
                    status = Response.Status.NOT_FOUND;
                    dto = new ErrorResponseDTO1("Blog not found");
                    isSuccess = false;
                } else {
                    Rating rating = new Rating(ratingNr, blog, currentUser);
                    ratingService.addRating(rating);
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
    @Path("/{blogId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    @Operation(summary = "Remove rating from blog by a user")
    public Response removeRating(@Context SecurityContext securityContext,
                                 @PathParam("blogId") Long blogId) {
        Response.Status status = Response.Status.OK;
        Object dto = null;
        boolean isSuccess = true;

        try {
            // Get current user from SecurityContext
            String loggedInUser = securityContext.getUserPrincipal().getName();
            User currentUser = userService.getUserById(Long.valueOf(loggedInUser));

            if (currentUser == null) {
                status = Response.Status.UNAUTHORIZED;
                dto = new ErrorResponseDTO1("User is not authenticated");
                isSuccess = false;
            } else {
                Blog blog = blogService.findBlogById(blogId);
                if (blog == null) {
                    status = Response.Status.NOT_FOUND;
                    dto = new ErrorResponseDTO1("Blog not found");
                    isSuccess = false;
                } else {
                    Rating rating = ratingService.getRatingWithBlogAndUserID(blog, currentUser);
                    if (rating == null) {
                        status = Response.Status.NOT_FOUND;
                        dto = new ErrorResponseDTO1("Rating not found");
                        isSuccess = false;
                    } else {
                        ratingService.removeRating(rating);
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

    @PUT
    @RolesAllowed({"admin", "user"})
    @Path("/{blogId}/{rating}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    @Operation(summary = "Update rating for a blog by user")
    public Response updateRating(@Context SecurityContext securityContext,
                                 @PathParam("blogId") long blogId,
                                 @PathParam("rating") @Min(1) @Max(5) int ratingNr) {
        Response.Status status = Response.Status.OK;
        Object dto = null;
        boolean isSuccess = true;

        try {
            // Get current user from SecurityContext
            String loggedInUser = securityContext.getUserPrincipal().getName();
            User currentUser = userService.getUserById(Long.valueOf(loggedInUser));

            if (currentUser == null) {
                status = Response.Status.UNAUTHORIZED;
                dto = new ErrorResponseDTO1("User is not authenticated");
                isSuccess = false;
            } else {
                Blog blog = blogService.findBlogById(blogId);
                if (blog == null) {
                    status = Response.Status.NOT_FOUND;
                    dto = new ErrorResponseDTO1("Blog not found");
                    isSuccess = false;
                } else {
                    Rating rating = ratingService.getRatingWithBlogAndUserID(blog, currentUser);
                    if (rating == null) {
                        status = Response.Status.NOT_FOUND;
                        dto = new ErrorResponseDTO1("Rating not found");
                        isSuccess = false;
                    } else {
                        rating.setRating(ratingNr);
                        ratingService.editRating(rating);
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

    @GET
    @PermitAll
    @Path("/{blogId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    @Operation(summary = "Get average rating from a blog")
    public Response getAverageRating(@Context SecurityContext securityContext,
                                     @PathParam("blogId") long blogId) {
        Response.Status status = Response.Status.OK;
        Object dto = null;
        boolean isSuccess = true;

        try {
            // Check if the blog exists
            Blog blog = blogService.findBlogById(blogId);
            if (blog == null) {
                status = Response.Status.NOT_FOUND;
                dto = new ErrorResponseDTO1("Blog not found");
                isSuccess = false;
            } else {
                // Calculate average rating
                double average = ratingService.getAverageRating(blog);
                dto = new RatingResponseDTO1(blog, average);
            }
        } catch (Exception e) {
            status = Response.Status.INTERNAL_SERVER_ERROR;
            dto = new ErrorResponseDTO1(e.getMessage());
            isSuccess = false;
        }

        return Response.status(status).entity(new ResponseDTO1(isSuccess, dto)).build();
    }
}