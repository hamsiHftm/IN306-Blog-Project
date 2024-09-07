package ch.hftm.blog.boundry;

import ch.hftm.blog.dto.ErrorResponseDTO1;
import ch.hftm.blog.dto.ResponseDTO1;
import ch.hftm.blog.dto.blog.BlogDetailResponseDTO1;
import ch.hftm.blog.dto.blog.BlogListResponseDTO1;
import ch.hftm.blog.dto.blog.BlogResponseDTO1;
import ch.hftm.blog.entity.Blog;
import ch.hftm.blog.service.BlogService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;

import java.util.List;
import java.util.stream.Collectors;

import static io.smallrye.config.common.utils.StringUtil.isNumeric;

@Path("public/blog")
public class BlogPublicResource {
    @Inject
    BlogService blogService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Get all blogs with pagination and search")
    public Response getAllBlogs(@QueryParam("searchTitle") String searchTitle,
                                @QueryParam("userId") Integer userId,
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
    @Path("/{id}")
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


    private boolean isValidOrderByField(String field) {
        List<String> validFields = List.of("id", "createdAt", "updatedAt", "title");
        return validFields.contains(field);
    }
}
