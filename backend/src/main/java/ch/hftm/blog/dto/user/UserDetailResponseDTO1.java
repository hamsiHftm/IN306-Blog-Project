package ch.hftm.blog.dto.user;

import ch.hftm.blog.entity.User;

public record UserDetailResponseDTO1(Long id, String firstname, String lastname, String email) {
    public UserDetailResponseDTO1(User user) {
        this(user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail());
    }
}
