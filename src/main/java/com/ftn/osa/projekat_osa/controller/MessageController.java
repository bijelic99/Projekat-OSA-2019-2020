package com.ftn.osa.projekat_osa.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.ftn.osa.projekat_osa.android_dto.FolderDTO;
import com.ftn.osa.projekat_osa.android_dto.TagDTO;
import com.ftn.osa.projekat_osa.model.Folder;
import com.ftn.osa.projekat_osa.model.Tag;
import com.ftn.osa.projekat_osa.service.serviceInterface.FolderServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ftn.osa.projekat_osa.android_dto.MessageDTO;
import com.ftn.osa.projekat_osa.model.Message;
import com.ftn.osa.projekat_osa.service.serviceInterface.MessageServiceInterface;

@RestController
@RequestMapping(value = "api/messages")
public class MessageController {

    @Autowired
    private MessageServiceInterface messageService;

    @Autowired
    private FolderServiceInterface folderService;

    @GetMapping
    public ResponseEntity<List<MessageDTO>> getMessages() {
        List<Message> messages = messageService.getAll();

        List<MessageDTO> messagesDTO = new ArrayList<MessageDTO>();
        for (Message mess : messages) {
            messagesDTO.add(new MessageDTO(mess));
        }
        return new ResponseEntity<List<MessageDTO>>(messagesDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<MessageDTO> getMessage(@PathVariable("id") Long id) {
        Message message = messageService.getOne(id);
        if (message == null) {
            return new ResponseEntity<MessageDTO>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<MessageDTO>(new MessageDTO(message), HttpStatus.OK);
    }

    @PutMapping(value = "/{messageId}/tags")
    public ResponseEntity<Object> putMessageTag(@PathVariable("messageId") Long id, @RequestBody Set<TagDTO> tags) {
        try {
            Set<Tag> allMessageTags = messageService.addTags(id, tags.stream().map(tagDTO -> tagDTO.getJpaEntity()).collect(Collectors.toSet()));
            return new ResponseEntity<>(allMessageTags.stream().map(tag -> new TagDTO(tag)).collect(Collectors.toSet()), HttpStatus.OK);
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping(value = "/{messageID}/move", consumes = "application/json")
    public ResponseEntity<Void> moveMessage(@PathVariable("messageID") Long messageId) {//, @RequestBody Long folderId){
        Message message = messageService.getOne(messageId);
//		Folder folder = folderService.getOne(folderId);
//		System.out.println(folder.getId());
//		Folder thisF;
        List<Folder> folders = folderService.getAll();

        for (Folder f : folders) {
            Set<Message> messagesF = f.getMessages();
            System.out.println(messagesF);
//			for(Message m:messagesF){
//				System.out.println(m.getId());
//				if(m.getId() == messageId){
//					thisF = f;
//					messagesF.remove(message);
//					thisF.setMessages(messagesF);
//				}
        }
//			if(f.getId() == folderId){
//				Set<Message> messagesFN = f.getMessages();
//				messagesFN.add(message);
//				folder.setMessages(messagesFN);
//			}
//		}
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
