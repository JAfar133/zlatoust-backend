package com.zlatoust.mapper.contents;

import com.zlatoust.models.Image;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ContentMapper<T> {

    List<T> getAll(@Param("limit") int limit, @Param("offset") int offset);

    List<T> getAllPublished(@Param("limit") int limit, @Param("offset") int offset);

    List<T> getAllNotPublished(@Param("limit") int limit, @Param("offset") int offset);

    List<T> getAllRemoved(@Param("limit") int limit, @Param("offset") int offset);

    T getById(long id);

    int insert(T item);

    void update(T item);

    void delete(long id);

    int publishedCount();

    void insertImage(Long contentId, Long imageId);

    List<Image> getImages(long id);

    void deleteImage(@Param("itemId") Long itemId, @Param("imageId") Long imageId);

    void deleteContentImages(@Param("itemId") Long itemId);

    void setMainImage(@Param("itemId") Long itemId, @Param("imageId") Long imageId);

    Image getMainImage(long id);
}
