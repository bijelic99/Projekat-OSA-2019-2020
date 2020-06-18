package com.ftn.osa.projekat_osa.service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.ftn.osa.projekat_osa.exceptions.InvalidConditionException;
import com.ftn.osa.projekat_osa.exceptions.InvalidOperationException;
import com.ftn.osa.projekat_osa.model.*;
import com.ftn.osa.projekat_osa.repository.AttachmentRepository;
import com.ftn.osa.projekat_osa.repository.FolderRepository;
import com.ftn.osa.projekat_osa.repository.MessageRepository;
import com.ftn.osa.projekat_osa.utillity.RuleHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.osa.projekat_osa.repository.RuleRepository;
import com.ftn.osa.projekat_osa.service.serviceInterface.RuleServiceInterface;

@Service
public class RuleService implements RuleServiceInterface {

    @Autowired
    RuleRepository ruleRepository;

    @Autowired
    FolderRepository folderRepository;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    AttachmentRepository attachmentRepository;

    @Override
    public Rule getOne(Long ruleID) {
        return ruleRepository.getOne(ruleID);
    }

    @Override
    public List<Rule> getAll() {
        return ruleRepository.findAll();
    }

    @Override
    public Rule save(Rule rule) {
        return ruleRepository.save(rule);
    }

    @Override
    public void remove(Long ruleID) {
        ruleRepository.deleteById(ruleID);
    }

    @Override
    public List<Message> executeRuleSetOnNewMessages(Long accountId, List<Message> messages) throws InvalidConditionException, InvalidOperationException {
        Set<Rule> rules = ruleRepository.getAccountRules(accountId);
        List<Message> ruleReqNotMetMessageList = new ArrayList<>();
        for(Message message : messages){
            Boolean messagePassesRules = false;
            for(Rule rule : rules){
                messagePassesRules = messagePassesRules || RuleHelper.executeRule(rule, message, new RuleHelper.RuleActionInterface() {
                    @Override
                    public void moveAction(Message message, Folder destinationFolder) {
                        destinationFolder.getMessages().add(message);
                        folderRepository.save(destinationFolder);
                    }

                    @Override
                    public void copyAction(Message message, Folder destinationFolder) {
                        destinationFolder.getMessages().add(message);
                        folderRepository.save(destinationFolder);
                    }

                    @Override
                    public void deleteAction(Message message) {
                        messageRepository.deleteById(message.getId());
                    }
                });

                if(rule.getOperation().equals(Operation.DELETE) || rule.getOperation().equals(Operation.MOVE)) break;
            }
            if(!messagePassesRules) ruleReqNotMetMessageList.add(message);
        }

        return ruleReqNotMetMessageList;
    }

    @Override
    public Folder executeRuleSet(Long accountId, Long folderId) throws InvalidConditionException, InvalidOperationException {
        Folder folder = folderRepository.getOne(folderId);
        Set<Rule> rules = ruleRepository.getAccountRules(accountId);
        List<Message> messageList = new ArrayList<>(folder.getMessages());
        for(int i = 0; i<messageList.size(); i++){
            for(Rule rule : rules){
                Integer index = i;
                RuleHelper.executeRule(rule, messageList.get(index), new RuleHelper.RuleActionInterface() {
                    @Override
                    public void moveAction(Message message, Folder destinationFolder) {
                        destinationFolder.getMessages().add(message);
                        messageList.remove(index);
                        folderRepository.save(destinationFolder);
                    }

                    @Override
                    public void copyAction(Message message, Folder destinationFolder) {
                        Message copyMessage = new Message();
                        copyMessage.setDateTime(message.getDateTime());
                        copyMessage.setFrom(message.getFrom());
                        copyMessage.setTo(message.getTo());
                        copyMessage.setCc(message.getCc());
                        copyMessage.setBcc(message.getBcc());
                        copyMessage.setAccount(message.getAccount());
                        copyMessage.setContent(message.getContent());
                        copyMessage.setSubject(message.getSubject());
                        copyMessage.setUnread(message.isUnread());
                        copyMessage.setTags(message.getTags());
                        List<Attachment> copyAttachments = message.getAttachments().stream().map(attachment -> {
                            Attachment newAttachment = new Attachment();
                            newAttachment.setName(attachment.getName());
                            newAttachment.setMime_type(attachment.getMime_type());
                            newAttachment.setData(attachment.getData());
                            return newAttachment;
                        }).collect(Collectors.toList());
                        copyAttachments = attachmentRepository.saveAll(copyAttachments);
                        copyMessage.setAttachments(new HashSet<>(copyAttachments));

                        destinationFolder.getMessages().add(copyMessage);
                        folderRepository.save(destinationFolder);
                    }

                    @Override
                    public void deleteAction(Message message) {
                        messageList.remove(index);
                        messageRepository.deleteById(message.getId());
                    }
                });
                if(rule.getOperation().equals(Operation.DELETE) || rule.getOperation().equals(Operation.MOVE)) break;
            }

        }

        folder.setMessages(new HashSet<>(messageList));
        folder = folderRepository.save(folder);

        return folder;
    }


}
