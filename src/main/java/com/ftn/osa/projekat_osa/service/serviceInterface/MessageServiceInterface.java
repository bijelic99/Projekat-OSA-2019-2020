package com.ftn.osa.projekat_osa.service.serviceInterface;

import java.util.List;
import java.util.Set;

import com.ftn.osa.projekat_osa.model.Message;
import com.ftn.osa.projekat_osa.model.Tag;

public interface MessageServiceInterface {

	public List<Message> getAll();
	public Message getOne(Long messageID);
	public Message save(Message message);
	public void remove(Long messageID);
	public Set<Tag> addTags(Long messageId, Set<Tag> tags) throws NullPointerException;
}
