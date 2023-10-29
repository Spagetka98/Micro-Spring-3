package cz.spagetka.newsService.service;

import cz.spagetka.newsService.model.db.Comment;
import cz.spagetka.newsService.model.dto.UserDTO;
import cz.spagetka.newsService.model.request.CommentRequest;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
public interface CommentService {
    Page<Comment> findComments(long newsId, int page, int size);
    void createComment(long newsId,@NotNull CommentRequest commentRequest,@NotNull UserDTO userDTO);
    void changeComment(long newsId,@NotNull CommentRequest commentRequest,@NotNull UserDTO userDTO);
    void deleteComment(long newsId,@NotNull UserDTO userDTO);
}
