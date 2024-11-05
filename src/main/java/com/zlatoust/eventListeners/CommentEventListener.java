package com.zlatoust.eventListeners;

import com.zlatoust.services.NotificationService;

public class CommentEventListener<Comment> implements EventListener<Comment> {

    private final NotificationService notificationService;

    public CommentEventListener(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Override
    public void onAdded(Comment content) {

    }

    @Override
    public void onUpdated(Comment content) {

    }

    @Override
    public void onDeleted(Comment content) {

    }
}
