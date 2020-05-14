package com.ftn.osa.projekat_osa.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.osa.projekat_osa.android_dto.MessageDTO;
import com.ftn.osa.projekat_osa.model.Message;
import com.ftn.osa.projekat_osa.service.serviceInterface.MessageServiceInterface;

@RestController
@RequestMapping(value = "api/messages")
public class MessageController {

	@Autowired
	private MessageServiceInterface messageService;
	
	@GetMapping
	public ResponseEntity<List<MessageDTO>> getMessages() {
		List<Message> messages = messageService.getAll();
		
		List<MessageDTO> messagesDTO = new ArrayList<MessageDTO>();
		for(Message mess : messages) {
			messagesDTO.add(new MessageDTO(mess));
		}
		return new ResponseEntity<List<MessageDTO>>(messagesDTO, HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<MessageDTO> getMessage(@PathVariable("id") Long id){
		Message message = messageService.getOne(id);
		if(message == null) {
			return new ResponseEntity<MessageDTO>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<MessageDTO>(new MessageDTO(message), HttpStatus.OK);
	}
}
