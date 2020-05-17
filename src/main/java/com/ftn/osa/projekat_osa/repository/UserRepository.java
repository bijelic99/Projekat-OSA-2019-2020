package com.ftn.osa.projekat_osa.repository;

import com.ftn.osa.projekat_osa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("Select u from User u where u.username = :username")
    Optional<User> findUserByUsername(@Param("username") String username);

}
