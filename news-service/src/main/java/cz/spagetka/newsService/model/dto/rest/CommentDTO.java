package cz.spagetka.newsService.model.dto.rest;

public record CommentDTO(
        long id,
        String text,
        String authId,
        String createdAt,
        String updatedAt
) {
}
