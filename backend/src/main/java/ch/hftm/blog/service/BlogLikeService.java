package ch.hftm.blog.service;

import ch.hftm.blog.entity.Blog;
import ch.hftm.blog.entity.BlogLike;
import ch.hftm.blog.entity.User;
import ch.hftm.blog.repository.BlogLikeRepository;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;

@Dependent
public class BlogLikeService {
    @Inject
    BlogLikeRepository likeRepository;

    public void addLikeToBlog(Blog blog, User user) {
        BlogLike like = new BlogLike(blog, user);
        likeRepository.persist(like);
        Log.info("A like added to " + blog.getTitle() + " by user: " + user.getEmail());
    }

    public void removeLikeToBlog(BlogLike like) {
        String blogTitle = like.getBlog().getTitle();
        String userMail = like.getUser().getEmail();
        likeRepository.delete(like);
        Log.info("Like removed from " + blogTitle + " by user: " + userMail);
    }

    public BlogLike getBlogLikeByBlogAndUserID(Long blogID, Long userID) {
        return likeRepository.findByBlogAndUser(blogID, userID);
    }
}
