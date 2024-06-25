package ch.hftm.blog.repository;

import ch.hftm.blog.entity.Blog;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class BlogRepository implements PanacheRepository<Blog> {
    public List<Blog> findAllBlogsWithLimitAndOffset(int limit, int offset, String orderBy, boolean asc) {
        if (orderBy != null && !orderBy.isEmpty()) {
            Sort sort = Sort.descending(orderBy);
            if (asc) {
                sort = Sort.ascending(orderBy);
            }
            return findAll(sort).page(offset, limit).list();
        } else {
            return findAll().page(offset, limit).list();
        }
    }

    public List<Blog> findAllBlogsWithTitleAndLimitAndOffset(String searchTitle, int limit, int offset, String orderBy, boolean asc) {
        String query = "title like ?1";
        if (orderBy != null && !orderBy.isEmpty()) {
            Sort sort = Sort.descending(orderBy);
            if (asc) {
                sort = Sort.ascending(orderBy);
            }
            return find(query, sort, "%" + searchTitle + "%").page(offset, limit).list();
        } else {
            return find(query, "%" + searchTitle + "%").page(offset, limit).list();
        }
    }

    public List<Blog> findFavoriteBlogsByUserId(Long userId, String searchTitle, int limit, int offset, String orderBy, boolean asc) {
        Sort sort = orderBy != null && !orderBy.isEmpty() ?
                (asc ? Sort.ascending(orderBy) : Sort.descending(orderBy)) : null;

        String baseQuery = "select b from Blog b join b.likes bl where bl.user.id = ?1";
        if (searchTitle != null && !searchTitle.isEmpty()) {
            baseQuery += " and b.title like ?2";
            if (sort != null) {
                return find(baseQuery, sort, userId, "%" + searchTitle + "%").page(offset, limit).list();
            } else {
                return find(baseQuery, userId, "%" + searchTitle + "%").page(offset, limit).list();
            }
        } else {
            if (sort != null) {
                return find(baseQuery, sort, userId).page(offset, limit).list();
            } else {
                return find(baseQuery, userId).page(offset, limit).list();
            }
        }
    }

    public long countFavoriteBlogsByUserId(Long userId, String searchTitle) {
        String baseQuery = "select count(b) from Blog b join b.likes bl where bl.user.id = ?1";
        if (searchTitle != null && !searchTitle.isEmpty()) {
            baseQuery += " and b.title like ?2";
            return count(baseQuery, userId, "%" + searchTitle + "%");
        } else {
            return count(baseQuery, userId);
        }
    }

}
