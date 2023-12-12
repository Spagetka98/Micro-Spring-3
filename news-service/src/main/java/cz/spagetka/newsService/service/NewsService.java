package cz.spagetka.newsService.service;

import cz.spagetka.newsService.model.db.News;
import cz.spagetka.newsService.model.dto.NewsDTO;
import cz.spagetka.newsService.model.dto.UserDTO;
import cz.spagetka.newsService.model.request.NewsRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

@Validated
public interface NewsService {
    void createNews(@Valid @NotNull NewsRequest newsRequest,@NotNull MultipartFile thumbnail, @NotNull UserDTO userInfo);
    void deleteNews(long id);
    void addLike(long id,@NotNull UserDTO userDTO);
    void removeLike(long id,@NotNull UserDTO userDTO);
    void addDislike(long id,@NotNull UserDTO userDTO);
    void removeDislike(long id,@NotNull UserDTO userDTO);
    News findNews(long id);
    NewsDTO findNews(long id, @NotNull UserDTO userDTO);
    Page<News> findNews(int page, int size);
    Page<NewsDTO> findNews(int page, int size, String search, @NotNull UserDTO userDTO);
}
