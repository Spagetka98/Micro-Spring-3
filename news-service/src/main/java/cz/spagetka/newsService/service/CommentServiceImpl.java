package cz.spagetka.newsService.service;

import cz.spagetka.newsService.exception.CommentNotFoundException;
import cz.spagetka.newsService.exception.NewsNotFoundException;
import cz.spagetka.newsService.exception.UserNotFoundException;
import cz.spagetka.newsService.model.db.Comment;
import cz.spagetka.newsService.model.db.News;
import cz.spagetka.newsService.model.db.User;
import cz.spagetka.newsService.model.dto.UserDTO;
import cz.spagetka.newsService.model.request.CommentRequest;
import cz.spagetka.newsService.repository.CommentRepository;
import cz.spagetka.newsService.repository.NewsRepository;
import cz.spagetka.newsService.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final NewsRepository newsRepository;
    private final UserRepository userRepository;

    @Override
    public Page<Comment> findComments(long newsId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        return this.commentRepository.findAllByNews_IdOrderByCreatedAtDesc(newsId,pageable);
    }

    @Override
    public void createComment(long newsId, CommentRequest commentRequest, UserDTO userDTO) {
        User commentAuthor = this.userRepository.findByAuthId(userDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException(String.format("Could not find user with authId: %s",userDTO.getUserId())));

        News commentedNews = this.newsRepository.findById(newsId)
                .orElseThrow(() -> new NewsNotFoundException(String.format("Could not find news with id: %d",newsId)));

        Comment comment = Comment.builder()
                .text(commentRequest.text())
                .build();

        commentAuthor.addComment(comment);
        commentedNews.addComment(comment);

        this.commentRepository.save(comment);
    }

    @Override
    public void changeComment(long newsId, CommentRequest commentRequest, UserDTO userDTO) {
        Comment comment = this.commentRepository.findByNews_IdAndAuthor_AuthId(newsId, userDTO.getUserId())
                .orElseThrow(() -> new CommentNotFoundException(String.format("Could not find comment with newsId: %d and authorId: %s",newsId,userDTO.getUserId())));

        if(comment.getText().equals(commentRequest.text())) return;

        comment.setText(commentRequest.text());

        this.commentRepository.save(comment);
    }

    @Override
    public void deleteComment(long newsId, UserDTO userDTO) {
        Comment comment = this.commentRepository.findByNews_IdAndAuthor_AuthId(newsId, userDTO.getUserId())
                .orElseThrow(() -> new CommentNotFoundException(String.format("Could not find comment with newsId: %d and authorId: %s",newsId,userDTO.getUserId())));

        comment.getNews().removeComment(comment);
        comment.getAuthor().removeComment(comment);

        this.commentRepository.delete(comment);
    }
}
