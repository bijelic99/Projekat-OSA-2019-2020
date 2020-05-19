package com.ftn.osa.projekat_osa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.osa.projekat_osa.model.Message;
import com.ftn.osa.projekat_osa.repository.MessageRepository;
import com.ftn.osa.projekat_osa.service.serviceInterface.MessageServiceInterface;

@Service
public class MessageService implements MessageServiceInterface {

	@Autowired
	MessageRepository messageRepository;
	
	@Override
	public List<Message> getAll() {
		return messageRepository.findAll();
	}

	@Override
	public Message getOne(Long messageID) {
		return messageRepository.getOne(messageID);
	}

	@Override
	public Message save(Message message) {
		return messageRepository.save(message);
	}

	@Override
	public void remove(Long messageID) {
		messageRepository.deleteById(messageID);
	}

}
