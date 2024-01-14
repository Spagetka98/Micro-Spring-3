package cz.spagetka.newsService.service;

import cz.spagetka.newsService.model.db.Comment;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

@Validated
public interface CommentService {
    Page<Comment> getComments(long newsId, Pageable pageable);
    Comment createComment(long newsId,@NotBlank String authorId,@NotBlank String commentText);
    Comment changeComment(long newsId,@NotBlank String authorId,@NotBlank String commentText);
    void deleteComment(long newsId,@NotBlank String authorId);
}
