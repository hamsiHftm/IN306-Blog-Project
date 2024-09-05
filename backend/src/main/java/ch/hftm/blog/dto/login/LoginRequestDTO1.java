package ch.hftm.blog.dto.login;

import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO1(
        @NotBlank(message = "Email must not be null nor blank nor empty")
        String email,
        @NotBlank(message = "Password must not be null nor blank nor empty")
        String password) {
}
