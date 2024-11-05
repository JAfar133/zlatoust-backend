package com.zlatoust.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class News extends Content {
    private LocalDateTime viewDateFrom;
    private LocalDateTime viewDateTo;

    @Override
    public String getType() {
        return "news";
    }
}
