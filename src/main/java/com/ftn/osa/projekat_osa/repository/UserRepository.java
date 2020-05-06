package com.ftn.osa.projekat_osa.repository;

import com.ftn.osa.projekat_osa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
