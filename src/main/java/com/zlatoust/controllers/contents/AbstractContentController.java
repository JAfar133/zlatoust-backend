package com.zlatoust.controllers.contents;

import com.zlatoust.models.Content;
import com.zlatoust.models.Image;
import com.zlatoust.services.ImageService;
import com.zlatoust.services.contents.ContentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public abstract class AbstractContentController<T extends Content> {

    protected final ImageService imageService;
    protected abstract ContentService<T> getService();

    protected AbstractContentController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/get")
    public List<T> getAll(@RequestParam(required = false) Integer page,
                          @RequestParam(required = false) Integer limit,
                          @RequestParam(required = false) Boolean published) {
        final int pageLimit = 1000;
        page = (page != null && page > 0) ? page - 1 : 0;
        limit = (limit != null && limit > 0 && limit < pageLimit) ? limit : pageLimit;

        return getService().getAll(page, limit, published);
    }

    @GetMapping("/get-removed")
    public List<T> getAllRemoved(@RequestParam(required = false) Integer page,
                          @RequestParam(required = false) Integer limit) {
        final int pageLimit = 1000;
        page = (page != null && page > 0) ? page - 1 : 0;
        limit = (limit != null && limit > 0 && limit < pageLimit) ? limit : pageLimit;

        return getService().getAllRemoved(page, limit);
    }


    @GetMapping("/get/{id}")
    public ResponseEntity<T> getById(@PathVariable long id) {
        T item = getService().getById(id);
        if (item != null) {
            return ResponseEntity.ok(item);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/save")
    public ResponseEntity<T> add(@RequestBody T item) {
        T savedItem = getService().add(item);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedItem);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Void> update(@PathVariable long id, @RequestBody T item) {
        getService().update(id, item);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/images/upload/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadImage(
            @PathVariable long id,
            @RequestParam("file") MultipartFile file) throws IOException {
        Image image = imageService.saveImage(file);
        getService().addImage(image.getId(), id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-main-image/{id}")
    public ResponseEntity<Image> getMainImage(@PathVariable long id) {
        return ResponseEntity.ok(getService().getMainImage(id));
    }

    @GetMapping("/images/get/{id}")
    public ResponseEntity<List<Image>> getImages(@PathVariable long id) {
        return ResponseEntity.ok(getService().getImages(id));
    }

    @DeleteMapping("/images/delete/{itemId}")
    public ResponseEntity<Void> deleteImage(@PathVariable long itemId, @RequestParam("imageId") long imageId) {
        getService().deleteImage(itemId, imageId);
        imageService.deleteImageIfNotUsed(imageId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        getService().delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public ResponseEntity<Long> count() {
        long count = getService().count();
        return ResponseEntity.ok(count);
    }
}
