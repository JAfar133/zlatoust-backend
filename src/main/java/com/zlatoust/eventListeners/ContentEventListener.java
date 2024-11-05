package com.zlatoust.eventListeners;

import com.zlatoust.models.Content;
import com.zlatoust.services.LuceneService;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

@Slf4j
public class ContentEventListener<T extends Content> implements EventListener<T> {
    private final LuceneService luceneService;

    public ContentEventListener(LuceneService luceneService) {
        this.luceneService = luceneService;
    }

    @Override
    public void onAdded(T content) {
        try {
            if (content.isPublished() && !content.isRemoved()) {
                luceneService.indexContentsDocuments(List.of(content));
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void onUpdated(T content) {
        try {
            if (content.isPublished() && !content.isRemoved()) {
                luceneService.indexContentsDocuments(List.of(content));
            } else {
                luceneService.deleteDocument(content);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void onDeleted(T content) {
        luceneService.deleteDocument(content);
    }
}

