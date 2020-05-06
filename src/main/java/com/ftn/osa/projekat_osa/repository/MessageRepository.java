package com.ftn.osa.projekat_osa.repository;

import com.ftn.osa.projekat_osa.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
