package com.zlatoust.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public abstract class Content {
    private Long id;
    private boolean published = false;
    private boolean removed = false;
    private LocalDateTime createdDate;
    private LocalDateTime publishedDate;
    private LocalDateTime removedDate;
    private String title;
    private String description;
    private String shortDescription;
    private boolean canComment = true;
    private User author;
    private Image mainImage;
    private Album album;

    public abstract String getType();
}
