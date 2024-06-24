package ch.hftm.blog.dto.comment;

import ch.hftm.blog.entity.Comment;

import java.time.LocalDateTime;

public record CommentResponseDTO(
        long id,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        int numberOfLikes) {

    public CommentResponseDTO(Comment comment) {
        this(comment.getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getUpdatedAt(),
                comment.getLikes() != null ? comment.getLikes().size() : 0);
    }
}
