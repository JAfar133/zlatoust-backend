package com.zlatoust.services;

import com.zlatoust.mapper.AlbumMapper;
import com.zlatoust.models.Album;
import com.zlatoust.models.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlbumService {
    private final AlbumMapper albumMapper;

    public Album getAlbumById(long id) {
        return albumMapper.getAlbumById(id);
    }

    public List<Album> getAllAlbums() {
        return albumMapper.getAllAlbums();
    }

    public List<Image> getAlbumImagesByAlbumId(long albumId) {
        return albumMapper.getAlbumImagesByAlbumId(albumId);
    }

    public void addAlbum(Album album) {
        album.setTimestamp(LocalDateTime.now());
        albumMapper.addAlbum(album);
    }

    public void addImageToAlbum(long albumId, long imageId) throws IOException {
        // Associate the image with the album
        albumMapper.addImageToAlbum(albumId, imageId);
    }

    public void updateAlbum(Album album) {
        albumMapper.updateAlbum(album);
    }

    public void deleteImage(long albumId, long imageId) {
        albumMapper.deleteImage(albumId, imageId);
    }

    public void deleteAlbum(long id) {
        albumMapper.deleteAlbum(id);
    }

    public Image getAlbumFirstImage(long id) {
        return albumMapper.getAlbumFirstImage(id);
    }
}
