package com.ftn.osa.projekat_osa.controller;

import java.util.ArrayList;
import java.util.List;

import com.ftn.osa.projekat_osa.android_dto.FolderDTO;
import com.ftn.osa.projekat_osa.exceptions.InvalidConditionException;
import com.ftn.osa.projekat_osa.exceptions.InvalidOperationException;
import com.ftn.osa.projekat_osa.model.Folder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ftn.osa.projekat_osa.android_dto.RuleDTO;
import com.ftn.osa.projekat_osa.model.Rule;
import com.ftn.osa.projekat_osa.service.serviceInterface.FolderServiceInterface;
import com.ftn.osa.projekat_osa.service.serviceInterface.RuleServiceInterface;

@RestController
@RequestMapping(value = "api/rules")
public class RuleController {

    @Autowired
    private RuleServiceInterface ruleService;

    @Autowired
    private FolderServiceInterface folderService;

    @GetMapping
    public ResponseEntity<List<RuleDTO>> getRules() {
        List<Rule> rules = ruleService.getAll();

        List<RuleDTO> rulesDTO = new ArrayList<RuleDTO>();
        for (Rule rule : rules) {
            rulesDTO.add(new RuleDTO(rule));
        }
        return new ResponseEntity<List<RuleDTO>>(rulesDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<RuleDTO> getRule(@PathVariable("id") Long id) {
        Rule rule = ruleService.getOne(id);
        if (rule == null) {
            return new ResponseEntity<RuleDTO>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<RuleDTO>(new RuleDTO(rule), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<RuleDTO> saveRule(@RequestBody RuleDTO ruleDTO) {
        Rule rule = ruleDTO.getJpaEntity();

        rule = ruleService.save(rule);
        return new ResponseEntity<RuleDTO>(new RuleDTO(rule), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public ResponseEntity<RuleDTO> updateRule(@RequestBody RuleDTO ruleDTO) {
        Rule rule = ruleService.getOne(ruleDTO.getId());

        if (rule == null) {
            return new ResponseEntity<RuleDTO>(HttpStatus.BAD_REQUEST);
        }

        Rule jpaEntityFromDto = ruleDTO.getJpaEntity();

        rule.setCondition(jpaEntityFromDto.getCondition());
        rule.setValue(jpaEntityFromDto.getValue());
        rule.setOperation(jpaEntityFromDto.getOperation());
        rule.setDestinationFolder(folderService.getOne(ruleDTO.getId()));

        rule = ruleService.save(rule);
        return new ResponseEntity<RuleDTO>(new RuleDTO(rule), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteRule(@PathVariable("id") Long ruleID) {
        Rule rule = ruleService.getOne(ruleID);
        if (rule != null) {
            ruleService.remove(ruleID);
            return new ResponseEntity<Void>(HttpStatus.OK);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value =  "/{accountId}/{folderId}")
    public ResponseEntity<FolderDTO> executeRuleOnFolder(@PathVariable("accountId") Long accountId, @PathVariable("accountId") Long folderId) throws InvalidConditionException, InvalidOperationException {
        Folder f = ruleService.executeRuleSet(accountId, folderId);
        return ResponseEntity.ok(new FolderDTO(f));
    }
}
