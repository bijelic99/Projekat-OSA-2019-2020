package com.ftn.osa.projekat_osa.service.serviceInterface;

import java.util.List;
import java.util.Set;

import com.ftn.osa.projekat_osa.exceptions.InvalidConditionException;
import com.ftn.osa.projekat_osa.exceptions.InvalidOperationException;
import com.ftn.osa.projekat_osa.model.Folder;
import com.ftn.osa.projekat_osa.model.Message;
import com.ftn.osa.projekat_osa.model.Rule;

public interface RuleServiceInterface {

    Rule getOne(Long ruleID);

    List<Rule> getAll();

    Rule save(Rule rule);

    void remove(Long ruleID);

    List<Message> executeRuleSetOnNewMessages(Long accountId, List<Message> messages) throws InvalidConditionException, InvalidOperationException;

    Folder executeRuleSet(Long accountId, Long folderId) throws InvalidConditionException, InvalidOperationException;
}
