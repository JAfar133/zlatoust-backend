package com.zlatoust.services;


import com.zlatoust.mapper.ImageMapper;
import com.zlatoust.models.Image;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageMapper imageMapper;

    public Image saveImage(MultipartFile file) throws IOException {
        byte[] data = file.getBytes();

        Image image = new Image();
        image.setData(data);
        image.setType(file.getContentType());
        imageMapper.insertImage(image);
        return image;

    }

    public void deleteImageIfNotUsed(long id) {
        imageMapper.deleteImageIfNotUsed(id);
    }
}
