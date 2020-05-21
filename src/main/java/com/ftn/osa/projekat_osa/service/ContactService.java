package com.ftn.osa.projekat_osa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.osa.projekat_osa.model.Contact;
import com.ftn.osa.projekat_osa.repository.ContactRepository;
import com.ftn.osa.projekat_osa.service.serviceInterface.ContactServiceInterface;

@Service
public class ContactService implements ContactServiceInterface {

    @Autowired
    ContactRepository contactRepository;

    @Override
    public List<Contact> getAll() {
        return contactRepository.findAll();
    }

    @Override
    public Contact getOne(Long contactId) {
        return contactRepository.getOne(contactId);
    }

    @Override
    public Contact save(Contact contact) {
        return contactRepository.save(contact);
    }

    @Override
    public void remove(Long contactId) {
        contactRepository.deleteById(contactId);
    }

}
