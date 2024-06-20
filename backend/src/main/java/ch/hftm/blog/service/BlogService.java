package ch.hftm.blog.service;

import java.util.ArrayList;
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

    public List<Blog> getAllBlogs(String searchTitle, int limit, int offset) {
        List<Blog> blogs;
        if (searchTitle == null || searchTitle.isEmpty()) {
            blogs = blogRepository.find("order by createdAt desc").page(offset, limit).list();
        } else {
            blogs = blogRepository.find("title like ?1 order by createdAt desc", "%" + searchTitle + "%").page(offset, limit).list();
        }
        Log.info("Returning " + blogs.size() + " blogs");
        return blogs;
    }

    public Blog getBlogById(Long id, boolean includeDetails) {
        Blog blog = blogRepository.findById(id);
        if (includeDetails && blog != null) {
            blog.setNumberOfLikes(blog.getLikes().size());
            blog.getComments().size();
        }
        return blog;
    }

    public List<Blog> getFavoriteBlogsByUserId(Long userId, String searchTitle, int limit, int offset) {
        List<Blog> blogs;
        if (searchTitle == null || searchTitle.isEmpty()) {
            blogs = blogRepository.find("user.id = ?1 and isFavourite = true order by createdAt desc", userId).page(offset, limit).list();
        } else {
            blogs = blogRepository.find("user.id = ?1 and isFavourite = true and title like ?2 order by createdAt desc", userId, "%" + searchTitle + "%").page(offset, limit).list();
        }
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
    public Blog updateBlog(Long id, Blog updatedBlog) {
        Blog blog = blogRepository.findById(id);
        if (blog != null) {
            blog.setTitle(updatedBlog.getTitle());
            blog.setContent(updatedBlog.getContent());
            blog.setUpdatedAt(updatedBlog.getUpdatedAt());
            blogRepository.persist(blog);
        }
        return blog;
    }

    @Transactional
    public void deleteBlog(Long id) {
        Blog blog = blogRepository.findById(id);
        if (blog != null) {
            blogRepository.delete(blog);
            Log.info("Deleted blog with id " + id);
        }
    }
}
