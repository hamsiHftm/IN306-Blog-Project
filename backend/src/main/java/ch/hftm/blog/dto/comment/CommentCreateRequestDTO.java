package ch.hftm.blog.dto.comment;

import ch.hftm.blog.entity.Blog;
import ch.hftm.blog.entity.Comment;
import ch.hftm.blog.entity.User;

public record CommentCreateRequestDTO(String content, Long blogID, Long userID) {
    public Comment toComment(User user, Blog blog) {
        Comment comment = new Comment();
        comment.setContent(content);
        comment.setBlog(blog);
        comment.setUser(user);
        return comment;
    }
}
