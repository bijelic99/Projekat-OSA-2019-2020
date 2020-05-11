package com.ftn.osa.projekat_osa.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.osa.projekat_osa.dto.AccountDTO;
import com.ftn.osa.projekat_osa.model.Account;
import com.ftn.osa.projekat_osa.service.serviceInterface.AccountServiceInterface;

@RestController
@RequestMapping(value="api/accounts")
public class AccountController {
	
	@Autowired
	private AccountServiceInterface accountService;
	
	@GetMapping
	public ResponseEntity<List<AccountDTO>> getAccounts() {
		List<Account> accounts = accountService.getAll();
		
		List<AccountDTO> accountsDTO = new ArrayList<AccountDTO>();
		for(Account account : accounts) {
			accountsDTO.add(new AccountDTO(account));
		}
		return new ResponseEntity<List<AccountDTO>>(accountsDTO, HttpStatus.OK);
	}
	
	@GetMapping(value="/{id}")
	public ResponseEntity<AccountDTO> getAccount(@PathVariable("id") Long id){
		Account account = accountService.getOne(id);
		if(account == null) {
			return new ResponseEntity<AccountDTO>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<AccountDTO>(new AccountDTO(account), HttpStatus.OK);
	}
	
	@PostMapping(consumes="application/json")
	public ResponseEntity<AccountDTO> saveAccount(@RequestBody AccountDTO accountDTO){
		Account account = new Account();
		account.setSmtpAddress(accountDTO.getSmtpAddress());
		account.setSmtpPort(accountDTO.getSmtpPort());
		account.setInServerType(accountDTO.getInServerType());
		account.setInServerAddress(accountDTO.getInServerAddress());
		account.setInServerPort(accountDTO.getInServerPort());
		account.setUsername(accountDTO.getUsername());
		account.setPassword(accountDTO.getPassword());
		account.setDisplayName(accountDTO.getDisplayName());
		
		account = accountService.save(account);
		return new ResponseEntity<AccountDTO>(new AccountDTO(account), HttpStatus.CREATED);
	}
	
	@PutMapping(value="/{id}", consumes="application/json")
	public ResponseEntity<AccountDTO> updateAccount(@RequestBody AccountDTO accountDTO, @PathVariable("id") Long accountId){
		Account account = accountService.getOne(accountDTO.getId());
			
		if(account == null) {
			return new ResponseEntity<AccountDTO>(HttpStatus.BAD_REQUEST);
		}
		
		account.setSmtpAddress(accountDTO.getSmtpAddress());
		account.setSmtpPort(accountDTO.getSmtpPort());
		account.setInServerType(accountDTO.getInServerType());
		account.setInServerAddress(accountDTO.getInServerAddress());
		account.setInServerPort(accountDTO.getInServerPort());
		account.setUsername(accountDTO.getUsername());
		account.setPassword(accountDTO.getPassword());
		account.setDisplayName(accountDTO.getDisplayName());
		
		account = accountService.save(account);
		
		return new ResponseEntity<AccountDTO>(new AccountDTO(account), HttpStatus.OK);
	}
	
	@DeleteMapping(value="/{id}")
	public ResponseEntity<Void> deleteAccount(@PathVariable("id") Long accountid){
		Account account = accountService.getOne(accountid);
		if(account != null) {
			accountService.remove(accountid);
			return new ResponseEntity<Void>(HttpStatus.OK);
		}else {
			return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
		}
	}
}
