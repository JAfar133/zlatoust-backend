package com.zlatoust.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleEvent {
    private Long id;
    private String title;
    private LocalDateTime date;
}
