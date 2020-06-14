package com.ftn.osa.projekat_osa.controller;

import com.ftn.osa.projekat_osa.android_dto.PhotoDTO;
import com.ftn.osa.projekat_osa.model.Image;
import com.ftn.osa.projekat_osa.model.Photo;
import com.ftn.osa.projekat_osa.service.serviceInterface.ImageServiceInterface;
import com.ftn.osa.projekat_osa.service.serviceInterface.PhotoServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Multipart;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;

@RestController
@RequestMapping(value = ImageController.mappingPath)
public class ImageController {
    public static final String mappingPath = "/api/images";

    @Autowired
    private ImageServiceInterface imageService;
    @Autowired
    private PhotoServiceInterface photoServiceInterface;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PhotoDTO> uploadPhoto(@RequestParam("image") MultipartFile imageFile) throws IOException {
        Image image = imageService.saveImage(imageFile);
        Photo photo = new Photo();
        photo.setPath(mappingPath+"/"+image.getId());
        photo = photoServiceInterface.save(photo);

        return new ResponseEntity<>(new PhotoDTO(photo), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<InputStreamResource> getImage(@PathVariable("id") Long id){
        Optional<Image> optional = imageService.getImage(id);
        if (optional.isPresent()){
            Image i = optional.get();

            InputStreamResource inputStreamResource = new InputStreamResource(new ByteArrayInputStream(i.getImage()));

            return ResponseEntity.ok()
                    .contentLength(i.getImage().length)
                    .contentType(MediaType.parseMediaType(i.getType()))
                    .body(inputStreamResource);
        }
        else return ResponseEntity.notFound().build();
    }
}
