package com.zlatoust.services.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LuceneDocument {
    private String id;
    private String title;
    private String description;
    private String type;
    private String url;
    private String author;
    private Boolean generated;
}
