package ch.hftm.blog.dto.blog;

import java.util.List;

public record BlogListResponseDTO(List<BlogResponseDTO1> blogs, int offset, int limit, String searchTitle, long totalBlogs) {
}

