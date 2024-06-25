package ch.hftm.blog.dto.blog;

import ch.hftm.blog.entity.Blog;
import ch.hftm.blog.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BlogCreateRequestDTO(
        @NotBlank(message = "Title must not be null")
        @Size(min=5, message = "Title must contain at least 5 characters")
        String title,
        @NotBlank(message = "Content must not be null")
        String content,
        @NotNull(message = "User ID cannot be null.")
        Long userId) {

    public Blog toBlog(User user) {
        Blog blog = new Blog(title, content);
        blog.setUser(user);
        return blog;
    }
}
