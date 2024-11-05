package com.zlatoust.controllers;

import com.zlatoust.configuration.ApiConstants;
import com.zlatoust.models.Album;
import com.zlatoust.models.Image;
import com.zlatoust.services.AlbumService;
import com.zlatoust.services.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiConstants.API_PATH_ALBUM)
public class AlbumController {

    private final AlbumService albumService;
    private final ImageService imageService;

    @GetMapping("/{id}")
    public ResponseEntity<Album> getAlbumById(@PathVariable long id) {
        Album album = albumService.getAlbumById(id);
        if (album != null) {
            return ResponseEntity.ok(album);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Album>> getAllAlbums() {
        List<Album> albums = albumService.getAllAlbums();
        return ResponseEntity.ok(albums);
    }

    @GetMapping("/{id}/images")
    public ResponseEntity<List<Image>> getAlbumImages(@PathVariable long id) {
        List<Image> images = albumService.getAlbumImagesByAlbumId(id);
        return ResponseEntity.ok(images);
    }

    @GetMapping("/{id}/first-image")
    public ResponseEntity<Image> getAlbumFirstImage(@PathVariable long id) {
        Image image = albumService.getAlbumFirstImage(id);
        return ResponseEntity.ok(image);
    }

    @PostMapping
    public ResponseEntity<Album> addAlbum(@RequestBody Album album) {
        albumService.addAlbum(album);
        return ResponseEntity.ok(album);
    }

    @PostMapping(value = "/{id}/images/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadImage(
            @PathVariable long id,
            @RequestParam("file") MultipartFile file) throws IOException {
        Image image = imageService.saveImage(file);
        albumService.addImageToAlbum(id, image.getId());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Album> updateAlbum(@PathVariable long id, @RequestBody Album album) {
        Album existingAlbum = albumService.getAlbumById(id);
        if (existingAlbum != null) {
            album.setId(id);
            albumService.updateAlbum(album);
            return ResponseEntity.ok(album);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAlbum(@PathVariable long id) {
        Album existingAlbum = albumService.getAlbumById(id);
        if (existingAlbum != null) {
            albumService.deleteAlbum(id);
            return ResponseEntity.ok("Album deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}/images/{imageId}")
    public ResponseEntity<Void> deleteImage(@PathVariable long id, @PathVariable("imageId") long imageId) {
        albumService.deleteImage(id, imageId);
        imageService.deleteImageIfNotUsed(imageId);
        return ResponseEntity.ok().build();
    }
}
