package cz.spagetka.newsService.service;

import cz.spagetka.newsService.exception.CommentNotFoundException;
import cz.spagetka.newsService.exception.NewsNotFoundException;
import cz.spagetka.newsService.model.db.Comment;
import cz.spagetka.newsService.model.db.News;
import cz.spagetka.newsService.model.db.User;
import cz.spagetka.newsService.repository.CommentRepository;
import cz.spagetka.newsService.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final NewsRepository newsRepository;
    private final UserService userService;

    @Override
    public Page<Comment> getComments(long newsId, Pageable pageable) {
        return this.commentRepository.findAllByNews_IdOrderByCreatedAtDesc(newsId,pageable);
    }

    @Override
    public Comment createComment(long newsId, String authorId, String commentText) {
        User commentAuthor = this.userService.getUser(authorId);

        News commentedNews = this.newsRepository.findById(newsId)
                .orElseThrow(() -> new NewsNotFoundException(String.format("Could not find news with id: %d",newsId)));

        Comment comment = Comment.builder()
                .text(commentText)
                .build();

        commentAuthor.addComment(comment);
        commentedNews.addComment(comment);

        return this.commentRepository.save(comment);
    }

    @Override
    public Comment changeComment(long newsId, String authorId, String commentText) {
        Comment comment = this.commentRepository.findByNews_IdAndAuthor_AuthId(newsId, authorId)
                .orElseThrow(() -> new CommentNotFoundException(String.format("Could not find comment with newsId: %d and authorId: %s",newsId, authorId)));

        if(comment.getText().equals(commentText)) return comment;

        comment.setText(commentText);

        return this.commentRepository.save(comment);
    }

    @Override
    public void deleteComment(long newsId, String authorId) {
        Comment comment = this.commentRepository.findByNews_IdAndAuthor_AuthId(newsId, authorId)
                .orElseThrow(() -> new CommentNotFoundException(String.format("Could not find comment with newsId: %d and authorId: %s",newsId,authorId)));

        comment.getNews().removeComment(comment);
        comment.getAuthor().removeComment(comment);

        this.commentRepository.delete(comment);
    }
}
