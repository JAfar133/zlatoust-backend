package com.zlatoust.services.dto;

import com.zlatoust.models.Comment;
import com.zlatoust.models.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "replies")
@EqualsAndHashCode(exclude = "replies")
public class CommentDTO {
    private Long id;
    private LocalDateTime timestamp;
    private String message;
    private UserDTO user;
    private Long parentCommentId;
    private String contentType;
    private Long contentId;
    private boolean edited;
    private List<CommentDTO> replies;
}
