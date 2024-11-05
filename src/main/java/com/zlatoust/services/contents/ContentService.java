package com.zlatoust.services.contents;

import com.zlatoust.eventListeners.EventManager;
import com.zlatoust.logging.ExcludeFromLogging;
import com.zlatoust.mapper.contents.ContentMapper;
import com.zlatoust.models.Content;
import com.zlatoust.models.Image;
import com.zlatoust.security.auth.AuthContext;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

public abstract class ContentService<T extends Content> {

    protected abstract ContentMapper<T> getMapper();

    private final EventManager<Content> eventManager;

    protected ContentService(EventManager<Content> eventManager) {
        this.eventManager = eventManager;
    }

    @ExcludeFromLogging
    public List<T> getAll(int page, int limit, Boolean published) {
        int offset = page * limit;
        if (published == null) {
            return getMapper().getAll(limit, offset);
        } else {
            if (published) {
                return getMapper().getAllPublished(limit, offset);
            }
            return getMapper().getAllNotPublished(limit, offset);
        }
    }

    @ExcludeFromLogging
    public List<T> getAllRemoved(int page, int limit) {
        int offset = page * limit;
        return getMapper().getAllRemoved(limit, offset);
    }

    @ExcludeFromLogging
    public T getById(long id) {
        return getMapper().getById(id);
    }

    public T add(T item) {
        item.setCreatedDate(LocalDateTime.now());
        item.setAuthor(AuthContext.getUserFromSecurityContext());
        if (item.isPublished()) {
            item.setPublishedDate(LocalDateTime.now());
        }
        getMapper().insert(item);
        eventManager.notifyAdded(item);
        return item;
    }

    public void update(long id, T item) {
        T existingItem = getMapper().getById(id);
        if (existingItem != null) {
            if (!existingItem.isPublished() && item.isPublished()) {
                item.setPublishedDate(LocalDateTime.now());
            }
            if (!existingItem.isRemoved() && item.isRemoved()) {
                item.setPublished(false);
                item.setPublishedDate(null);
                item.setRemovedDate(LocalDateTime.now());
            }
            getMapper().update(item);
            eventManager.notifyUpdated(item);
        } else {
            throw new RuntimeException("Item with id " + id + " not found.");
        }
    }

    public void delete(long id) {
        T existingItem = getById(id);
        if (existingItem != null) {
            getMapper().deleteContentImages(id);
            getMapper().delete(id);
            eventManager.notifyDeleted(existingItem);
        }
    }

    @ExcludeFromLogging
    public int count() {
        return getMapper().publishedCount();
    }

    @ExcludeFromLogging
    public void addImage(long imageId, long contentId) {
        T item = getMapper().getById(contentId);
        if (item != null) {
            if (item.getMainImage() == null) {
                getMapper().setMainImage(contentId, imageId);
            }
            getMapper().insertImage(contentId, imageId);
        }

    }

    @ExcludeFromLogging
    public List<Image> getImages(long id) {
        return getMapper().getImages(id);
    }

    public void deleteImage(long itemId, long imageId) {
        getMapper().deleteImage(itemId, imageId);
    }

    public void deleteContentImages(long itemId) {
        getMapper().deleteContentImages(itemId);
    }

    @ExcludeFromLogging
    public Image getMainImage(long itemId) {
        return getMapper().getMainImage(itemId);
    }

}
