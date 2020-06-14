package com.ftn.osa.projekat_osa.service.serviceInterface;

import java.util.List;

import com.ftn.osa.projekat_osa.model.Contact;

public interface ContactServiceInterface {

    public List<Contact> getAll();

    public Contact getOne(Long contactId);

    public Contact save(Contact contact);

    public void remove(Long contactId);

}
