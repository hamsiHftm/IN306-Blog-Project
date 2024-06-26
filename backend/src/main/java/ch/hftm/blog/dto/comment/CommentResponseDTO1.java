package ch.hftm.blog.dto.comment;

import ch.hftm.blog.dto.blog.BlogResponseDTO2;
import ch.hftm.blog.dto.user.UserDetailResponseDTO1;
import ch.hftm.blog.entity.Comment;

import java.time.LocalDateTime;

public record CommentResponseDTO1(
        long id,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        int numberOfLikes,
        UserDetailResponseDTO1 user,
        BlogResponseDTO2 blog) {

    public CommentResponseDTO1(Comment comment) {
        this(comment.getId(),
                comment.getContent(),
                comment.getCreatedAt(),
                comment.getUpdatedAt(),
                comment.getLikes() != null ? comment.getLikes().size() : 0,
                new UserDetailResponseDTO1(comment.getUser()),
                new BlogResponseDTO2(comment.getBlog()));
    }
}
