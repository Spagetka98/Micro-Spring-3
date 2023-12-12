package cz.spagetka.newsService.service;

import cz.spagetka.newsService.exception.NewsNotFoundException;
import cz.spagetka.newsService.mapper.NewsMapper;
import cz.spagetka.newsService.model.db.News;
import cz.spagetka.newsService.model.db.User;
import cz.spagetka.newsService.model.dto.NewsDTO;
import cz.spagetka.newsService.model.dto.UserDTO;
import cz.spagetka.newsService.model.request.NewsRequest;
import cz.spagetka.newsService.repository.NewsRepository;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {
    private final UserService userService;
    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;

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
    public News findNews(long id) {
        return this.newsRepository.findById(id)
                .orElseThrow(() -> new NewsNotFoundException(String.format("Could not find a News with id: %d",id)));
    }

    @Override
    public NewsDTO findNews(long id, UserDTO userDTO) {
        News news = this.findNews(id);
        User user = this.userService.getUser(userDTO.getUserId());

        return newsMapper.toDTO(news,user);
    }


    @Override
    public Page<News> findNews(int page, int size) {
        Pageable paging = PageRequest.of(page, size);

        return this.newsRepository.findNewsByTitleOrAuthorId(null,paging);
    }

    @Override
    public Page<NewsDTO> findNews(int page, int size, String search, UserDTO userDTO) {
        Pageable paging = PageRequest.of(page, size);
        User user = this.userService.getUser(userDTO.getUserId());

        return this.newsRepository.findNewsByTitleOrAuthorId(search,paging)
                .map(news -> newsMapper.toDTO(news,user));
    }

    @Override
    @Retryable(retryFor = {OptimisticLockException.class},
            maxAttempts = 4,
            backoff = @Backoff(delay = 1000))
    public void addLike(long id, UserDTO userDTO) {
        News news = this.newsRepository.findById(id)
                .orElseThrow(() -> new NewsNotFoundException(String.format("Could not find a News with id: %d",id)));

        User user = this.userService.getUser(userDTO.getUserId());

        news.removeDislikedUser(user);
        news.addLikedUser(user);

        this.newsRepository.save(news);
    }

    @Override
    @Retryable(retryFor = {OptimisticLockException.class},
            maxAttempts = 4,
            backoff = @Backoff(delay = 1000))
    public void removeLike(long id, UserDTO userDTO) {
        News news = this.newsRepository.findById(id)
                .orElseThrow(() -> new NewsNotFoundException(String.format("Could not find a News with id: %d",id)));

        User user = this.userService.getUser(userDTO.getUserId());

        news.removeLikedUser(user);

        this.newsRepository.save(news);
    }

    @Override
    @Retryable(retryFor = {OptimisticLockException.class},
            maxAttempts = 4,
            backoff = @Backoff(delay = 1000))
    public void addDislike(long id, UserDTO userDTO) {
        News news = this.newsRepository.findById(id)
                .orElseThrow(() -> new NewsNotFoundException(String.format("Could not find a News with id: %d",id)));

        User user = this.userService.getUser(userDTO.getUserId());

        news.removeLikedUser(user);
        news.addDislikedUser(user);

        this.newsRepository.save(news);
    }

    @Override
    @Retryable(retryFor = {OptimisticLockException.class},
            maxAttempts = 4,
            backoff = @Backoff(delay = 1000))
    public void removeDislike(long id, UserDTO userDTO) {
        News news = this.newsRepository.findById(id)
                .orElseThrow(() -> new NewsNotFoundException(String.format("Could not find a News with id: %d",id)));

        User user = this.userService.getUser(userDTO.getUserId());

        news.removeDislikedUser(user);

        this.newsRepository.save(news);
    }
}
