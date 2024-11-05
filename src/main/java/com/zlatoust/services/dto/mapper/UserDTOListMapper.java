package com.zlatoust.services.dto.mapper;

import com.zlatoust.models.User;
import com.zlatoust.services.dto.UserDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = UserDTOMapper.class)
public interface UserDTOListMapper extends EntityDTOMapper<List<UserDTO>, List<User>>{
    @Override
    List<User> toEntity(List<UserDTO> dto);

    @Override
    List<UserDTO> toDto(List<User> entity);
}
