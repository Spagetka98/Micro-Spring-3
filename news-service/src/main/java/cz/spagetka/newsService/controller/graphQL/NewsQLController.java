package cz.spagetka.newsService.controller.graphQL;

import cz.spagetka.newsService.mapper.NewsQLMapper;
import cz.spagetka.newsService.model.dto.graphQL.NewsQL;
import cz.spagetka.newsService.model.dto.rest.UserDTO;
import cz.spagetka.newsService.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class NewsQLController {
    private final NewsService newsService;
    private final NewsQLMapper newsQLMapper;

    @QueryMapping
    public NewsQL getNews(
            @Argument long newsId,
            @AuthenticationPrincipal UserDTO userDTO){
        return this.newsService.findNewsById(newsId,userDTO, newsQLMapper::toQL);
    }

    @QueryMapping
    public Page<NewsQL> getAllNews(
            @Argument int page,
            @Argument int size,
            @Argument String authorOrTitle,
            @AuthenticationPrincipal UserDTO userDTO){
        return this.newsService.findAllNews(PageRequest.of(page,size),authorOrTitle,userDTO, newsQLMapper::toQL);
    }
}
