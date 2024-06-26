package ch.hftm.blog.dto.blog;

import jakarta.validation.constraints.Size;

public record BlogUpdateRequestDTO1(
        @Size(min=5, message = "Title must contain at least 5 characters")
        String title,
        String content) {
}
