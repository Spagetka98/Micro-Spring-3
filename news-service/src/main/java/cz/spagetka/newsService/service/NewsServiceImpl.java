package cz.spagetka.newsService.service;

import cz.spagetka.newsService.exception.NewsNotFoundException;
import cz.spagetka.newsService.model.db.News;
import cz.spagetka.newsService.model.db.User;
import cz.spagetka.newsService.model.dto.UserDTO;
import cz.spagetka.newsService.model.request.NewsRequest;
import cz.spagetka.newsService.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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
                    .title(newsRequest.title())
                    .thumbnail(thumbnail.getBytes())
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

    @Override
    public News findNews(long id) {
        return this.newsRepository.findById(id)
                .orElseThrow(() -> new NewsNotFoundException(String.format("Could not find a News with id: %d",id)));
    }

    @Override
    public Page<News> findNews(int page, int size) {
        Pageable paging = PageRequest.of(page, size);

        return this.newsRepository.findAllByOrderByCreatedAtDesc(paging);
    }

}
