package com.ftn.osa.projekat_osa.service.serviceInterface;

import com.ftn.osa.projekat_osa.model.Photo;

public interface PhotoServiceInterface {
    Photo save(Photo photo);
    void delete(Long id);
}
