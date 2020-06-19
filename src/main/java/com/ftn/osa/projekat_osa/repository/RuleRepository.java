package com.ftn.osa.projekat_osa.repository;

import com.ftn.osa.projekat_osa.model.Rule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface RuleRepository extends JpaRepository<Rule, Long> {

    @Query("select r from Rule r where r.destinationFolder.id = :folderId")
    Set<Rule> getRulesWithThisDestinationFolder(@Param("folderId") Long folderId);

    @Query("select accountRules from Account a where a.id = :accountId")
    Set<Rule> getAccountRules(@Param("accountId") Long accountId);


}
