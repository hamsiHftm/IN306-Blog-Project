package ch.hftm.blog.dto.user;

import ch.hftm.blog.entity.User;

public record UserDetailResponseDTO(Long id, String firstname, String lastname, String email) {
    public UserDetailResponseDTO(User user) {
        this(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail());
    }
}
