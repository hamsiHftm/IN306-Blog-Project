package ch.hftm.blog.dto.blog;

import ch.hftm.blog.dto.comment.CommentResponseDTO2;
import ch.hftm.blog.dto.user.UserDetailResponseDTO1;
import ch.hftm.blog.entity.Blog;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record BlogDetailResponseDTO1(
        long id,
        String title,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        long numberOfLikes,
        String picUrl,
        List<CommentResponseDTO2> comments,
        UserDetailResponseDTO1 user) {

    public BlogDetailResponseDTO1(Blog blog) {
        this(blog.getId(),
                blog.getTitle(),
                blog.getContent(),
                blog.getCreatedAt(),
                blog.getUpdatedAt(),
                blog.getNumberOfLikes(),
                blog.getPicUrl(),
                blog.getComments().stream()
                        .map(CommentResponseDTO2::new)
                        .collect(Collectors.toList()),
                new UserDetailResponseDTO1(blog.getUser()));
    }
}
