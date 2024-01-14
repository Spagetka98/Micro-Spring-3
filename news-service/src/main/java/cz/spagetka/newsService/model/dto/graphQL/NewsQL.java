package cz.spagetka.newsService.model.dto.graphQL;

import java.util.List;

public record NewsQL(
        long newsId,
        String title,
        String text,
        String creationDate,
        String updateDate,
        long totalLikes,
        long totalDislikes,
        long totalComments,
        boolean isLikedByUser,
        boolean isDislikedByUser,
        String userId,
        List<CommentQL> comments
) {
}
