package com.ftn.osa.projekat_osa.repository;

import com.ftn.osa.projekat_osa.model.Account;
import com.ftn.osa.projekat_osa.model.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("select a.accountFolders from Account a where a.id = :id")
    Set<Folder> getAccountFolders(@Param("id") Long accountId);
}
