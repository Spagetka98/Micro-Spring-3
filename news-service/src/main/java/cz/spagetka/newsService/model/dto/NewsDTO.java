package cz.spagetka.newsService.model.dto;

public record NewsDTO(
        long newsId,
        String title,
        String uri,
        String text,
        String userId,
        String creationDate,
        String updateDate,
        long totalLikes,
        long totalDislikes,
        boolean isLikedByUser,
        boolean isDislikedByUser
) {
}
