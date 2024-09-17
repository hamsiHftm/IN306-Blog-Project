package ch.hftm.blog.boundry;

import ch.hftm.blog.dto.ErrorResponseDTO1;
import ch.hftm.blog.dto.ResponseDTO1;
import ch.hftm.blog.dto.user.*;
import ch.hftm.blog.entity.User;
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
import jakarta.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.openapi.annotations.Operation;

@Path("user")
public class UserResource {

    @Inject
    UserService userService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    @PermitAll
    @Operation(summary = "Create new user")
    public Response createUser(@Valid UserCreateRequestDTO1 requestDTO) {
        Response.Status status = Response.Status.OK;
        Object dto = null;
        boolean isSuccess = true;
        try {
            User foundUser = userService.getUserByEmail(requestDTO.email());
            if (foundUser != null) {
                status = Response.Status.BAD_REQUEST;
                dto = new ErrorResponseDTO1("User already exists");
                isSuccess = false;
            } else {
                User createdUser = userService.createUser(requestDTO.toUser());
                dto = new UserDetailResponseDTO1(createdUser);
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
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    @RolesAllowed({"admin", "user"})
    @Operation(summary = "Update user firstname and lastname by ID")
    public Response updateUser(@PathParam("id") Long id, @Valid UserUpdateRequestDTO1 requestDTO, @Context SecurityContext securityContext) {
        Response.Status status = Response.Status.OK;
        Object dto = null;
        boolean isSuccess = true;
        try {
            // Get current user from SecurityContext
            String loggedInUserId = securityContext.getUserPrincipal().getName();
            User currentUser = userService.getUserById(Long.valueOf(loggedInUserId));

            // Check if the user is authenticated
            if (currentUser == null) {
                status = Response.Status.UNAUTHORIZED;
                dto = new ErrorResponseDTO1("User is not authenticated");
                isSuccess = false;
            } else if (!"admin".equals(currentUser.getRole()) && !currentUser.getId().equals(id)) {
                // Check if the user is an admin or updating their own details
                status = Response.Status.FORBIDDEN;
                dto = new ErrorResponseDTO1("Not authorized to update this user");
                isSuccess = false;
            } else {
                User user = userService.updateUserName(currentUser, requestDTO.firstname(), requestDTO.lastname());
                dto = new UserDetailResponseDTO1(user);
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
    @Operation(summary = "Delete user by ID")
    public Response deleteUser(@PathParam("id") Long id, @Context SecurityContext securityContext) {
        Response.Status status = Response.Status.OK;
        Object dto = null;
        boolean isSuccess = true;
        try {
            // Get current user from SecurityContext
            String loggedInUserId = securityContext.getUserPrincipal().getName();
            User currentUser = userService.getUserById(Long.valueOf(loggedInUserId));

            // Check if the user is authenticated
            if (currentUser == null) {
                status = Response.Status.UNAUTHORIZED;
                dto = new ErrorResponseDTO1("User is not authenticated");
                isSuccess = false;
            } else if (!"admin".equals(currentUser.getRole()) && !currentUser.getId().equals(id)) {
                // Check if the user is an admin or deleting their own account
                status = Response.Status.FORBIDDEN;
                dto = new ErrorResponseDTO1("Not authorized to delete this user");
                isSuccess = false;
            } else {
                User user = userService.getUserById(id);
                if (user == null) {
                    status = Response.Status.NOT_FOUND;
                    dto = new ErrorResponseDTO1("User not found");
                    isSuccess = false;
                } else {
                    userService.deleteUser(user);
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
    @Path("{id}/password")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    @RolesAllowed({"admin", "user"})
    @Operation(summary = "Change user password by ID")
    public Response changePassword(@PathParam("id") Long id, @Valid UserChangePasswordRequestDTO1 requestDTO, @Context SecurityContext securityContext) {
        Response.Status status = Response.Status.OK;
        Object dto = null;
        boolean isSuccess = true;
        try {
            // Get current user from SecurityContext
            String loggedInUserId = securityContext.getUserPrincipal().getName();
            User currentUser = userService.getUserById(Long.valueOf(loggedInUserId));

            // Check if the user is authenticated
            if (currentUser == null) {
                status = Response.Status.UNAUTHORIZED;
                dto = new ErrorResponseDTO1("User is not authenticated");
                isSuccess = false;
            } else if (!"admin".equals(currentUser.getRole()) && !currentUser.getId().equals(id)) {
                // Check if the user is an admin or changing their own password
                status = Response.Status.FORBIDDEN;
                dto = new ErrorResponseDTO1("Not authorized to change this user's password");
                isSuccess = false;
            } else {
                User user = userService.getUserById(id);
                if (user == null) {
                    status = Response.Status.NOT_FOUND;
                    dto = new ErrorResponseDTO1("User not found with id");
                    isSuccess = false;
                } else if (!user.getPassword().equals(requestDTO.confirmPassword())) {
                    status = Response.Status.FORBIDDEN;
                    dto = new ErrorResponseDTO1("Wrong password");
                    isSuccess = false;
                } else {
                    userService.changePassword(user, requestDTO.newPassword());
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