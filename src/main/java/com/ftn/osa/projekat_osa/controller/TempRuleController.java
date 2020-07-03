package com.ftn.osa.projekat_osa.controller;

import com.ftn.osa.projekat_osa.android_dto.FolderDTO;
import com.ftn.osa.projekat_osa.android_dto.MessageDTO;
import com.ftn.osa.projekat_osa.exceptions.InvalidConditionException;
import com.ftn.osa.projekat_osa.exceptions.InvalidOperationException;
import com.ftn.osa.projekat_osa.model.Folder;
import com.ftn.osa.projekat_osa.model.Message;
import com.ftn.osa.projekat_osa.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

//TODO za odbranu osa promeniti ovo, ne valja ovako
@RestController
@RequestMapping(value = "api")
public class TempRuleController {
    @Autowired
    RuleService ruleService;

    @GetMapping("/{accountId}/{folderId}")
    public ResponseEntity<FolderDTO> getMessages(@PathVariable("accountId") Long aid, @PathVariable("folderId") Long fid) throws InvalidConditionException, InvalidOperationException {
        Folder f = ruleService.executeRuleSet(aid, fid);
        return ResponseEntity.ok(new FolderDTO(f));
    }
}
