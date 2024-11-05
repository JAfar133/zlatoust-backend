package com.zlatoust.models;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Announcement extends Content {

    private LocalDateTime viewDateFrom;
    private LocalDateTime viewDateTo;

    @Override
    public String getType() {
        return "announcements";
    }
}