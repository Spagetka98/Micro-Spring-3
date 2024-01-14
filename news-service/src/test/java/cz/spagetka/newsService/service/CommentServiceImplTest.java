package cz.spagetka.newsService.service;

import cz.spagetka.newsService.exception.CommentNotFoundException;
import cz.spagetka.newsService.exception.NewsNotFoundException;
import cz.spagetka.newsService.exception.UserNotFoundException;
import cz.spagetka.newsService.mapper.CommentDTOMapper;
import cz.spagetka.newsService.model.db.Comment;
import cz.spagetka.newsService.model.db.News;
import cz.spagetka.newsService.model.db.User;
import cz.spagetka.newsService.repository.CommentRepository;
import cz.spagetka.newsService.repository.NewsRepository;
import cz.spagetka.newsService.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
class CommentServiceImplTest {
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private NewsRepository newsRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CommentDTOMapper commentDTOMapper;
    @InjectMocks
    private CommentServiceImpl commentService;

    @Test
    public void shouldCreateComment(){
        int passed_news_id = 1;
        final String passed_author_id = "1ABC1";
        final String passed_comment_text = "Test Text!";

        User comment_author = User.builder().authId(passed_author_id).comments(new ArrayList<>()).build();
        News comment_news = News.builder().comments(new ArrayList<>()).build();

        ArgumentCaptor<Comment> commentCaptor = ArgumentCaptor.forClass(Comment.class);

        when(userRepository.findByAuthId(eq(passed_author_id)))
                .thenReturn(Optional.ofNullable(comment_author));
        when(newsRepository.findById(any()))
                .thenReturn(Optional.ofNullable(comment_news));

        this.commentService.createComment(passed_news_id,passed_author_id,passed_comment_text);

        verify(commentRepository,times(1)).save(commentCaptor.capture());

        assertTrue(comment_author.getComments().contains(commentCaptor.getValue()));
        assertTrue(comment_news.getComments().contains(commentCaptor.getValue()));
    }

    @Test
    public void shouldThrowUserNotFoundExceptionWhenCreateComment(){
        int passed_news_id = 1;
        final String passed_author_id = "1ABC1";
        final String passed_comment_text = null;

        assertThrows(UserNotFoundException.class,
                () -> this.commentService.createComment(passed_news_id,passed_author_id,passed_comment_text)
        );
    }

    @Test
    public void shouldThrowNewsNotFoundExceptionWhenCreateComment(){
        int passed_news_id = 1;
        final String passed_author_id = "1ABC1";
        final String passed_comment_text = "Test Text!";

        User comment_author = User.builder().authId(passed_author_id).comments(new ArrayList<>()).build();

        when(userRepository.findByAuthId(eq(passed_author_id)))
                .thenReturn(Optional.ofNullable(comment_author));
        when(newsRepository.findById(any()))
                .thenReturn(Optional.empty());

        assertThrows(NewsNotFoundException.class,
                () -> this.commentService.createComment(passed_news_id,passed_author_id,passed_comment_text)
        );
    }

    @Test
    public void shouldChangeComment(){
        ArgumentCaptor<Comment> commentArgumentCaptor = ArgumentCaptor.forClass(Comment.class);
        int passed_news_id = 1;
        final String passed_author_id = "1ABC1";
        final String passed_old_text = "Test OLD Text!";
        final String passed_new_text = "Test NEW Text!";
        final Comment founded_comment = Comment.builder().text(passed_old_text).build();

        when(commentRepository.findByNews_IdAndAuthor_AuthId(passed_news_id,passed_author_id))
                .thenReturn(Optional.ofNullable(founded_comment));

        this.commentService.changeComment(passed_news_id,passed_author_id,passed_new_text);

        verify(commentRepository,times(1)).save(commentArgumentCaptor.capture());

        assertEquals(passed_new_text,commentArgumentCaptor.getValue().getText());
    }

    @Test
    public void shouldThrowCommentNotFoundExceptionChangeComment(){
        int passed_news_id = 1;
        final String passed_author_id = "1ABC1";
        final String passed_text = "Test OLD Text!";

        when(commentRepository.findByNews_IdAndAuthor_AuthId(passed_news_id,passed_author_id))
                .thenReturn(Optional.empty());

        assertThrows(CommentNotFoundException.class,
                () -> this.commentService.changeComment(1,passed_author_id,passed_text)
        );
    }

    @Test
    public void shouldDeleteComment(){
        ArgumentCaptor<Comment> commentArgumentCaptor = ArgumentCaptor.forClass(Comment.class);
        int passed_news_id = 1;
        final String passed_author_id = "1ABC1";
        User comment_author = User.builder().comments(new ArrayList<>()).build();
        News commented_news = News.builder().comments(new ArrayList<>()).build();
        Comment founded_comment = Comment.builder().build();

        comment_author.addComment(founded_comment);
        commented_news.addComment(founded_comment);

        when(commentRepository.findByNews_IdAndAuthor_AuthId(passed_news_id, passed_author_id))
                .thenReturn(Optional.of(founded_comment));

        this.commentService.deleteComment(passed_news_id,passed_author_id);

        verify(commentRepository,times(1)).delete(commentArgumentCaptor.capture());
        assertFalse(comment_author.getComments().contains(founded_comment));
        assertFalse(commented_news.getComments().contains(founded_comment));
    }

    @Test
    public void shouldThrowCommentNotFoundExceptionDeleteComment(){
        int passed_news_id = 1;
        final String passed_author_id = "1ABC1";

        when(commentRepository.findByNews_IdAndAuthor_AuthId(passed_news_id, passed_author_id))
                .thenReturn(Optional.empty());

        assertThrows(CommentNotFoundException.class,
                () -> this.commentService.deleteComment(1,passed_author_id)
        );
    }

}