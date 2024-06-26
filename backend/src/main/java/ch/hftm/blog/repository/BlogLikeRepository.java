package ch.hftm.blog.repository;

import ch.hftm.blog.entity.BlogLike;
import ch.hftm.blog.entity.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BlogLikeRepository implements PanacheRepository<BlogLike> {
    public BlogLike findByBlogAndUser(Long blogID, Long userID) {
        return find("blog.id = ?1 and user.id = ?2", blogID, userID).firstResult();
    }
}
