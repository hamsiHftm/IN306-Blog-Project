package ch.hftm.blog.boundry;

import ch.hftm.blog.dto.ResponseDTO;
import ch.hftm.blog.dto.blog.*;
import ch.hftm.blog.dto.ErrorResponseDTO;
import ch.hftm.blog.entity.Blog;
import ch.hftm.blog.entity.User;
import ch.hftm.blog.service.BlogService;
import ch.hftm.blog.service.UserService;
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
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get all blogs with pagination and search")
    public Response getAllBlogs(@QueryParam("searchTitle") String searchTitle,
                                @QueryParam("limit") @DefaultValue("10") int limit,
                                @QueryParam("offset") @DefaultValue("0") int offset) {
        Response.Status status = Response.Status.OK;
        Object dto = null;
        boolean isSuccess = true;
        try {
            List<Blog> blogs = blogService.getAllBlogs(searchTitle, limit, offset);
            List<BlogResponseDTO> blogDTOs = blogs.stream()
                    .map(BlogResponseDTO::new)
                    .collect(Collectors.toList());
            dto = new BlogListResponseDTO(blogDTOs, offset, limit, searchTitle);
        } catch (Exception e) {
            status = Response.Status.INTERNAL_SERVER_ERROR;
            dto = new ErrorResponseDTO(e.getMessage());
            isSuccess = false;
        }
        return Response.status(status).entity(new ResponseDTO(isSuccess, dto)).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get blog by ID")
    public Response getBlogById(@PathParam("id") Long id) {
        Response.Status status = Response.Status.OK;
        Object dto = null;
        boolean isSuccess = true;
        try {
            Blog blog = blogService.getBlogById(id, true);
            if (blog == null) {
                status = Status.NOT_FOUND;
                dto = new ErrorResponseDTO("Blog not found");
                isSuccess = false;
            } else {
                dto = new BlogDetailResponseDTO(blog);
            }
        } catch (Exception e) {
            status = Response.Status.INTERNAL_SERVER_ERROR;
            dto = new ErrorResponseDTO(e.getMessage());
            isSuccess = false;
        }
        return Response.status(status).entity(new ResponseDTO(isSuccess, dto)).build();
    }

    @GET
    @Path("favourites/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get favorite blogs by user ID with pagination and search")
    public Response getFavoriteBlogsByUserId(@PathParam("userId") Long userId,
                                             @QueryParam("searchTitle") String searchTitle,
                                             @QueryParam("limit") @DefaultValue("10") int limit,
                                             @QueryParam("offset") @DefaultValue("0") int offset) {
        Response.Status status = Response.Status.OK;
        Object dto = null;
        boolean isSuccess = true;
        try {
            List<Blog> blogs = blogService.getFavoriteBlogsByUserId(userId, searchTitle, limit, offset);
            List<BlogResponseDTO> blogDTOs = blogs.stream()
                    .map(BlogResponseDTO::new)
                    .collect(Collectors.toList());
            dto = new BlogListResponseDTO(blogDTOs, offset, limit, searchTitle);
        } catch (Exception e) {
            status = Response.Status.INTERNAL_SERVER_ERROR;
            dto = new ErrorResponseDTO(e.getMessage());
            isSuccess = false;
        }
        return Response.status(status).entity(new ResponseDTO(isSuccess, dto)).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    @Operation(summary = "Add a new blog")
    public Response addBlog(@Valid BlogCreateRequestDTO blogDTO) {
        Response.Status status = Response.Status.OK;
        Object dto = null;
        boolean isSuccess = true;
        try {
            // Retrieve the User entity based on the userId from blogDTO
            User user = userService.getUserById(blogDTO.getUserId());
            if (user == null) {
                status = Status.BAD_REQUEST;
                dto = new ErrorResponseDTO("User with id " + blogDTO.getUserId() + " not found");
                isSuccess = false;
            } else {
                var blog = blogDTO.toBlog(user);
                Blog createdBlog = blogService.addBlog(blog);
                dto = new BlogResponseDTO(createdBlog);
            }
        } catch (Exception e) {
            status = Response.Status.INTERNAL_SERVER_ERROR;
            dto = new ErrorResponseDTO(e.getMessage());
            isSuccess = false;
        }
        return Response.status(status).entity(new ResponseDTO(isSuccess, dto)).build();
    }

    @PATCH
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Update a blog by ID")
    public Response updateBlog(@PathParam("id") Long id, BlogUpdateRequestDTO requestDTO) {
        Response.Status status = Response.Status.OK;
        Object dto = null;
        boolean isSuccess = true;
        try {
            Blog foundBlog = blogService.getBlogById(id, false);
            if (foundBlog == null) {
                status = Response.Status.NOT_FOUND;
                dto = new ErrorResponseDTO("Blog not found");
                isSuccess = false;
            } else {
                blogService.updateBlog(foundBlog, requestDTO.title(), requestDTO.content());
                dto = new BlogResponseDTO(foundBlog);
            }
        } catch (Exception e) {
            status = Response.Status.INTERNAL_SERVER_ERROR;
            dto = new ErrorResponseDTO(e.getMessage());
            isSuccess = false;
        }
        return Response.status(status).entity(new ResponseDTO(isSuccess, dto)).build();
    }

    @DELETE
    @Path("{id}")
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
                dto = new ErrorResponseDTO("Blog not found");
                isSuccess = false;
            } else {
                blogService.deleteBlog(blog);
                dto = new BlogResponseDTO(blog);
            }
        } catch (Exception e) {
            status = Response.Status.INTERNAL_SERVER_ERROR;
            dto = new ErrorResponseDTO(e.getMessage());
            isSuccess = false;
        }
        return Response.status(status).entity(new ResponseDTO(isSuccess, dto)).build();
    }
}
