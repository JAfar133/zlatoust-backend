package com.zlatoust.services;

import com.zlatoust.eventListeners.EventManager;
import com.zlatoust.mapper.CommentMapper;
import com.zlatoust.models.Comment;
import com.zlatoust.services.dto.CommentDTO;
import com.zlatoust.services.dto.mapper.CommentDTOListMapper;
import com.zlatoust.services.dto.mapper.CommentDTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentMapper commentMapper;
    private final CommentDTOMapper commentDTOMapper;
    private final CommentDTOListMapper commentDTOListMapper;
    private final EventManager<Comment> eventManager;

    public CommentDTO getById(Long id) {
        return commentDTOMapper.toDto(commentMapper.getById(id));
    }

    public List<CommentDTO> getCommentsByContent(String contentType, Long contentId) {
        return commentDTOListMapper.toDto(commentMapper.getCommentsByContent(contentType, contentId));
    }

    public CommentDTO insert(CommentDTO comment) {
        comment.setTimestamp(LocalDateTime.now());
        Comment commentToSave = commentDTOMapper.toEntity(comment);
        commentMapper.insert(commentToSave);
        eventManager.notifyAdded(commentToSave);
        return commentDTOMapper.toDto(commentToSave);
    }

    public void update(CommentDTO comment) {
        comment.setEdited(true);
        Comment commentToSave = commentDTOMapper.toEntity(comment);
        commentMapper.update(commentToSave);
        eventManager.notifyUpdated(commentToSave);
    }

    public void delete(Long id) {
        commentMapper.delete(id);
    }

    public List<CommentDTO> buildCommentTree(List<CommentDTO> comments) {
        Map<Long, CommentDTO> commentMap = comments.stream()
                .collect(Collectors.toMap(CommentDTO::getId, Function.identity()));

        List<CommentDTO> roots = new ArrayList<>();

        for (CommentDTO comment : comments) {
            if (comment.getParentCommentId() == null) {
                roots.add(comment);
            } else {
                CommentDTO parent = commentMap.get(comment.getParentCommentId());
                if (parent != null) {
                    if (parent.getReplies() == null) {
                        parent.setReplies(new ArrayList<>());
                    }
                    parent.getReplies().add(comment);
                }
            }
        }

        return roots;
    }

}
