package ch.hftm.blog.boundry;

import ch.hftm.blog.dto.ResponseDTO1;
import ch.hftm.blog.dto.blog.*;
import ch.hftm.blog.dto.ErrorResponseDTO1;
import ch.hftm.blog.entity.Blog;
import ch.hftm.blog.entity.User;
import ch.hftm.blog.service.BlogService;
import ch.hftm.blog.service.UserService;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.openapi.annotations.Operation;

import java.util.List;
import java.util.stream.Collectors;

import static io.smallrye.config.common.utils.StringUtil.isNumeric;

@Path("blogs")
public class BlogResource {

    @Inject
    BlogService blogService;

    @Inject
    UserService userService;

    @GET
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get all blogs with pagination and search")
    public Response getAllBlogs(@QueryParam("searchTitle") String searchTitle,
                                @QueryParam("userId") Long userId,
                                @QueryParam("limit") @DefaultValue("10") int limit,
                                @QueryParam("offset") @DefaultValue("0") int offset,
                                @QueryParam("orderBy") @DefaultValue("createdAt") String orderBy,
                                @QueryParam("asc") @DefaultValue("true") boolean asc) {
        Response.Status status = Response.Status.OK;
        Object dto = null;
        boolean isSuccess = true;
        try {
            if (userId != null && !isNumeric(userId.toString())) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity(new ErrorResponseDTO1("Invalid userId: Must be a numeric value"))
                        .build();
            }
            if (!isValidOrderByField(orderBy)) {
                status = Response.Status.BAD_REQUEST;
                dto = new ErrorResponseDTO1("Invalid orderBy field. Supported fields: createdAt, title, etc.");
                isSuccess = false;
            } else {
                List<Blog> blogs = blogService.getAllBlogs(searchTitle, userId, orderBy, limit, offset, asc);
                List<BlogResponseDTO1> blogDTOs = blogs.stream()
                        .map(BlogResponseDTO1::new)
                        .collect(Collectors.toList());
                long count = blogService.countAllBlogs(searchTitle, userId);
                dto = new BlogListResponseDTO1(blogDTOs, offset, limit, searchTitle, count);
            }
        } catch (Exception e) {
            status = Response.Status.INTERNAL_SERVER_ERROR;
            dto = new ErrorResponseDTO1(e.getMessage());
            isSuccess = false;
        }
        return Response.status(status).entity(new ResponseDTO1(isSuccess, dto)).build();
    }

    @GET
    @Path("/{id}")
    @PermitAll
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get blog by ID")
    public Response getBlogById(@PathParam("id") Long id) {
        Response.Status status = Response.Status.OK;
        Object dto = null;
        boolean isSuccess = true;
        try {
            Blog blog = blogService.getBlogById(id, true);
            if (blog == null) {
                status = Response.Status.NOT_FOUND;
                dto = new ErrorResponseDTO1("Blog not found");
                isSuccess = false;
            } else {
                dto = new BlogDetailResponseDTO1(blog);
            }
        } catch (Exception e) {
            status = Response.Status.INTERNAL_SERVER_ERROR;
            dto = new ErrorResponseDTO1(e.getMessage());
            isSuccess = false;
        }
        return Response.status(status).entity(new ResponseDTO1(isSuccess, dto)).build();
    }
    
    @GET
    @Path("favourites/{userId}")
    @RolesAllowed({"admin", "user"})
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get favorite blogs by user ID with pagination and search")
    public Response getFavoriteBlogsByUserId(@Context SecurityContext securityContext,
                                             @PathParam("userId") Long userId,
                                             @QueryParam("searchTitle") String searchTitle,
                                             @QueryParam("limit") @DefaultValue("10") int limit,
                                             @QueryParam("offset") @DefaultValue("0") int offset,
                                             @QueryParam("orderBy") @DefaultValue("createdAt") String orderBy,
                                             @QueryParam("asc") @DefaultValue("true") boolean asc) {
        Response.Status status = Response.Status.OK;
        Object dto = null;
        boolean isSuccess = true;
        try {
            // Get current user from SecurityContext
            String loggedInUser = securityContext.getUserPrincipal().getName();
            User currentUser = userService.getUserById(Long.valueOf(loggedInUser));

            // Check if the user is authenticated and has the necessary access
            if (currentUser == null) {
                status = Response.Status.UNAUTHORIZED;
                dto = new ErrorResponseDTO1("User is not authenticated");
                isSuccess = false;
            } else if (!"admin".equals(currentUser.getRole()) && !currentUser.getId().equals(userId)) {
                status = Response.Status.FORBIDDEN;
                dto = new ErrorResponseDTO1("Not authorized to view favorite blogs for this user");
                isSuccess = false;
            } else {
                // Fetch favorite blogs and prepare response
                List<Blog> blogs = blogService.getFavoriteBlogsByUserId(userId, searchTitle, orderBy, limit, offset, asc);
                List<BlogResponseDTO1> blogDTOs = blogs.stream()
                        .map(BlogResponseDTO1::new)
                        .collect(Collectors.toList());
                long count = blogService.countAllBlogs(searchTitle, userId);
                dto = new BlogListResponseDTO1(blogDTOs, offset, limit, searchTitle, count);
            }
        } catch (Exception e) {
            status = Response.Status.INTERNAL_SERVER_ERROR;
            dto = new ErrorResponseDTO1(e.getMessage());
            isSuccess = false;
        }
        return Response.status(status).entity(new ResponseDTO1(isSuccess, dto)).build();
    }

    @POST
    @RolesAllowed({"admin", "user"})
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Add a new blog")
    public Response addBlog(@Context SecurityContext securityContext, @Valid BlogCreateRequestDTO1 blogDTO) {
        Response.Status status = Response.Status.OK;
        Object dto = null;
        boolean isSuccess = true;
        try {
            // Get current user from SecurityContext
            String loggedInUser = securityContext.getUserPrincipal().getName();
            User currentUser = userService.getUserById(Long.valueOf(loggedInUser));

            // Check if the user is authenticated and has the necessary access
            if (currentUser == null) {
                status = Response.Status.UNAUTHORIZED;
                dto = new ErrorResponseDTO1("User is not authenticated");
                isSuccess = false;
            } else if (!"admin".equals(currentUser.getRole()) && !currentUser.getId().equals(blogDTO.userId())) {
                status = Response.Status.FORBIDDEN;
                dto = new ErrorResponseDTO1("Not authorized to view favorite blogs for this user");
                isSuccess = false;
            } else {
                // Retrieving user
                User user = userService.getUserById(blogDTO.userId());
                // checking if user exists
                if (user == null) {
                    status = Status.BAD_REQUEST;
                    dto = new ErrorResponseDTO1("User with id " + blogDTO.userId() + " not found");
                    isSuccess = false;
                } else {
                    // adding blog
                    var blog = blogDTO.toBlog(user);
                    Blog createdBlog = blogService.addBlog(blog);
                    dto = new BlogResponseDTO1(createdBlog);
                }
            }
        } catch (Exception e) {
            status = Response.Status.INTERNAL_SERVER_ERROR;
            dto = new ErrorResponseDTO1(e.getMessage());
            isSuccess = false;
        }
        return Response.status(status).entity(new ResponseDTO1(isSuccess, dto)).build();
    }

    @PATCH
    @Path("{id}")
    @Transactional
    @RolesAllowed({"admin", "user"})
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Update a blog by ID")
    public Response updateBlog(@PathParam("id") Long id,
                               @Context SecurityContext securityContext,
                               @Valid BlogUpdateRequestDTO1 requestDTO) {
        Response.Status status = Response.Status.OK;
        Object dto = null;
        boolean isSuccess = true;
        try {
            // Get current user from SecurityContext
            String loggedInUser = securityContext.getUserPrincipal().getName();
            User currentUser = userService.getUserById(Long.valueOf(loggedInUser));

            // Check if the user is authenticated
            if (currentUser == null) {
                status = Response.Status.UNAUTHORIZED;
                dto = new ErrorResponseDTO1("User is not authenticated");
                isSuccess = false;
            } else {
                // Check if the user is an admin or the owner of the blog
                boolean isAdmin = "admin".equals(currentUser.getRole());
                Blog foundBlog = blogService.getBlogById(id, false);

                if (foundBlog == null) {
                    status = Response.Status.NOT_FOUND;
                    dto = new ErrorResponseDTO1("Blog not found");
                    isSuccess = false;
                } else if (!isAdmin && !foundBlog.getUser().getId().equals(currentUser.getId())) {
                    status = Response.Status.FORBIDDEN;
                    dto = new ErrorResponseDTO1("Not authorized to update this blog");
                    isSuccess = false;
                } else {
                    // Update the blog
                    blogService.updateBlog(foundBlog, requestDTO.title(), requestDTO.content());
                    dto = new BlogResponseDTO1(foundBlog);
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
    @Path("{id}")
    @Transactional
    @RolesAllowed({"admin", "user"})
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Delete a blog by ID")
    public Response deleteBlog(@PathParam("id") Long id, @Context SecurityContext securityContext) {
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
            } else {
                // Fetch the blog to be deleted
                Blog blog = blogService.getBlogById(id, false);

                if (blog == null) {
                    status = Response.Status.NOT_FOUND;
                    dto = new ErrorResponseDTO1("Blog not found");
                    isSuccess = false;
                } else if (!"admin".equals(currentUser.getRole()) && !blog.getUser().getId().equals(currentUser.getId())) {
                    status = Response.Status.FORBIDDEN;
                    dto = new ErrorResponseDTO1("Not authorized to delete this blog");
                    isSuccess = false;
                } else {
                    // Delete the blog
                    blogService.deleteBlog(blog);
                    dto = "Blog deleted successfully"; // or use a specific DTO for confirmation if needed
                }
            }
        } catch (Exception e) {
            status = Response.Status.INTERNAL_SERVER_ERROR;
            dto = new ErrorResponseDTO1(e.getMessage());
            isSuccess = false;
        }
        return Response.status(status).entity(new ResponseDTO1(isSuccess, dto)).build();
    }

    private boolean isValidOrderByField(String field) {
        List<String> validFields = List.of("id", "createdAt", "updatedAt", "title");
        return validFields.contains(field);
    }
}
