package com.ftn.osa.projekat_osa.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.osa.projekat_osa.android_dto.ContactDTO;
import com.ftn.osa.projekat_osa.model.Contact;
import com.ftn.osa.projekat_osa.service.serviceInterface.ContactServiceInterface;

@RestController
@RequestMapping(value = "api/contacts")
public class ContactController {

    @Autowired
    private ContactServiceInterface contactService;

    @GetMapping
    public ResponseEntity<List<ContactDTO>> getContacts() {
        List<Contact> contacts = contactService.getAll();

        List<ContactDTO> contactsDTO = new ArrayList<ContactDTO>();
        for (Contact contact : contacts) {
            contactsDTO.add(new ContactDTO(contact));
        }
        return new ResponseEntity<List<ContactDTO>>(contactsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ContactDTO> getContact(@PathVariable("id") Long id) {
        Contact contact = contactService.getOne(id);

        if (contact == null) {
            return new ResponseEntity<ContactDTO>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<ContactDTO>(new ContactDTO(contact), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<ContactDTO> saveContact(@RequestBody ContactDTO contactDTO) {
        Contact contact = contactDTO.getJpaEntity();

        contact = contactService.save(contact);
        return new ResponseEntity<ContactDTO>(new ContactDTO(contact), HttpStatus.OK);
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<ContactDTO> updateContact(@RequestBody ContactDTO contactDTO) {
        Contact contact = contactService.getOne(contactDTO.getId());

        if (contact == null) {
            return new ResponseEntity<ContactDTO>(HttpStatus.BAD_REQUEST);
        }
        Contact jpaEntityFromDto = contactDTO.getJpaEntity();

        contact.setFirstName(jpaEntityFromDto.getFirstName());
        contact.setLastName(jpaEntityFromDto.getLastName());
        contact.setDisplayName(jpaEntityFromDto.getDisplayName());
        contact.setEmail(jpaEntityFromDto.getEmail());
        contact.setNote(jpaEntityFromDto.getNote());
        contact.setContactPhotos(jpaEntityFromDto.getContactPhotos());

        contact = contactService.save(contact);

        return new ResponseEntity<ContactDTO>(new ContactDTO(contact), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable("id") Long contactId) {
        Contact contact = contactService.getOne(contactId);
        if (contact != null) {
            contactService.remove(contactId);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }
}
