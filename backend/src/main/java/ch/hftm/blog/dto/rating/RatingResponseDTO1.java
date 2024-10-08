package ch.hftm.blog.dto.rating;

import ch.hftm.blog.dto.blog.BlogResponseDTO2;
import ch.hftm.blog.entity.Blog;

public record RatingResponseDTO1(BlogResponseDTO2 blog, double averageRating) {
    public RatingResponseDTO1(Blog blog, double averageRating) {
        this(new BlogResponseDTO2(blog), averageRating);
    }
}
