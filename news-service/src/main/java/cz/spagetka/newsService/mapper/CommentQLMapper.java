package cz.spagetka.newsService.mapper;

import cz.spagetka.newsService.model.db.Comment;
import cz.spagetka.newsService.model.dto.graphQL.CommentQL;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentQLMapper {

    @Mapping(target = "authId",source = "author.authId")
    CommentQL toQL(Comment comment);
}
