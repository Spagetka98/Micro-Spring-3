package cz.spagetka.newsService.mapper;

import cz.spagetka.newsService.model.db.Comment;
import cz.spagetka.newsService.model.db.News;
import cz.spagetka.newsService.model.db.User;
import cz.spagetka.newsService.model.dto.graphQL.CommentQL;
import cz.spagetka.newsService.model.dto.graphQL.NewsQL;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface NewsQLMapper {
    @Mapping(target = "newsId", source = "id")
    @Mapping(target = "userId", source = "creator.authId")
    @Mapping(target = "creationDate", source = "createdAt")
    @Mapping(target = "updateDate", source = "updatedAt")
    @Mapping(target = "totalLikes", source = ".",qualifiedByName = "totalLikes")
    @Mapping(target = "totalDislikes", source = ".",qualifiedByName = "totalDislikes")
    @Mapping(target = "totalComments", source = ".",qualifiedByName = "totalComments")
    @Mapping(target = "isLikedByUser", source = ".",qualifiedByName = "isLikedByUser")
    @Mapping(target = "isDislikedByUser", source = ".",qualifiedByName = "isDislikedByUser")
    NewsQL toQL(News news, @Context User user);

    @Mapping(target = "authId",source = "author.authId")
    CommentQL toQL(Comment comment);

    @Named("totalLikes")
    default long totalLikesToNewsDTO(News news) {
        return news.getLikedByUsers().size();
    }

    @Named("totalDislikes")
    default long totalDislikesToNewsDTO(News news) {
        return news.getDislikedByUsers().size();
    }

    @Named("totalComments")
    default long totalCommentsToNewsDTO(News news) {
        return news.getComments().size();
    }

    @Named("isLikedByUser")
    default boolean isLikedByUserToNewsDTO(News news,@Context User user) {
        return news.getLikedByUsers().contains(user);
    }

    @Named("isDislikedByUser")
    default boolean iisDislikedByUserToNewsDTO(News news,@Context User user) {
        return news.getDislikedByUsers().contains(user);
    }
}
