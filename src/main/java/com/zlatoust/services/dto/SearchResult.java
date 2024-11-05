package com.zlatoust.services.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SearchResult {
    private String id;
    private String title;
    private String author;
    private String description;
    private String shortDescription;
    private String contentId;
    private String contentType;
    private String url;
    private float score;
}
