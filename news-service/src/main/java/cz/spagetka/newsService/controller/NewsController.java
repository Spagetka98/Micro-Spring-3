package cz.spagetka.newsService.controller;

import cz.spagetka.newsService.model.db.News;
import cz.spagetka.newsService.model.dto.UserInformation;
import cz.spagetka.newsService.model.request.NewsRequest;
import cz.spagetka.newsService.service.NewsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {
    private final NewsService newsService;

    @GetMapping
    public News getNews(){
        return null;
    }

    @PostMapping(consumes = { "multipart/form-data" })
    @PreAuthorize("hasRole('ROLE_EDITOR') or hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public void createNews(@Valid @RequestPart NewsRequest newsRequest,@RequestPart MultipartFile thumbnail, @AuthenticationPrincipal UserInformation userInformation){
        this.newsService.createNews(newsRequest,thumbnail,userInformation);
    }

}
