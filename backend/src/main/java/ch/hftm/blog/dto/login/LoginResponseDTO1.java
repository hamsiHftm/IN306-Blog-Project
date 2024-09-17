package ch.hftm.blog.dto.login;

import ch.hftm.blog.entity.User;

public record LoginResponseDTO1(Long id, String firstname, String lastname, String email, String role, String token) {
    public LoginResponseDTO1(User user, String token) {
        this(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getRole(),
                token);
    }
}
