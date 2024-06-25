package ch.hftm.blog.dto.blog;

import ch.hftm.blog.dto.comment.CommentResponseDTO2;
import ch.hftm.blog.dto.user.UserDetailResponseDTO;
import ch.hftm.blog.entity.Blog;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record BlogDetailResponseDTO(
        long id,
        String title,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        long numberOfLikes,
        List<CommentResponseDTO2> comments,
        UserDetailResponseDTO user) {

    public BlogDetailResponseDTO(Blog blog) {
        this(blog.getId(),
                blog.getTitle(),
                blog.getContent(),
                blog.getCreatedAt(),
                blog.getUpdatedAt(),
                blog.getNumberOfLikes(),
                blog.getComments().stream()
                        .map(CommentResponseDTO2::new)
                        .collect(Collectors.toList()),
                new UserDetailResponseDTO(blog.getUser()));
    }
}
