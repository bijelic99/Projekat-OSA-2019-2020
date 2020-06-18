package com.ftn.osa.projekat_osa.repository;

import com.ftn.osa.projekat_osa.model.Contact;
import com.ftn.osa.projekat_osa.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface TagRepository extends JpaRepository<Tag, Long> {
    @Query("Select u.userTags from User u where u.id = :id")
    Set<Tag> getUsersTags(@Param("id") Long id);
}
