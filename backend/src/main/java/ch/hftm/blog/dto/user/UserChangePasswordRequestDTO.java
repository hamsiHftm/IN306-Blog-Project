package ch.hftm.blog.dto.user;

public record UserChangePasswordRequestDTO(String newPassword, String confirmPassword) {
}
