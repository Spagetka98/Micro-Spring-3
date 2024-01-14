package cz.spagetka.newsService.controller.rest;

import cz.spagetka.newsService.mapper.CommentDTOMapper;
import cz.spagetka.newsService.model.dto.rest.CommentDTO;
import cz.spagetka.newsService.model.dto.rest.UserDTO;
import cz.spagetka.newsService.model.request.CommentRequest;
import cz.spagetka.newsService.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/news")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final CommentDTOMapper commentDTOMapper;

    @GetMapping("/{id}/comment")
    public Page<CommentDTO> getComments(
            @PathVariable(name = "id") long newsId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        return this.commentService.getComments(newsId, PageRequest.of(page,size))
                .map(commentDTOMapper::toDTO);
    }

    @PostMapping("/{id}/comment")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDTO createComment(
            @PathVariable(name = "id") long newsId,
            @Valid @RequestBody CommentRequest commentRequest,
            @AuthenticationPrincipal UserDTO userDTO
    ){
        return this.commentDTOMapper.toDTO(
                this.commentService.createComment(newsId,userDTO.getUserId(),commentRequest.text())
        );
    }

    @PutMapping ("/{id}/comment")
    public CommentDTO changeComment(
            @PathVariable(name = "id") long newsId,
            @Valid @RequestBody CommentRequest commentRequest,
            @AuthenticationPrincipal UserDTO userDTO
    ){
        return this.commentDTOMapper.toDTO(
                this.commentService.changeComment(newsId,userDTO.getUserId(),commentRequest.text())
        );
    }

    @DeleteMapping ("/{id}/comment")
    public String deleteComment(
            @PathVariable(name = "id") long newsId,
            @AuthenticationPrincipal UserDTO userDTO
    ){
        this.commentService.deleteComment(newsId,userDTO.getUserId());

        return "Comment was deleted successfully!";
    }
}
