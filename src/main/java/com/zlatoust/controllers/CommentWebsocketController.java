package com.zlatoust.controllers;

import com.zlatoust.models.User;
import com.zlatoust.security.auth.AuthContext;
import com.zlatoust.services.CommentService;
import com.zlatoust.services.dto.CommentDTO;
import com.zlatoust.services.dto.mapper.UserDTOMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class CommentWebsocketController {

    private final CommentService commentService;
    private final UserDTOMapper userDTOMapper;
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/comment.add")
    public CommentDTO addComment(@Payload CommentDTO comment) {
        User user = AuthContext.getUserFromSecurityContext();
        if (user == null) {
            throw new MessagingException("Пользователь не аутентифицирован");
        }

        CommentDTO savedComment = commentService.insert(comment);

        messagingTemplate.convertAndSend(
                "/comments/" + comment.getContentType() + "/" + comment.getContentId(),
                savedComment
        );

        return savedComment;
    }


}
