package com.ftn.osa.projekat_osa.controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.ftn.osa.projekat_osa.android_dto.FolderDTO;
import com.ftn.osa.projekat_osa.android_dto.TagDTO;
import com.ftn.osa.projekat_osa.exceptions.ResourceNotFoundException;
import com.ftn.osa.projekat_osa.model.Folder;
import com.ftn.osa.projekat_osa.model.Tag;
import com.ftn.osa.projekat_osa.service.MailService;
import com.ftn.osa.projekat_osa.service.serviceInterface.FolderServiceInterface;
import com.ftn.osa.projekat_osa.service.serviceInterface.MailServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ftn.osa.projekat_osa.android_dto.MessageDTO;
import com.ftn.osa.projekat_osa.model.Message;
import com.ftn.osa.projekat_osa.service.serviceInterface.MessageServiceInterface;

import javax.mail.MessagingException;

@RestController
@RequestMapping(value = "api/messages")
public class MessageController {

	@Autowired
	private MessageServiceInterface messageService;

	@Autowired
	private FolderServiceInterface folderService;

	@Autowired
	private MailServiceInterface mailServiceInterface;
	
	@GetMapping
	public ResponseEntity<List<MessageDTO>> getMessages(@RequestParam(required = false) String sortBy, @RequestParam(required = false) String tag ) {
		List<Message> messages = messageService.getAll();
		
		List<MessageDTO> messagesDTO = new ArrayList<MessageDTO>();
		for(Message mess : messages) {
			messagesDTO.add(new MessageDTO(mess));
		}
		if(sortBy != null){
			if(sortBy.equals("subject")) messagesDTO.sort(Comparator.comparing(MessageDTO::getSubject));
			if(sortBy.equals("sender")) messagesDTO.sort(Comparator.comparing(o -> o.getFrom().getEmail()));
			if(sortBy.equals("date")) messagesDTO.sort(Comparator.comparing(MessageDTO::getDateTime));
		}
		if(tag != null) messagesDTO = messagesDTO.stream().filter(message -> message.getTags().stream()
				.filter(mailTag -> mailTag.getName().toLowerCase().equals(tag.toLowerCase())).count() > 0).collect(Collectors.toList());
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

	@PutMapping(value = "/{messageId}/tags")
	public ResponseEntity<Object> putMessageTag(@PathVariable("messageId") Long id, @RequestBody Set<TagDTO> tags){
		try {
			Set<Tag> allMessageTags = messageService.addTags(id, tags.stream().map(tagDTO -> tagDTO.getJpaEntity()).collect(Collectors.toSet()));
			return new ResponseEntity<>(allMessageTags.stream().map(tag -> new TagDTO(tag)).collect(Collectors.toSet()), HttpStatus.OK);
		}
		catch (NullPointerException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}
	}

	@PutMapping(value = "/{messageID}/move", consumes = "application/json")
	public ResponseEntity<Void> moveMessage(@PathVariable("messageID") Long messageId, @RequestBody FolderDTO folderDTO){
		Message message = messageService.getOne(messageId);
		Folder folder = folderService.getOne(folderDTO.getId());
		Folder thisF;
		List<Folder> folders = folderService.getAll();

		for(Folder f:folders) {
			Set<Message> messagesF = f.getMessages();
			for(Message m:messagesF){
				if(m.getId() == messageId){
					thisF = folderService.getOne(f.getId());
					messagesF.remove(message);
					thisF.setMessages(messagesF);
					folderService.save(f);
				}
			}
			if(f.getId() == folderDTO.getId()){
				Set<Message> messagesFN = f.getMessages();
				messagesFN.add(message);
				folder.setMessages(messagesFN);
				folderService.save(f);
			}
		}
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@PostMapping(value = "/send", consumes = "application/json")
	public ResponseEntity<MessageDTO> sendMessage(@RequestBody MessageDTO messageDTO) throws ResourceNotFoundException, MessagingException {
		Message message = mailServiceInterface.sendMessage(messageDTO.getJpaEntity());
		return ResponseEntity.ok(new MessageDTO(message));
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> deleteMessage(@PathVariable("id") Long messageId){
		messageService.remove(messageId);
		return ResponseEntity.ok().build();
	}
}
