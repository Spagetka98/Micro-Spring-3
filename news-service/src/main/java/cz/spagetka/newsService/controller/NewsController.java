package cz.spagetka.newsService.controller;

import cz.spagetka.newsService.model.dto.UserInformation;
import cz.spagetka.newsService.model.request.NewsRequest;
import cz.spagetka.newsService.service.NewsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/news")
@RequiredArgsConstructor
public class NewsController {
    private final NewsService newsService;
    @PostMapping
    @PreAuthorize("hasAuthority('EDITOR')")
    @ResponseStatus(HttpStatus.CREATED)
    public void createNews(@Valid @RequestBody NewsRequest newsRequest, @AuthenticationPrincipal UserInformation userInformation){
        this.newsService.createNews(newsRequest,userInformation);
    }

}
