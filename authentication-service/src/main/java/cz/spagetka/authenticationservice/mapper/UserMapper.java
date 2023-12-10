package cz.spagetka.authenticationservice.mapper;

import cz.spagetka.authenticationservice.model.document.User;
import cz.spagetka.authenticationservice.model.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
        @Mapping(target = "userId",expression = "java(user.getUserId().toString())")
        UserDTO toDTO(User user);
}
