package cz.spagetka.newsService.model.dto.rest;

public record NewsDTO(
        long newsId,
        String title,
        String text,
        String userId,
        String creationDate,
        String updateDate,
        long totalLikes,
        long totalDislikes,
        long totalComments,
        boolean isLikedByUser,
        boolean isDislikedByUser
) {
}
