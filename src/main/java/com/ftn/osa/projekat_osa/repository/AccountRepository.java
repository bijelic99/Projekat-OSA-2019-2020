package com.ftn.osa.projekat_osa.repository;

import com.ftn.osa.projekat_osa.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
