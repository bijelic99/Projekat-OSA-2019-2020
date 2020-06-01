package com.ftn.osa.projekat_osa.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.ftn.osa.projekat_osa.exceptions.InvalidConditionException;
import com.ftn.osa.projekat_osa.exceptions.InvalidOperationException;
import com.ftn.osa.projekat_osa.model.Folder;
import com.ftn.osa.projekat_osa.model.Message;
import com.ftn.osa.projekat_osa.repository.FolderRepository;
import com.ftn.osa.projekat_osa.repository.MessageRepository;
import com.ftn.osa.projekat_osa.utillity.RuleHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.osa.projekat_osa.model.Rule;
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

    //TODO Proveriti
    @Override
    public Set<Message> executeRuleSet(Long accountId, Set<Message> messages) {
        Set<Rule> rules = ruleRepository.getAccountRules(accountId);
        return messages.stream()
                .filter(message -> !rules.stream()
                                .map(rule -> {
                                    try {
                                        return RuleHelper.executeRule(rule, message, new RuleHelper.RuleActionInterface() {
                                            @Override
                                            public void moveAction(Message message, Folder destinationFolder) {
                                                Optional<Folder> optionalFolder = folderRepository.getFolderThatContainsMessage(message.getId());
                                                if(optionalFolder.isPresent()){
                                                    Folder currentFolder = optionalFolder.get();
                                                    currentFolder.getMessages().remove(message);
                                                    folderRepository.save(currentFolder);

                                                    destinationFolder.getMessages().add(message);
                                                    folderRepository.save(destinationFolder);
                                                }
                                            }

                                            @Override
                                            public void copyAction(Message message, Folder destinationFolder) {
                                                message = new Message(null, message.getFrom(), message.getTo(),
                                                        message.getCc(), message.getBcc(), message.getDateTime(),
                                                        message.getSubject(), message.getContent(), message.isUnread(),
                                                        message.getTags(), message.getAttachments(), message.getAccount());

                                                message = messageRepository.save(message);

                                                destinationFolder.getMessages().add(message);
                                                    folderRepository.save(destinationFolder);

                                            }

                                            @Override
                                            public void deleteAction(Message message) {
                                                messageRepository.deleteById(message.getId());
                                            }
                                        });
                                    } catch (InvalidConditionException e) {
                                        e.printStackTrace();
                                    } catch (InvalidOperationException e) {
                                        e.printStackTrace();
                                    }
                                    return null;
                                })
                                .filter(aBoolean -> aBoolean != null)
                                .reduce(false, (aBoolean, aBoolean2) -> aBoolean || aBoolean2 )
        ).collect(Collectors.toSet());
    }


}
