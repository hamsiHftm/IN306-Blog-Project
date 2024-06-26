package ch.hftm.blog.dto.comment;

import jakarta.validation.constraints.NotBlank;

public record CommentUpdateRequestDTO1(
        @NotBlank(message = "Content must not be null nor empty")
        String content) {
}
