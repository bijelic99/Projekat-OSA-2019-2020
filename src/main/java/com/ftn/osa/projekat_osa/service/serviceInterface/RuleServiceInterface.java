package com.ftn.osa.projekat_osa.service.serviceInterface;

import java.util.List;

import com.ftn.osa.projekat_osa.model.Rule;

public interface RuleServiceInterface {

	Rule getOne(Long ruleID);
	List<Rule> getAll();
	Rule save(Rule rule);
	void remove(Long ruleID);
}
