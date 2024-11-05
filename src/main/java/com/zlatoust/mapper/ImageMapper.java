package com.zlatoust.mapper;

import com.zlatoust.models.Image;
import org.apache.ibatis.annotations.*;


@Mapper
public interface ImageMapper {
    @Insert("INSERT INTO images (data, type) VALUES (#{data}, #{type})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    long insertImage(Image image);

    @Delete("DELETE FROM images i WHERE i.id = #{id}")
    void deleteImage(long id);

    @Select("""
        DELETE FROM images i
            WHERE i.id = #{id}
            AND NOT EXISTS (SELECT 1 FROM news_images ni WHERE ni.image_id = #{id})
            AND NOT EXISTS (SELECT 1 FROM sermons_images si WHERE si.image_id = #{id})
            AND NOT EXISTS (SELECT 1 FROM announcements_images ai WHERE ai.image_id = #{id})
            AND NOT EXISTS (SELECT 1 FROM album_images_associations aia WHERE aia.image_id = #{id})
""")
    void deleteImageIfNotUsed(long id);
}
