package cz.spagetka.newsService.service;

import cz.spagetka.newsService.model.db.Comment;
import cz.spagetka.newsService.model.dto.CommentDTO;
import cz.spagetka.newsService.model.dto.UserDTO;
import cz.spagetka.newsService.model.request.CommentRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface CommentService {
    Page<CommentDTO> getCommentsDTO(long newsId, int page, int size);
    void createComment(long newsId,@NotBlank String authorId,@NotBlank String commentText);
    void changeComment(long newsId,@NotBlank String authorId,@NotBlank String commentText);
    void deleteComment(long newsId,@NotBlank String authorId);
}
