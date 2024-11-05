package com.zlatoust.configuration;

import com.zlatoust.eventListeners.CommentEventListener;
import com.zlatoust.eventListeners.ContentEventListener;
import com.zlatoust.eventListeners.EventListener;
import com.zlatoust.eventListeners.EventManager;
import com.zlatoust.models.*;
import com.zlatoust.services.LuceneService;
import com.zlatoust.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class EventHandlerConfig {

    private final LuceneService luceneService;
    private final NotificationService notificationService;

    private <T> EventManager<T> createEventManager(EventListener<T> listener) {
        EventManager<T> eventManager = new EventManager<>();
        eventManager.registerListener(listener);
        return eventManager;
    }

    @Bean
    public EventManager<Comment> commentEventManager() {
        return createEventManager(new CommentEventListener<>(notificationService));
    }

    @Bean
    public EventManager<Content> contentEventManager() {
        return createEventManager(new ContentEventListener<>(luceneService));
    }
}
