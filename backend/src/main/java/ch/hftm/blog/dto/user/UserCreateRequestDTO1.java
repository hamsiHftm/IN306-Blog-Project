package ch.hftm.blog.dto.user;

import ch.hftm.blog.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UserCreateRequestDTO1(
        String firstName,
        String lastName,
        @NotBlank(message = "Email must not be null nor blank nor empty")
        @Email(message = "Email should be valid")
        String email,
        @NotNull(message = "Password must not be null nor blank nor empty")
        String password,
        @NotBlank(message = "Role must not be null nor blank nor empty")
        @Pattern(regexp = "^(user|admin)$", message = "Role must be either 'user' or 'admin'")
        String role) {
    public User toUser() {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);
        return user;
    }
}
