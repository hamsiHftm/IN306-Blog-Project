package ch.hftm.blog.dto.blog;

import ch.hftm.blog.dto.user.UserDetailResponseDTO;
import ch.hftm.blog.entity.Blog;

import java.time.LocalDateTime;

public record BlogResponseDTO(long id,
                              String title,
                              String content,
                              LocalDateTime createdAt,
                              LocalDateTime updatedAt,
                              long numberOfLikes,
                              UserDetailResponseDTO userDetailResponseDTO) {
    public BlogResponseDTO(Blog blog) {
        this(blog.getId(), blog.getTitle(), blog.getContent(), blog.getCreatedAt(), blog.getUpdatedAt(), blog.getNumberOfLikes(), new UserDetailResponseDTO(blog.getUser()));
    }
}
