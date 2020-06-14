package com.ftn.osa.projekat_osa.repository;

import com.ftn.osa.projekat_osa.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface ContactRepository extends JpaRepository<Contact, Long> {
    @Query("Select u.userContacts from User u where u.id = :id")
    Set<Contact> getUsersContacts(@Param("id") Long id);
}
