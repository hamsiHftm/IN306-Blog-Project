package ch.hftm.blog.dto.blog;

import ch.hftm.blog.entity.Blog;
import ch.hftm.blog.entity.User;

public record BlogCreateRequestDTO(String title, String content, Long userId) {
    public Blog toBlog(User user) {
        Blog blog = new Blog(title, content);
        blog.setUser(user);
        return blog;
    }

    public Long getUserId() {
        return userId;
    }
}
