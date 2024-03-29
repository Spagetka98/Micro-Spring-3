package cz.spagetka.newsService.controller.rest;

import cz.spagetka.newsService.mapper.NewsDTOMapper;
import cz.spagetka.newsService.model.dto.rest.NewsDTO;
import cz.spagetka.newsService.model.dto.rest.UserDTO;
import cz.spagetka.newsService.model.request.NewsRequest;
import cz.spagetka.newsService.service.NewsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
public class NewsController {
    private final NewsService newsService;
    private final NewsDTOMapper newsDTOMapper;

    @GetMapping("/{id}")
    public NewsDTO getNews(
            @PathVariable(name = "id") long newsId,
            @AuthenticationPrincipal UserDTO userDTO){
        return this.newsService.findNewsById(newsId,userDTO, newsDTOMapper::toDTO);
    }

    @GetMapping
    public Page<NewsDTO> getAllNews(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(required = false) String titleOrAuthorName,
            @AuthenticationPrincipal UserDTO userDTO){
        return this.newsService.findAllNews(PageRequest.of(page,size),titleOrAuthorName,userDTO, newsDTOMapper::toDTO);
    }

    @GetMapping(value = "/img/{id}.jpg",produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getThumbnail(@PathVariable long id) {
        return this.newsService.findNewsById(id).getThumbnail();
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_EDITOR') or hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public void createNews(@Valid @RequestPart NewsRequest newsRequest,@RequestPart MultipartFile thumbnail, @AuthenticationPrincipal UserDTO userDTO){
        this.newsService.createNews(newsRequest,thumbnail, userDTO);
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ROLE_EDITOR') or hasRole('ROLE_ADMIN')")
    public void deleteNews(@RequestParam(name = "id") long newsId){
        this.newsService.deleteNews(newsId);
    }

    @PutMapping("/{id}/add-like")
    public void addLike(@PathVariable(name = "id") long newsId, @AuthenticationPrincipal UserDTO userDTO){
        this.newsService.addLike(newsId,userDTO);
    }

    @PutMapping("/{id}/remove-like")
    public void removeLike(@PathVariable(name = "id") long newsId, @AuthenticationPrincipal UserDTO userDTO){
        this.newsService.removeLike(newsId,userDTO);
    }

    @PutMapping("/{id}/add-dislike")
    public void addDislike(@PathVariable(name = "id") long newsId, @AuthenticationPrincipal UserDTO userDTO){
        this.newsService.addDislike(newsId,userDTO);
    }

    @PutMapping("/{id}/remove-dislike")
    public void removeDislike(@PathVariable(name = "id") long newsId, @AuthenticationPrincipal UserDTO userDTO){
        this.newsService.removeDislike(newsId,userDTO);
    }
}
