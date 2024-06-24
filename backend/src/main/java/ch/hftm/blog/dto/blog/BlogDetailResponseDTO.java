package ch.hftm.blog.dto.blog;

import ch.hftm.blog.dto.comment.CommentResponseDTO;
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
        List<CommentResponseDTO> commentResponseDTOs,
        UserDetailResponseDTO userDetailResponseDTO) {

    public BlogDetailResponseDTO(Blog blog) {
        this(blog.getId(),
                blog.getTitle(),
                blog.getContent(),
                blog.getCreatedAt(),
                blog.getUpdatedAt(),
                blog.getNumberOfLikes(),
                blog.getComments().stream()
                        .map(CommentResponseDTO::new)
                        .collect(Collectors.toList()),
                new UserDetailResponseDTO(blog.getUser()));
    }
}
