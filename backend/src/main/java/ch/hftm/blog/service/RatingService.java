package ch.hftm.blog.service;

import ch.hftm.blog.entity.Blog;
import ch.hftm.blog.entity.Rating;
import ch.hftm.blog.entity.User;
import ch.hftm.blog.repository.RatingRepository;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;

import java.util.List;
import java.util.OptionalDouble;

@Dependent
public class RatingService {
    @Inject
    RatingRepository ratingRepository;

    public void addRating(Rating rating) {
        ratingRepository.persist(rating);
        Log.info("A Rating of " + rating.getRating() + " added to " + rating.getBlog().getTitle() + " by user: " + rating.getUser().getEmail());
    }

    public void editRating(Rating rating) {
        ratingRepository.persist(rating);
        Log.info("A Rating changed of " + rating.getRating() + " added to " + rating.getBlog().getTitle() + " by user: " + rating.getUser().getEmail());
    }

    public void removeRating(Rating rating) {
        ratingRepository.delete(rating);
        Log.info("Rating removed from " + rating.getBlog().getTitle() + " by user: " + rating.getUser().getEmail());
    }

    public double getAverageRating(Blog blog) {
        List<Rating> ratings = ratingRepository.findByBlog(blog);
        OptionalDouble average = ratings.stream().mapToInt(Rating::getRating).average();
        return average.isPresent() ? average.getAsDouble() : 0.0;
    }

    public Rating getRatingWithBlogAndUserID(Blog blog, User user) {
        return ratingRepository.findByBlogAndUser(blog, user);
    }
}
