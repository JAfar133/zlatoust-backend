package com.zlatoust.services.contents;


import com.zlatoust.eventListeners.EventManager;
import com.zlatoust.mapper.contents.ContentMapper;
import com.zlatoust.mapper.contents.SermonMapper;
import com.zlatoust.models.Content;
import com.zlatoust.models.Sermon;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class SermonService extends ContentService<Sermon> {

    private final SermonMapper sermonMapper;

    public SermonService(EventManager<Content> eventManager, SermonMapper sermonMapper) {
        super(eventManager);
        this.sermonMapper = sermonMapper;
    }

    @Override
    protected ContentMapper<Sermon> getMapper() {
        return sermonMapper;
    }
}
