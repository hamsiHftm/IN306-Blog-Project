package ch.hftm.blog.dto.blog;

import ch.hftm.blog.entity.Blog;

public record BlogResponseDTO2(Long blogID, String title) {
    public BlogResponseDTO2(Blog blog) {
        this(blog.getId(), blog.getTitle());
    }
}
