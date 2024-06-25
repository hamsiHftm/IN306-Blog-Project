package ch.hftm.blog.dto.user;

import jakarta.validation.constraints.NotBlank;

public record UserChangePasswordRequestDTO(
        @NotBlank(message = "New Password must not be null nor empty")
        String newPassword,
        @NotBlank(message = "Confirm Password must not be null nor empty")
        String confirmPassword) {
}
