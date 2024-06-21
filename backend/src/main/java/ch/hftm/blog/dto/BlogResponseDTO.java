package ch.hftm.blog.dto;

import ch.hftm.blog.entity.Blog;

import java.time.LocalDateTime;

public record BlogResponseDTO(long id,
                              String title,
                              String content,
                              LocalDateTime createdAt,
                              LocalDateTime updatedAt,
                              long numberOfLikes) {
    public BlogResponseDTO(Blog blog) {
        this(blog.getId(), blog.getTitle(), blog.getContent(), blog.getCreatedAt(), blog.getUpdatedAt(), blog.getNumberOfLikes());
    }
}
