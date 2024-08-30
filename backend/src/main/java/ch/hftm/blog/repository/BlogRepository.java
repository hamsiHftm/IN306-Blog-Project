package ch.hftm.blog.repository;

import ch.hftm.blog.entity.Blog;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class BlogRepository implements PanacheRepository<Blog> {

    // Method to find all blogs by user with limit, offset, and sorting
    public List<Blog> findAllBlogsByUserWithLimitAndOffset(Integer userId, int limit, int offset, String orderBy, boolean asc) {
        String query = "select b from Blog b where b.user.id = ?1";
        Object[] params = new Object[]{userId};

        if (userId == null) {
            query = "select b from Blog b"; // No filtering
            params = new Object[]{};
        }

        Sort sort = (orderBy != null && !orderBy.isEmpty())
                ? (asc ? Sort.ascending(orderBy) : Sort.descending(orderBy))
                : null;

        if (sort != null) {
            return find(query, sort, params).page(offset, limit).list();
        } else {
            return find(query, params).page(offset, limit).list();
        }
    }

    // Method to find all blogs by user and title with limit, offset, and sorting
    public List<Blog> findAllBlogsByUserAndTitleWithLimitAndOffset(String searchTitle, Integer userId, int limit, int offset, String orderBy, boolean asc) {
        String query = "select b from Blog b where b.title like ?2";
        Object[] params;

        if (userId != null) {
            query += " and b.user.id = ?1";
            params = new Object[]{userId, "%" + searchTitle + "%"};
        } else {
            params = new Object[]{"%" + searchTitle + "%"};
        }

        Sort sort = (orderBy != null && !orderBy.isEmpty())
                ? (asc ? Sort.ascending(orderBy) : Sort.descending(orderBy))
                : null;

        if (sort != null) {
            return find(query, sort, params).page(offset, limit).list();
        } else {
            return find(query, params).page(offset, limit).list();
        }
    }

    // Method to find favorite blogs by user with optional title search, limit, offset, and sorting
    public List<Blog> findFavoriteBlogsByUserId(Long userId, String searchTitle, int limit, int offset, String orderBy, boolean asc) {
        String baseQuery = "select b from Blog b join b.likes l where l.user.id = ?1";
        Object[] params;

        if (searchTitle != null && !searchTitle.isEmpty()) {
            baseQuery += " and b.title like ?2";
            params = new Object[]{userId, "%" + searchTitle + "%"};
        } else {
            params = new Object[]{userId};
        }

        Sort sort = (orderBy != null && !orderBy.isEmpty())
                ? (asc ? Sort.ascending(orderBy) : Sort.descending(orderBy))
                : null;

        if (sort != null) {
            return find(baseQuery, sort, params).page(offset, limit).list();
        } else {
            return find(baseQuery, params).page(offset, limit).list();
        }
    }

    // Method to count favorite blogs by user with optional title search
    public long countFavoriteBlogsByUserId(Long userId, String searchTitle) {
        String baseQuery = "select count(b) from Blog b join b.likes l where l.user.id = ?1";
        Object[] params;

        if (searchTitle != null && !searchTitle.isEmpty()) {
            baseQuery += " and b.title like ?2";
            params = new Object[]{userId, "%" + searchTitle + "%"};
        } else {
            params = new Object[]{userId};
        }

        return count(baseQuery, params);
    }
}