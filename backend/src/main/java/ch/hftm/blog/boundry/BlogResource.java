package ch.hftm.blog.boundry;

import ch.hftm.blog.dto.ResponseDTO1;
import ch.hftm.blog.dto.blog.*;
import ch.hftm.blog.dto.ErrorResponseDTO1;
import ch.hftm.blog.entity.Blog;
import ch.hftm.blog.entity.User;
import ch.hftm.blog.service.BlogService;
import ch.hftm.blog.service.UserService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import org.eclipse.microprofile.openapi.annotations.Operation;

import java.util.List;
import java.util.stream.Collectors;

@Path("blogs")
public class BlogResource {

    @Inject
    BlogService blogService;

    @Inject
    UserService userService;

    @GET
    @Path("public")
    @RolesAllowed("quest")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get all blogs with pagination and search")
    public Response getAllBlogs(@QueryParam("searchTitle") String searchTitle,
                                @QueryParam("limit") @DefaultValue("10") int limit,
                                @QueryParam("offset") @DefaultValue("0") int offset,
                                @QueryParam("orderBy") @DefaultValue("createdAt") String orderBy,
                                @QueryParam("asc") @DefaultValue("true") boolean asc) {
        Response.Status status = Response.Status.OK;
        Object dto = null;
        boolean isSuccess = true;
        try {
            if (!isValidOrderByField(orderBy)) {
                status = Response.Status.BAD_REQUEST;
                dto = new ErrorResponseDTO1("Invalid orderBy field. Supported fields: createdAt, title, etc.");
                isSuccess = false;
            } else {
                List<Blog> blogs = blogService.getAllBlogs(searchTitle, orderBy, limit, offset, asc);
                List<BlogResponseDTO1> blogDTOs = blogs.stream()
                        .map(BlogResponseDTO1::new)
                        .collect(Collectors.toList());
                long count = blogService.countAllBlogs(searchTitle, null);
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
    @Path("/public/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get blog by ID")
    @RolesAllowed("quest")
    public Response getBlogById(@PathParam("id") Long id) {
        Response.Status status = Response.Status.OK;
        Object dto = null;
        boolean isSuccess = true;
        try {
            Blog blog = blogService.getBlogById(id, true);
            if (blog == null) {
                status = Status.NOT_FOUND;
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
    @RolesAllowed("registered_user")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get favorite blogs by user ID with pagination and search")
    public Response getFavoriteBlogsByUserId(@PathParam("userId") Long userId,
                                             @QueryParam("searchTitle") String searchTitle,
                                             @QueryParam("limit") @DefaultValue("10") int limit,
                                             @QueryParam("offset") @DefaultValue("0") int offset,
                                             @QueryParam("orderBy") @DefaultValue("createdAt") String orderBy,
                                             @QueryParam("asc") @DefaultValue("true") boolean asc) {
        Response.Status status = Response.Status.OK;
        Object dto = null;
        boolean isSuccess = true;
        try {
            List<Blog> blogs = blogService.getFavoriteBlogsByUserId(userId, searchTitle,orderBy, limit, offset, asc);
            List<BlogResponseDTO1> blogDTOs = blogs.stream()
                    .map(BlogResponseDTO1::new)
                    .collect(Collectors.toList());
            long count = blogService.countAllBlogs(searchTitle, userId);
            dto = new BlogListResponseDTO1(blogDTOs, offset, limit, searchTitle, count);
        } catch (Exception e) {
            status = Response.Status.INTERNAL_SERVER_ERROR;
            dto = new ErrorResponseDTO1(e.getMessage());
            isSuccess = false;
        }
        return Response.status(status).entity(new ResponseDTO1(isSuccess, dto)).build();
    }

    @POST
    @RolesAllowed("registered_user")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    @Operation(summary = "Add a new blog")
    public Response addBlog(@Valid BlogCreateRequestDTO1 blogDTO) {
        Response.Status status = Response.Status.OK;
        Object dto = null;
        boolean isSuccess = true;
        try {
            // Retrieve the User entity based on the userId from blogDTO
            User user = userService.getUserById(blogDTO.userId());
            if (user == null) {
                status = Status.BAD_REQUEST;
                dto = new ErrorResponseDTO1("User with id " + blogDTO.userId() + " not found");
                isSuccess = false;
            } else {
                var blog = blogDTO.toBlog(user);
                Blog createdBlog = blogService.addBlog(blog);
                dto = new BlogResponseDTO1(createdBlog);
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
    @RolesAllowed("registered_user")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Update a blog by ID")
    public Response updateBlog(@PathParam("id") Long id, @Valid BlogUpdateRequestDTO1 requestDTO) {
        Response.Status status = Response.Status.OK;
        Object dto = null;
        boolean isSuccess = true;
        try {
            Blog foundBlog = blogService.getBlogById(id, false);
            if (foundBlog == null) {
                status = Response.Status.NOT_FOUND;
                dto = new ErrorResponseDTO1("Blog not found");
                isSuccess = false;
            } else {
                blogService.updateBlog(foundBlog, requestDTO.title(), requestDTO.content());
                dto = new BlogResponseDTO1(foundBlog);
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
    @RolesAllowed("registered_user")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Delete a blog by ID")
    public Response deleteBlog(@PathParam("id") Long id) {
        Response.Status status = Response.Status.OK;
        Object dto = null;
        boolean isSuccess = true;
        try {
            Blog blog = blogService.getBlogById(id, false);
            if (blog == null) {
                status = Response.Status.NOT_FOUND;
                dto = new ErrorResponseDTO1("Blog not found");
                isSuccess = false;
            } else {
                blogService.deleteBlog(blog);
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
