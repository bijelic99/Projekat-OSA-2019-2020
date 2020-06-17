package com.ftn.osa.projekat_osa.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.ftn.osa.projekat_osa.android_dto.FolderDTO;
import com.ftn.osa.projekat_osa.android_dto.FolderMetadataDTO;
import com.ftn.osa.projekat_osa.android_dto.MessageDTO;
import com.ftn.osa.projekat_osa.exceptions.ResourceNotFoundException;
import com.ftn.osa.projekat_osa.exceptions.WrongProtocolException;
import com.ftn.osa.projekat_osa.model.Folder;
import com.ftn.osa.projekat_osa.model.Message;
import com.ftn.osa.projekat_osa.service.MailService;
import com.ftn.osa.projekat_osa.service.serviceInterface.FolderServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ftn.osa.projekat_osa.android_dto.AccountDTO;
import com.ftn.osa.projekat_osa.model.Account;
import com.ftn.osa.projekat_osa.service.serviceInterface.AccountServiceInterface;

import javax.mail.MessagingException;

@RestController
@RequestMapping(value = "api/accounts")
public class AccountController {

    @Autowired
    private AccountServiceInterface accountService;

    @Autowired
    private MailService mailService;

    @Autowired
    private FolderServiceInterface folderServiceInterface;

    @GetMapping
    public ResponseEntity<List<AccountDTO>> getAccounts() {
        List<Account> accounts = accountService.getAll();

        List<AccountDTO> accountsDTO = new ArrayList<AccountDTO>();
        for (Account account : accounts) {
            accountsDTO.add(new AccountDTO(account));
        }
        return new ResponseEntity<List<AccountDTO>>(accountsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AccountDTO> getAccount(@PathVariable("id") Long id) {
        Account account = accountService.getOne(id);
        if (account == null) {
            return new ResponseEntity<AccountDTO>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<AccountDTO>(new AccountDTO(account), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<AccountDTO> saveAccount(@RequestBody AccountDTO accountDTO) throws WrongProtocolException, MessagingException {
        Account account = accountDTO.getJpaEntity();
        account = accountService.add(account);

        return new ResponseEntity<AccountDTO>(new AccountDTO(account), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<AccountDTO> updateAccount(@RequestBody AccountDTO accountDTO, @PathVariable("id") Long accountId) throws WrongProtocolException, MessagingException {
        Account account = accountService.getOne(accountDTO.getId());

        if (account == null) {
            return new ResponseEntity<AccountDTO>(HttpStatus.BAD_REQUEST);
        }

        Account jpaEntityFromDto = accountDTO.getJpaEntity();

        account.setSmtpAddress(jpaEntityFromDto.getSmtpAddress());
        account.setSmtpPort(jpaEntityFromDto.getSmtpPort());
        account.setInServerType(jpaEntityFromDto.getInServerType());
        account.setInServerAddress(jpaEntityFromDto.getInServerAddress());
        account.setInServerPort(jpaEntityFromDto.getInServerPort());
        account.setUsername(jpaEntityFromDto.getUsername());
        account.setPassword(jpaEntityFromDto.getPassword());
        account.setDisplayName(jpaEntityFromDto.getDisplayName());

        account = accountService.save(account);

        return new ResponseEntity<AccountDTO>(new AccountDTO(account), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable("id") Long accountid) {
        Account account = accountService.getOne(accountid);
        if (account != null) {
            accountService.remove(accountid);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping(value = "/{id}/folders")
    public ResponseEntity<Set<FolderMetadataDTO>> getAccountFolders(@PathVariable("id") Long accountId){
        Set<Folder> folders = accountService.getAccountFolders(accountId);
        return new ResponseEntity<>(folders.stream().map(folder -> new FolderMetadataDTO(folder)).collect(Collectors.toSet()), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}/messages")
    public ResponseEntity<Set<MessageDTO>> getMessages(@PathVariable("id") Long accountId) throws WrongProtocolException, MessagingException {
        return new ResponseEntity<>(mailService.getAllMessages(accountId).stream()
                .map(message -> new MessageDTO(message))
                .collect(Collectors.toSet()), HttpStatus.OK);
    }

    @PostMapping(value = "/{id}/folders", consumes = "application/json")
    public ResponseEntity<FolderDTO> addAccountFolder(@PathVariable("id") Long accountId, @RequestBody FolderDTO folderDTO) throws WrongProtocolException, MessagingException {

            Folder folder = folderDTO.getJpaEntity();
            folder.setParentFolder(null);
            folder = folderServiceInterface.save(folder);
            Account account = accountService.getOne(accountId);
            account.getAccountFolders().add(folder);
            accountService.save(account);

            return new ResponseEntity<>(new FolderDTO(folder), HttpStatus.OK);

    }
}
