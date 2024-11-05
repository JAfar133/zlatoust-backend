package com.zlatoust.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Album {
    private long id;
    private LocalDateTime timestamp;
    private LocalDate eventDate;
    private String title;
    private String description;
    private String photographer;
}
