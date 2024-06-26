package ch.hftm.blog.repository;

import ch.hftm.blog.entity.CommentLike;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CommentLikeRepository implements PanacheRepository<CommentLike> {
    public CommentLike findByCommentAndUser(long commentID, long userID) {
        return find("comment.id = ?1 and user.id = ?2", commentID, userID).firstResult();
    }
}
