package com.zlatoust.mapper;

import com.zlatoust.models.Album;
import com.zlatoust.models.Image;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AlbumMapper {
    Album getAlbumById(long id);
    List<Album> getAllAlbums();
    List<Image> getAlbumImagesByAlbumId(long id);
    void addAlbum(Album album);
    void updateAlbum(Album album);
    void deleteAlbum(long id);
    void addImageToAlbum(@Param("albumId") long albumId, @Param("imageId") long imageId);
    void deleteImage(@Param("albumId") long albumId, @Param("imageId") long imageId);

    Image getAlbumFirstImage(long id);
}
