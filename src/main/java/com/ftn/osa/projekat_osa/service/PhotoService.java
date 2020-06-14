package com.ftn.osa.projekat_osa.service;

import com.ftn.osa.projekat_osa.model.Photo;
import com.ftn.osa.projekat_osa.repository.PhotoRepository;
import com.ftn.osa.projekat_osa.service.serviceInterface.PhotoServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhotoService implements PhotoServiceInterface {
    @Autowired
    private PhotoRepository photoRepository;

    @Override
    public Photo save(Photo photo) {
        return photoRepository.save(photo);
    }

    @Override
    public void delete(Long id) {
        photoRepository.deleteById(id);
    }
}
