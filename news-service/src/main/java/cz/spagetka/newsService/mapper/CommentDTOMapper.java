package cz.spagetka.newsService.mapper;

import cz.spagetka.newsService.model.db.Comment;
import cz.spagetka.newsService.model.dto.rest.CommentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentDTOMapper {
    @Mapping(target = "authId",source = "author.authId")
    CommentDTO toDTO(Comment comment);
}
