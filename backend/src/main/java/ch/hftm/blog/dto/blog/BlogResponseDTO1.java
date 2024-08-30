package ch.hftm.blog.dto.blog;

import ch.hftm.blog.dto.user.UserDetailResponseDTO1;
import ch.hftm.blog.entity.Blog;

import java.time.LocalDateTime;

public record BlogResponseDTO1(long id,
                               String title,
                               String content,
                               LocalDateTime createdAt,
                               LocalDateTime updatedAt,
                               long numberOfLikes,
                               String picUrl,
                               UserDetailResponseDTO1 user) {
    public BlogResponseDTO1(Blog blog) {
        this(blog.getId(), blog.getTitle(), blog.getContent(), blog.getCreatedAt(), blog.getUpdatedAt(), blog.getNumberOfLikes(), blog.getPicUrl(), new UserDetailResponseDTO1(blog.getUser()));
    }
}
