package cz.spagetka.newsService.service;

import cz.spagetka.newsService.model.db.News;
import cz.spagetka.newsService.model.db.User;
import cz.spagetka.newsService.model.dto.rest.UserDTO;
import cz.spagetka.newsService.model.interfaces.UserInfo;
import cz.spagetka.newsService.model.request.NewsRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.util.function.BiFunction;

@Validated
public interface NewsService {
    void createNews(@Valid @NotNull NewsRequest newsRequest,@NotNull MultipartFile thumbnail, @NotNull UserDTO userInfo);
    void deleteNews(long id);
    void addLike(long id,@NotNull UserInfo userInfo);
    void removeLike(long id,@NotNull UserInfo userInfo);
    void addDislike(long id,@NotNull UserInfo userInfo);
    void removeDislike(long id,@NotNull UserInfo userInfo);
    News findNewsById(long id);
    <T> T findNewsById(long id, @NotNull UserInfo userInfo, @NotNull BiFunction<News, User,T> mapper);
    Page<News> findAllNews(@NotNull Pageable pageable);
    Page<News> findAllNews(@NotNull Pageable pageable, String titleOrAuthorName);
    <T> Page<T> findAllNews(@NotNull Pageable pageable, String titleOrAuthorName, @NotNull UserInfo userInfo, @NotNull BiFunction<News,User,T> mapper);
}
