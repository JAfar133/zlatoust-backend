package com.zlatoust.mapper;

import com.zlatoust.models.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {
    Comment getById(Long id);
    List<Comment> getCommentsByContent(@Param("contentType") String contentType, @Param("contentId") Long contentId);
    int insert(Comment comment);
    int update(Comment comment);
    int delete(Long id);
}
