package ch.hftm.blog.dto.user;

import ch.hftm.blog.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserCreateRequestDTO(
        String firstName,
        String lastName,
        @NotBlank(message = "Email must not be null nor blank nor empty")
        @Email(message = "Email should be valid")
        String email,
        @NotNull(message = "Password must not be null nor blank nor empty")
        String password) {
    public User toUser() {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }
}
