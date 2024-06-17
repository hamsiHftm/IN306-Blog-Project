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
    private CommentRepository commentRepository;

    public List<Comment> getAllCommentsForBlog(String blogId) {
        return commentRepository.findByBlogId(blogId);
    }

    public Comment createComment(Comment comment) {
        Log.info("Adding comment: " + comment);
        commentRepository.persist(comment);
        return comment;
    }
}
