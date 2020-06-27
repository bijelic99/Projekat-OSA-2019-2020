package com.ftn.osa.projekat_osa.controller;

import com.ftn.osa.projekat_osa.android_dto.AccountDTO;
import com.ftn.osa.projekat_osa.android_dto.ContactDTO;
import com.ftn.osa.projekat_osa.android_dto.TagDTO;
import com.ftn.osa.projekat_osa.android_dto.UserDTO;
import com.ftn.osa.projekat_osa.exceptions.*;
import com.ftn.osa.projekat_osa.model.Account;
import com.ftn.osa.projekat_osa.model.Contact;
import com.ftn.osa.projekat_osa.model.Tag;
import com.ftn.osa.projekat_osa.model.User;
import com.ftn.osa.projekat_osa.service.ContactService;
import com.ftn.osa.projekat_osa.service.UserService;
import com.ftn.osa.projekat_osa.service.serviceInterface.AccountServiceInterface;
import com.ftn.osa.projekat_osa.service.serviceInterface.ContactServiceInterface;
import com.ftn.osa.projekat_osa.service.serviceInterface.TagServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ContactServiceInterface contactService;

    @Autowired
    private TagServiceInterface tagService;

    @Autowired
    private AccountServiceInterface accountServiceInterface;

    @PutMapping(value = "/{id}/changePassword")
    public ResponseEntity<Object> changePassword(@PathVariable(value = "id") Long id, @RequestBody Map<String, String> data) {
        //TODO odjaviti korisnika nakon uspesne promene lozinke
        if (data.containsKey("currentPassword") && data.containsKey("newPassword")) {
            try {
                if (userService.changePassword(id, data.get("currentPassword"), data.get("newPassword")).isPresent())
                    return new ResponseEntity<>(HttpStatus.OK);
                else return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            } catch (ResourceNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            } catch (WrongPasswordException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }
        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable("id") Long id, @RequestBody Map<String, String> data) {
        try {
            Optional<User> userOptional = userService.updateUser(id, data);
            if (userOptional.isPresent()) return new ResponseEntity<>(new UserDTO(userOptional.get()), HttpStatus.OK);
            else return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal error");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{id}/contacts")
    public ResponseEntity<Set<ContactDTO>> getUserContacts(@PathVariable(value = "id") Long id){
        Set<Contact> contacts = contactService.getUsersContacts(id);
        return new ResponseEntity<>(contacts.stream().map(ContactDTO::new).collect(Collectors.toSet()), HttpStatus.OK);
    }

    @PostMapping(value = "/{id}/contacts", consumes = "application/json")
    public ResponseEntity<ContactDTO> addUserContact(@PathVariable(value = "id") Long id, @RequestBody ContactDTO contact){
        Contact contact1 = contactService.addUserContact(id, contact.getJpaEntity());
        return new ResponseEntity<>(new ContactDTO(contact1), HttpStatus.OK);
    }

    @GetMapping("{id}/tags")
    public ResponseEntity<Set<TagDTO>> getUserTags(@PathVariable(value = "id") Long id){
        Set<Tag> tags = tagService.getUsersTags(id);
        return new ResponseEntity<>(tags.stream().map(TagDTO::new).collect(Collectors.toSet()), HttpStatus.OK);
    }
    @PostMapping(value = "/{id}/tags", consumes = "application/json")
    public ResponseEntity<TagDTO> addUserTag(@PathVariable(value = "id") Long id, @RequestBody TagDTO tag){
        Tag tag1 = tagService.addUserTag(id, tag.getJpaEntity());
        return  new ResponseEntity<>(new TagDTO(tag1), HttpStatus.OK);
    }

    @PostMapping("{id}/accounts")
    public ResponseEntity<AccountDTO> addUserAccount(@PathVariable(value = "id") Long id, @RequestBody AccountDTO accountDTO) throws MessagingException, InvalidConditionException, InvalidOperationException, WrongProtocolException {
        Account account = accountServiceInterface.addUserAccount(accountDTO.getJpaEntity(), id);
        return ResponseEntity.ok(new AccountDTO(account));
    }

    @GetMapping("{id}/accounts")
    public ResponseEntity<Set<AccountDTO>> getUserAccounts(@PathVariable(value = "id") Long id){
        Set<Account> accounts = accountServiceInterface.getUserAccounts(id);
        return ResponseEntity.ok(accounts.stream().map(account -> new AccountDTO(account)).collect(Collectors.toSet()));
    }

}
