package cz.spagetka.newsService.model.request;

import jakarta.validation.constraints.NotBlank;

public record CommentRequest(
        @NotBlank(message = "Parameter text cannot be null or empty!") String text
) {
}
