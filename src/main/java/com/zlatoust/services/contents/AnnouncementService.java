package com.zlatoust.services.contents;

import com.zlatoust.eventListeners.EventManager;
import com.zlatoust.mapper.contents.AnnouncementMapper;
import com.zlatoust.mapper.contents.ContentMapper;
import com.zlatoust.mapper.contents.SermonMapper;
import com.zlatoust.models.Announcement;
import com.zlatoust.models.Content;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class AnnouncementService extends ContentService<Announcement> {

    private final AnnouncementMapper announcementMapper;

    public AnnouncementService(EventManager<Content> eventManager, AnnouncementMapper announcementMapper) {
        super(eventManager);
        this.announcementMapper = announcementMapper;
    }

    @Override
    protected ContentMapper<Announcement> getMapper() {
        return announcementMapper;
    }
}
