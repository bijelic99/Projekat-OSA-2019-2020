package com.ftn.osa.projekat_osa.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.ftn.osa.projekat_osa.model.Account;
import com.ftn.osa.projekat_osa.model.Folder;
import com.ftn.osa.projekat_osa.model.Tag;
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

    @Override
    public Set<Tag> addTags(Long messageId, Set<Tag> tags) throws NullPointerException {
        if (messageId != null) {
            if (tags != null) {
                Message message = messageRepository.getOne(messageId);
                message.setTags(Stream.concat(message.getTags().stream(), tags.stream()).collect(Collectors.toSet()));
                message = messageRepository.save(message);

                return message.getTags();
            } else throw new NullPointerException("tags argument can't be null");
        } else throw new NullPointerException("messageId argument can't be null");
    }

}
