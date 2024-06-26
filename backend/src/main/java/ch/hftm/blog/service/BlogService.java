package ch.hftm.blog.service;

import java.util.List;

import ch.hftm.blog.entity.Blog;
import ch.hftm.blog.repository.BlogRepository;
import io.quarkus.logging.Log;

import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@Dependent
public class BlogService {
    @Inject
    BlogRepository blogRepository;

    public List<Blog> getAllBlogs(String searchTitle, String order_by, int limit, int offset, boolean asc) {
        List<Blog> blogs;
        if (searchTitle == null || searchTitle.isEmpty()) {
            blogs = blogRepository.findAllBlogsWithLimitAndOffset(limit, offset, order_by, asc);
        } else {
            blogs = blogRepository.findAllBlogsWithTitleAndLimitAndOffset(searchTitle, limit, offset, order_by, asc);
        }
        Log.info("Returning " + blogs.size() + " blogs");
        return blogs;
    }

    public Blog getBlogById(Long id, boolean includeDetails) {
        Blog blog = blogRepository.findById(id);
        if (blog != null) {
            if (includeDetails) {
                int commentSize = blog.getComments().size();
                Log.info("Blog found with " + commentSize + " comments and " + blog.getNumberOfLikes() + ".");
            }
        } else {
            Log.warn("No blog found with id " + id);
        }
        return blog;
    }

    public Blog findBlogById(Long id) {
        return blogRepository.findById(id);
    }

    public List<Blog> getFavoriteBlogsByUserId(Long userId, String searchTitle, String orderBy, int limit, int offset, boolean asc) {
        List<Blog> blogs = blogRepository.findFavoriteBlogsByUserId(userId, searchTitle, limit, offset, orderBy, asc);
        Log.info("Returning " + blogs.size() + " favorite blogs for user " + userId);
        return blogs;
    }

    @Transactional
    public Blog addBlog(Blog blog) {
        Log.info("Adding blog " + blog.getTitle());
        blogRepository.persist(blog);
        return blog;
    }

    @Transactional
    public void updateBlog(Blog blog, String title, String content) {
        if (title == null || title.isEmpty()) {
            blog.setTitle(title);
        }
        if (content == null || content.isEmpty()) {
            blog.setContent(content);
        }
        blogRepository.persist(blog);
        Log.info("Updated blog " + blog.getId());
    }

    @Transactional
    public void deleteBlog(Blog blog) {
        long id = blog.getId();
        blogRepository.delete(blog);
        Log.info("Deleted blog with id " + id);
    }

    @Transactional
    public long countAllBlogs(String searchTitle, Long userId) {
        if (userId == null) {
            if (searchTitle == null || searchTitle.isEmpty()) {
                return blogRepository.count();
            } else {
                return blogRepository.count("title like ?1", "%" + searchTitle + "%");
            }
        } else {
            return blogRepository.countFavoriteBlogsByUserId(userId, searchTitle);
        }

    }
}
