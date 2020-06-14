package com.ftn.osa.projekat_osa.service.serviceInterface;

import com.ftn.osa.projekat_osa.model.Image;
import com.ftn.osa.projekat_osa.model.Photo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface ImageServiceInterface {
    Image saveImage(MultipartFile multipartFile) throws IOException;
    Optional<Image> getImage(Long id);
    void deleteImage(Long id);
}
