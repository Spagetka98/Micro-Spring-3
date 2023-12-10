package cz.spagetka.newsService.controller;

import cz.spagetka.newsService.model.dto.CommentDTO;
import cz.spagetka.newsService.model.dto.UserDTO;
import cz.spagetka.newsService.model.request.CommentRequest;
import cz.spagetka.newsService.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/{id}/comment")
    public Page<CommentDTO> getComments(
            @PathVariable(name = "id") long newsId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        return this.commentService.getCommentsDTO(newsId,page,size);
    }

    @PostMapping("/{id}/comment")
    @ResponseStatus(HttpStatus.CREATED)
    public void createComment(
            @PathVariable(name = "id") long newsId,
            @Valid @RequestBody CommentRequest commentRequest,
            @AuthenticationPrincipal UserDTO userDTO
    ){
        this.commentService.createComment(newsId,commentRequest,userDTO);
    }

    @PutMapping ("/{id}/comment")
    public void changeComment(
            @PathVariable(name = "id") long newsId,
            @Valid @RequestBody CommentRequest commentRequest,
            @AuthenticationPrincipal UserDTO userDTO
    ){
        this.commentService.changeComment(newsId,commentRequest,userDTO);
    }

    @DeleteMapping ("/{id}/comment")
    public void changeComment(
            @PathVariable(name = "id") long newsId,
            @AuthenticationPrincipal UserDTO userDTO
    ){
        this.commentService.deleteComment(newsId,userDTO);
    }
}
