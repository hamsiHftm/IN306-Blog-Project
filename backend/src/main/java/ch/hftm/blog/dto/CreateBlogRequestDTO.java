package ch.hftm.blog.dto;

import ch.hftm.blog.entity.Blog;
import ch.hftm.blog.service.BlogService;

public record CreateBlogRequestDTO(String title, String content) {
    public Blog toBlog() {
        return new Blog(title, content);
    }
}
