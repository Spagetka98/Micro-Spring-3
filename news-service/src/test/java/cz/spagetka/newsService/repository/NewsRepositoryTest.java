package cz.spagetka.newsService.repository;

import cz.spagetka.newsService.model.db.News;
import cz.spagetka.newsService.model.db.User;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@ActiveProfiles("test")
public class NewsRepositoryTest {
    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp(){
        User user1 = User.builder().authId("1").news(new ArrayList<>()).build();
        User user2 = User.builder().authId("2").news(new ArrayList<>()).build();

        this.userRepository.saveAll(List.of(user1,user2));

        News news1 = News.builder().title("Title_First").text("text").likedByUsers(new HashSet<>()).dislikedByUsers(new HashSet<>()).build();
        News news2 = News.builder().title("Title_Second").text("text").likedByUsers(new HashSet<>()).dislikedByUsers(new HashSet<>()).build();

        user1.addNews(news1);
        user2.addNews(news2);

        this.newsRepository.saveAll(List.of(news1,news2));
    }

    @AfterEach
    public void cleanUp(){
        this.newsRepository.deleteAll();
    }

    @Test
    public void shouldFindNewsByTitleOrAuthorId(){
        int page = 0;
        int page_size = 10;
        final String passed_news_title = "Title_Second";

        Page<News> news = this.newsRepository.findNewsByTitleOrAuthorId(passed_news_title, PageRequest.of(page,page_size));

        assertEquals(passed_news_title,news.getContent().getFirst().getTitle());
    }
}
