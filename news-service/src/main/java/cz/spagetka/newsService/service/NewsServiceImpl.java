package cz.spagetka.newsService.service;

import cz.spagetka.newsService.model.db.News;
import cz.spagetka.newsService.model.db.User;
import cz.spagetka.newsService.model.dto.UserInformation;
import cz.spagetka.newsService.model.request.NewsRequest;
import cz.spagetka.newsService.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {
    private final UserService userService;
    private final NewsRepository newsRepository;

    @Override
    public void createNews(NewsRequest newsRequest, UserInformation userInfo) {
        User author = this.userService.getUser(userInfo.getUserId());

        try {
            News news = News.builder()
                    .title(newsRequest.title())
                    .thumbnail(newsRequest.thumbnail().getBytes())
                    .text(newsRequest.text())
                    .creator(author)
                    .build();

            this.newsRepository.save(news);
        }catch (IOException e){
            throw new IllegalArgumentException("Error occurred while saving thumbnail image for news!",e);
        }catch (Exception e){
            throw new IllegalStateException("Error occurred while saving news!",e);
        }
    }
}
