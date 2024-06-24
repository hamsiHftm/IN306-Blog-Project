package ch.hftm.blog.dto.user;

import ch.hftm.blog.entity.User;

public record UserCreateRequestDTO(String firstName, String lastName, String email, String password) {
    public User toUser() {
        User user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }
}
