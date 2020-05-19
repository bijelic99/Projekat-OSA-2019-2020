package com.ftn.osa.projekat_osa.service.serviceInterface;

import java.util.List;

import com.ftn.osa.projekat_osa.model.Message;

public interface MessageServiceInterface {

	public List<Message> getAll();
	public Message getOne(Long messageID);
	public Message save(Message message);
	public void remove(Long messageID);
}
