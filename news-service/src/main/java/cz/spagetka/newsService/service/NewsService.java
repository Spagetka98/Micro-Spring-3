package cz.spagetka.newsService.service;

import cz.spagetka.newsService.model.dto.UserInformation;
import cz.spagetka.newsService.model.request.NewsRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Validated
public interface NewsService {
    void createNews(@Valid @NotNull NewsRequest newsRequest,@NotNull MultipartFile thumbnail, @NotNull UserInformation userInfo);
}
