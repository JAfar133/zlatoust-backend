package com.zlatoust.controllers;

import com.zlatoust.configuration.ApiConstants;
import com.zlatoust.models.User;
import com.zlatoust.security.auth.AuthContext;
import com.zlatoust.services.CommentService;
import com.zlatoust.services.dto.CommentDTO;
import com.zlatoust.services.dto.mapper.UserDTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(ApiConstants.API_PATH_COMMENT)
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final UserDTOMapper userDTOMapper;

    @GetMapping
    public ResponseEntity<List<CommentDTO>> getCommentsByContent(
            @RequestParam String contentType,
            @RequestParam Long contentId) {
        List<CommentDTO> comments = commentService.getCommentsByContent(contentType, contentId);
        return ResponseEntity.ok(commentService.buildCommentTree(comments));
    }


    @PostMapping
    public ResponseEntity<CommentDTO> addComment(@RequestBody CommentDTO comment) {
        User user = AuthContext.getUserFromSecurityContext();
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        comment.setUser(userDTOMapper.toDto(user));
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.insert(comment));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateComment(@PathVariable Long id, @RequestBody CommentDTO comment) {
        CommentDTO existingComment = commentService.getById(id);
        if (existingComment == null) {
            return ResponseEntity.notFound().build();
        }
        User user = AuthContext.getUserFromSecurityContext();
        Long userId = user.getId();
        if (!existingComment.getUser().getId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        comment.setId(id);
        commentService.update(comment);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        CommentDTO existingComment = commentService.getById(id);
        if (existingComment == null) {
            return ResponseEntity.notFound().build();
        }

        User user = AuthContext.getUserFromSecurityContext();
        if (!existingComment.getUser().getId().equals(user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        commentService.delete(id);
        return ResponseEntity.ok().build();
    }
}
