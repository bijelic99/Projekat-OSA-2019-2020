package com.ftn.osa.projekat_osa.service.serviceInterface;

import java.util.List;
import java.util.Set;

import com.ftn.osa.projekat_osa.model.Message;
import com.ftn.osa.projekat_osa.model.Rule;

public interface RuleServiceInterface {

    Rule getOne(Long ruleID);

    List<Rule> getAll();

    Rule save(Rule rule);

    void remove(Long ruleID);

    /**
     * Metod bi trebao da izvrsava sva pravila vezana za nalog nad prosledjenim porukama
     * @param accountId id naloga za koga se uzimaju pravila
     * @param messages set poruka nad kojima treba primeniti pravila
     * @return poruke koje nisu odgovarale ni jednom pravilu
     */
    Set<Message> executeRuleSet(Long accountId, Set<Message> messages);
}
