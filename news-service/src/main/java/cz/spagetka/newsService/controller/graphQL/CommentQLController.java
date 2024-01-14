package cz.spagetka.newsService.controller.graphQL;

import cz.spagetka.newsService.mapper.CommentQLMapper;
import cz.spagetka.newsService.model.dto.graphQL.CommentQL;
import cz.spagetka.newsService.model.dto.rest.UserDTO;
import cz.spagetka.newsService.model.request.CommentRequest;
import cz.spagetka.newsService.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class CommentQLController {
    private final CommentService commentService;
    private final CommentQLMapper commentQLMapper;

    @QueryMapping
    public Page<CommentQL> getComments(@Argument long newsId, @Argument int page, @Argument int size){
        return this.commentService.getComments(newsId, PageRequest.of(page,size))
                .map(commentQLMapper::toQL);
    }

    @MutationMapping
    public CommentQL createComment(
            @Argument long newsId,
            @Valid @Argument CommentRequest commentRequest,
            @AuthenticationPrincipal UserDTO userDTO
    ){
        return this.commentQLMapper.toQL(
                this.commentService.createComment(newsId,userDTO.getUserId(),commentRequest.text())
        );
    }

    @MutationMapping
    public CommentQL changeComment(
            @Argument long newsId,
            @Valid @Argument CommentRequest commentRequest,
            @AuthenticationPrincipal UserDTO userDTO
    ){
        return this.commentQLMapper.toQL(
                this.commentService.changeComment(newsId,userDTO.getUserId(),commentRequest.text())
        );
    }

    @MutationMapping
    public String deleteComment(
            @Argument long newsId,
            @AuthenticationPrincipal UserDTO userDTO
    ){
        this.commentService.deleteComment(newsId,userDTO.getUserId());

        return "Comment was deleted successfully!";
    }
}
