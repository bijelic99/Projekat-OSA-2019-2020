package com.ftn.osa.projekat_osa.repository;

import com.ftn.osa.projekat_osa.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}
