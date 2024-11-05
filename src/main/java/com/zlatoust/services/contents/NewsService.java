package com.zlatoust.services.contents;

import com.zlatoust.eventListeners.EventManager;
import com.zlatoust.mapper.contents.NewsMapper;
import com.zlatoust.models.Content;
import com.zlatoust.models.News;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class NewsService extends ContentService<News> {

    private final NewsMapper newsMapper;

    public NewsService(EventManager<Content> eventManager, NewsMapper newsMapper) {
        super(eventManager);
        this.newsMapper = newsMapper;
    }

    @Override
    protected NewsMapper getMapper() {
        return newsMapper;
    }
}
