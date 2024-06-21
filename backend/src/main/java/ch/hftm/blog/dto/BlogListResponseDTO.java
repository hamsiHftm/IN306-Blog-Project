package ch.hftm.blog.dto;

import java.util.List;

public record BlogListResponseDTO(List<BlogResponseDTO> blogs, int offset, int limit) {
}

