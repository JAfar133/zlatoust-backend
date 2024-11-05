package com.zlatoust.services.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SiteSettings {
    private boolean announcementCommentsEnabled;
    private boolean newsCommentsEnabled;
    private boolean sermonsCommentsEnabled;
    private int maxAnnouncementShowed;
    private int maxNewsShowed;
    private int maxSermonsShowed;
}
