package cz.spagetka.newsService.model.dto.graphQL;

public record CommentQL(
        long id,
        String text,
        String authId,
        String createdAt,
        String updatedAt
) {
}
