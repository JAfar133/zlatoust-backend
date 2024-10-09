package com.zlatoust.services.dto.mapper;

import com.zlatoust.models.User;
import com.zlatoust.services.dto.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface UserDTOMapper extends EntityDTOMapper<UserDTO, User>{
    @Override
    User toEntity(UserDTO dto);

    @Override
    UserDTO toDto(User entity);
}
