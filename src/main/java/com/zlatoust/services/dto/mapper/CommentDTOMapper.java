package com.zlatoust.services.dto.mapper;

import com.zlatoust.models.Comment;
import com.zlatoust.services.dto.CommentDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface CommentDTOMapper extends EntityDTOMapper<CommentDTO, Comment> {

    @Override
    Comment toEntity(CommentDTO dto);

    @Override
    CommentDTO toDto(Comment entity);
}
