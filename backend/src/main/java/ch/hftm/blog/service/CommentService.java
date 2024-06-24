package ch.hftm.blog.service;

import ch.hftm.blog.entity.Comment;
import ch.hftm.blog.repository.CommentRepository;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;

import java.util.List;

@Dependent
public class CommentService {
    @Inject
    CommentRepository commentRepository;

    public List<Comment> getAllCommentsForBlog(String blogId) {
        return commentRepository.findByBlogId(blogId);
    }

    public Comment createComment(Comment comment) {
        Log.info("Adding comment: " + comment);
        commentRepository.persist(comment);
        return comment;
    }

    public Comment updateComment(Comment comment, String content) {
        comment.setContent(content);
        commentRepository.persist(comment);
        return comment;
    }

    public void deleteComment(Comment comment) {
        commentRepository.delete(comment);
    }

    public Comment getCommentById(Long id) {
        return commentRepository.findById(id);
    }
}
