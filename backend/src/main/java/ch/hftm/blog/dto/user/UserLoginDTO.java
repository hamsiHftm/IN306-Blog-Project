package ch.hftm.blog.dto.user;

import jakarta.validation.constraints.NotBlank;

public record UserLoginDTO(
        @NotBlank(message = "Email must not be null nor blank nor empty")
        String email,
        @NotBlank(message = "Password must not be null nor blank nor empty")
        String password) {
}
