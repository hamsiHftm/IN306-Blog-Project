package ch.hftm.blog.boundry;

import ch.hftm.blog.dto.ErrorResponseDTO1;
import ch.hftm.blog.dto.ResponseDTO1;
import ch.hftm.blog.dto.login.LoginResponseDTO1;
import ch.hftm.blog.dto.login.LoginRequestDTO1;
import ch.hftm.blog.service.AuthService;
import jakarta.annotation.security.PermitAll;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ch.hftm.blog.service.UserService;
import ch.hftm.blog.entity.User;
import org.eclipse.microprofile.openapi.annotations.Operation;

@Path("/auth")
public class AuthResource {

    @Inject
    UserService userService;

    @Inject
    AuthService authService;

    @POST
    @PermitAll
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Login user")
    public Response login(LoginRequestDTO1 loginRequestDTO) {
        Response.Status status = Response.Status.OK;
        Object dto = null;
        boolean isSuccess = true;
        try {
            User user = userService.getUserByEmail(loginRequestDTO.email());
            if (user == null) {
                status = Response.Status.NOT_FOUND;
                dto = new ErrorResponseDTO1("User not found with email");
                isSuccess = false;
            } else if (!user.getPassword().equals(loginRequestDTO.password())) {
                status = Response.Status.FORBIDDEN;
                dto = new ErrorResponseDTO1("Wrong password");
                isSuccess = false;
            } else {
                String token = authService.generateJwtToken(user);
                dto = new LoginResponseDTO1(user, token);
            }
        } catch (Exception e) {
            status = Response.Status.INTERNAL_SERVER_ERROR;
            dto = new ErrorResponseDTO1(e.getMessage());
            isSuccess = false;
        }
        return Response.status(status).entity(new ResponseDTO1(isSuccess, dto)).build();
    }
}