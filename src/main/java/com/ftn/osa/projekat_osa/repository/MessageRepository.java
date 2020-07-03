package com.ftn.osa.projekat_osa.repository;

import com.ftn.osa.projekat_osa.model.Folder;
import com.ftn.osa.projekat_osa.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("Select f from Folder f join f.messages fm where fm.id = :messageId")
    Optional<Folder> getFolderWithMessage(@Param("messageId") Long messageId);
}
