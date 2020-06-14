package com.ftn.osa.projekat_osa.service;

import com.ftn.osa.projekat_osa.model.Image;
import com.ftn.osa.projekat_osa.repository.ImageRepository;
import com.ftn.osa.projekat_osa.service.serviceInterface.ImageServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class ImageService implements ImageServiceInterface {
    @Autowired
    private ImageRepository imageRepository;

    @Override
    @Transactional
    public Image saveImage(MultipartFile multipartFile) throws IOException {
        byte[] fileBytes = multipartFile.getBytes();

        Image i = new Image();
        i.setImage(fileBytes);
        i.setType(multipartFile.getContentType());
        i = imageRepository.save(i);

        return i;
    }

    @Override
    public Optional<Image> getImage(Long id) {
        return imageRepository.findById(id);
    }

    @Override
    public void deleteImage(Long id) {
        imageRepository.deleteById(id);
    }
}
