package com.ftn.osa.projekat_osa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftn.osa.projekat_osa.model.Rule;
import com.ftn.osa.projekat_osa.repository.RuleRepository;
import com.ftn.osa.projekat_osa.service.serviceInterface.RuleServiceInterface;

@Service
public class RuleService implements RuleServiceInterface{

	@Autowired
	RuleRepository ruleRepository;
	
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

	
}
