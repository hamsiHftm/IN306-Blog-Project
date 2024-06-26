package ch.hftm.blog.dto.comment;

import ch.hftm.blog.entity.Blog;
import ch.hftm.blog.entity.Comment;
import ch.hftm.blog.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CommentCreateRequestDTO(
        @NotBlank(message = "Content must not be blank or null")
        String content,
        @NotNull(message = "Blog-Id must not be null")
        Long blogId,
        @NotNull(message = "User-Id must not be null")
        Long userId) {
    public Comment toComment(User user, Blog blog) {
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setBlog(blog);
        comment.setUser(user);
        return comment;
    }
}
