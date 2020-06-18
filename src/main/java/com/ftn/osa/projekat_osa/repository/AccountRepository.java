package com.ftn.osa.projekat_osa.repository;

import com.ftn.osa.projekat_osa.model.Account;
import com.ftn.osa.projekat_osa.model.Folder;
import com.ftn.osa.projekat_osa.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("select a.accountFolders from Account a where a.id = :id")
    Set<Folder> getAccountFolders(@Param("id") Long accountId);

    @Query("select a from Account a join a.accountFolders f where f.id = :folderId")
    Optional<Account> getAccountFromAccountFolder(@Param("folderId") Long folderId);

    @Query("select f from Account a join a.accountFolders f where a.id = :id and f.name = 'Inbox'")
    Optional<Folder> getAccountIndexFolder(@Param("id") Long id);

    @Query("select m from Message m where m.account.id = :accountId")
    Set<Message> getAccountMessages(@Param("accountId") Long accountId);

    @Query("Select f from Account a join a.accountFolders f where a.id = :id and f.name = 'Sent'")
    Optional<Folder> getAccountSentFolder(@Param("id") Long id);

    @Query("Select f from Account a join a.accountFolders f where a.id = :id and f.name = 'Drafts'")
    Optional<Folder> getAccountDraftsFolder(@Param("id") Long id);


}
