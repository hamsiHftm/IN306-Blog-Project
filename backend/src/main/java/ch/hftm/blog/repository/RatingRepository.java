package ch.hftm.blog.repository;

import ch.hftm.blog.entity.Blog;
import ch.hftm.blog.entity.Rating;
import ch.hftm.blog.entity.User;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class RatingRepository implements PanacheRepository<Rating> {
    public List<Rating> findByBlog(Blog blog) {
        return find("blog", blog).list();
    }

    public Rating findByBlogAndUser(Blog blog, User user) {
        return find("blog = ?1 and user = ?2", blog, user).firstResult();
    }
}
