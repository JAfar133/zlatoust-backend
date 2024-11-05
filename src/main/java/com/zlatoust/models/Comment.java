package com.zlatoust.models;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "replies")
@EqualsAndHashCode(exclude = "replies")
public class Comment {
    private Long id;
    private LocalDateTime timestamp;
    private String message;
    private User user;
    private Long parentCommentId;
    private String contentType;
    private Long contentId;
    private boolean edited;
    private List<Comment> replies;
}
