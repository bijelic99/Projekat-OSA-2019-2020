package com.ftn.osa.projekat_osa.repository;

import com.ftn.osa.projekat_osa.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("Select u from User u where u.username = :username")
    Optional<User> findUserByUsername(@Param("username") String username);

    @Query("Select u from User u join u.userAccounts ua where ua.id = :accountId")
    Optional<User> findUserForAccount(@Param("accountId") Long id);

    @Query("Select u from User u join u.userTags ut where ut.id = :tagId")
    Optional<User> findUserForTag(@Param("tagId") Long id);

    @Query("Select u from User u join u.userContacts uc where uc.id = :contactId")
    Optional<User> findUserForContact(@Param("contactId") Long id);

}
