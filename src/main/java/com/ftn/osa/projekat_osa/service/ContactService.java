package com.ftn.osa.projekat_osa.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.ftn.osa.projekat_osa.model.Account;
import com.ftn.osa.projekat_osa.model.Photo;
import com.ftn.osa.projekat_osa.model.User;
import com.ftn.osa.projekat_osa.repository.AccountRepository;
import com.ftn.osa.projekat_osa.repository.PhotoRepository;
import com.ftn.osa.projekat_osa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.osa.projekat_osa.model.Contact;
import com.ftn.osa.projekat_osa.repository.ContactRepository;
import com.ftn.osa.projekat_osa.service.serviceInterface.ContactServiceInterface;

@Service
public class ContactService implements ContactServiceInterface {

    @Autowired
    ContactRepository contactRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PhotoRepository photoRepository;

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

    @Override
    public Set<Contact> getUsersContacts(Long userId) {
        return contactRepository.getUsersContacts(userId);
    }

    @Override
    public Contact addUserContact(Long userId, Contact contact) {
        User user = userRepository.getOne(userId);
        contact.setContactPhotos(new HashSet<>(photoRepository.saveAll(contact.getContactPhotos())));
        contact = contactRepository.save(contact);
        user.getUserContacts().add(contact);
        userRepository.save(user);

        return contact;
    }
}
