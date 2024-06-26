package ch.hftm.blog.service;

import ch.hftm.blog.entity.Comment;
import ch.hftm.blog.entity.CommentLike;
import ch.hftm.blog.entity.User;
import ch.hftm.blog.repository.CommentLikeRepository;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;

@Dependent
public class CommentLikeService {
    @Inject
    CommentLikeRepository likeRepository;

    public void addLikeToComment(Comment comment, User user) {
        CommentLike like = new CommentLike(comment, user);
        likeRepository.persist(like);
        Log.info("A like added to " + comment.getId() + " by user: " + user.getEmail());
    }

    public void removeLikeToComment(CommentLike like) {
        long commentId = like.getComment().getId();
        String userMail = like.getUser().getEmail();
        likeRepository.delete(like);
        Log.info("Like removed from " + commentId + " by user: " + userMail);
    }

    public CommentLike getCommentLikeByCommentAndUserID(long commentID, long userID) {
        return likeRepository.findByCommentAndUser(commentID, userID);
    }
}
