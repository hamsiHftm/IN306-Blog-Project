package ch.hftm.blog.dto.comment;

import ch.hftm.blog.dto.blog.BlogResponseDTO2;
import ch.hftm.blog.dto.user.UserDetailResponseDTO;
import ch.hftm.blog.entity.Comment;

import java.time.LocalDateTime;

public record CommentResponseDTO(
        long id,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        int numberOfLikes,
        UserDetailResponseDTO user,
        BlogResponseDTO2 blog) {

    public CommentResponseDTO(Comment comment) {
        this(comment.getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getUpdatedAt(),
                comment.getLikes() != null ? comment.getLikes().size() : 0,
                new UserDetailResponseDTO(comment.getUser()),
                new BlogResponseDTO2(comment.getBlog()));
    }
}
