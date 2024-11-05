package com.zlatoust.services.dto.mapper;

import com.zlatoust.models.Comment;
import com.zlatoust.services.dto.CommentDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = CommentDTOMapper.class)
public interface CommentDTOListMapper extends EntityDTOMapper<List<CommentDTO>, List<Comment>>{
    @Override
    List<Comment> toEntity(List<CommentDTO> dto);

    @Override
    List<CommentDTO> toDto(List<Comment> entity);
}
