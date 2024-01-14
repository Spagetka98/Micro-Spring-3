package cz.spagetka.newsService.service;

import cz.spagetka.newsService.exception.NewsNotFoundException;
import cz.spagetka.newsService.model.db.News;
import cz.spagetka.newsService.model.db.User;
import cz.spagetka.newsService.model.dto.rest.UserDTO;
import cz.spagetka.newsService.model.interfaces.UserInfo;
import cz.spagetka.newsService.model.request.NewsRequest;
import cz.spagetka.newsService.repository.NewsRepository;
import jakarta.persistence.OptimisticLockException;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {
    private final UserService userService;
    private final NewsRepository newsRepository;

    @Override
    public void createNews(NewsRequest newsRequest,MultipartFile thumbnail, UserDTO userInfo) {
        User author = this.userService.getUser(userInfo.getUserId());

        try {
            News news = News.builder()
                    .title(newsRequest.title().trim())
                    .thumbnail(thumbnail.getBytes())
                    .text(newsRequest.text().trim())
                    .creator(author)
                    .build();

            this.newsRepository.save(news);
        }catch (IOException e){
            throw new IllegalArgumentException("Error occurred while saving thumbnail image for news!",e);
        }catch (Exception e){
            throw new IllegalStateException("Error occurred while saving news!",e);
        }
    }

    @Override
    public void deleteNews(long id) {
        News news = this.newsRepository.findById(id)
                .orElseThrow(() -> new NewsNotFoundException(String.format("Could not find a News with id: %d",id)));

        news.getCreator().removeNews(news);

        this.newsRepository.delete(news);
    }

    @Override
    public News findNewsById(long id) {
        return this.newsRepository.findById(id)
                .orElseThrow(() -> new NewsNotFoundException(String.format("Could not find a News with id: %d",id)));
    }

    @Override
    public <T> T findNewsById(long id, UserInfo userInfo, BiFunction<News,User,T> mapper) {
        User user = this.userService.getUser(userInfo.getUserId());

        return mapper.apply(this.findNewsById(id), user);
    }

    @Override
    public Page<News> findAllNews(Pageable pageable) {
        return this.newsRepository.findNewsByTitleOrAuthorId(null,pageable);
    }

    @Override
    public Page<News> findAllNews(Pageable pageable, String titleOrAuthorName) {
        return this.newsRepository.findNewsByTitleOrAuthorId(titleOrAuthorName,pageable);
    }

    @Override
    public <T> Page<T> findAllNews(Pageable pageable, String titleOrAuthorName, UserInfo userInfo, BiFunction<News,User,T> mapper) {
        User user = this.userService.getUser(userInfo.getUserId());

        return this.findAllNews(pageable, titleOrAuthorName)
                .map(news -> mapper.apply(news,user));
    }

    @Override
    @Retryable(retryFor = {OptimisticLockException.class},
            maxAttempts = 4,
            backoff = @Backoff(delay = 1000))
    public void addLike(long id, UserInfo userInfo) {
        this.userInteraction(id,userInfo,(news,user) -> {
            news.removeDislikedUser(user);
            news.addLikedUser(user);
        });
    }

    @Override
    @Retryable(retryFor = {OptimisticLockException.class},
            maxAttempts = 4,
            backoff = @Backoff(delay = 1000))
    public void removeLike(long id, UserInfo userInfo) {
        this.userInteraction(id,userInfo, News::removeLikedUser);
    }

    @Override
    @Retryable(retryFor = {OptimisticLockException.class},
            maxAttempts = 4,
            backoff = @Backoff(delay = 1000))
    public void addDislike(long id, UserInfo userInfo) {
        this.userInteraction(id,userInfo,(news,user) -> {
            news.removeLikedUser(user);
            news.addDislikedUser(user);
        });
    }

    @Override
    @Retryable(retryFor = {OptimisticLockException.class},
            maxAttempts = 4,
            backoff = @Backoff(delay = 1000))
    public void removeDislike(long id, UserInfo userInfo) {
        this.userInteraction(id,userInfo, News::removeDislikedUser);
    }

    private void userInteraction(long id, @NotNull UserInfo userInfo, @NotNull BiConsumer<News,User> action){
        News news = this.newsRepository.findById(id)
                .orElseThrow(() -> new NewsNotFoundException(String.format("Could not find a News with id: %d",id)));

        User user = this.userService.getUser(userInfo.getUserId());

        action.accept(news,user);

        this.newsRepository.save(news);
    }
}
