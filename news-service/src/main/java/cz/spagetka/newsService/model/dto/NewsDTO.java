package cz.spagetka.newsService.model.dto;

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
