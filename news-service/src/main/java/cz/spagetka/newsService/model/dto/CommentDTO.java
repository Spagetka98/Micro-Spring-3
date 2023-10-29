package cz.spagetka.newsService.model.dto;

public record CommentDTO(
        long id,
        String text,
        String authId,
        String createdAt,
        String updatedAt
) {
}
