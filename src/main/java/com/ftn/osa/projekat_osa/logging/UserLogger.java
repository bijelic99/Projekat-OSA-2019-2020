package com.ftn.osa.projekat_osa.logging;

import com.ftn.osa.projekat_osa.android_dto.*;
import com.ftn.osa.projekat_osa.model.Folder;
import com.ftn.osa.projekat_osa.model.Rule;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserLogger {

    Logger logger = LoggerFactory.getLogger(UserLogger.class);

    @AfterReturning(value = "execution(* com..projekat_osa.controller.RegisterController.register(..))", returning = "returnValue")
    public void logAfterNewUserRegister(JoinPoint joinPoint, ResponseEntity<UserDTO> returnValue) {
        Object[] joinPointArgs = joinPoint.getArgs();
        UserDTO newUser = (UserDTO) joinPointArgs[0];
        if (returnValue.getStatusCode() == HttpStatus.OK)
            logger.info("New user with username of {} successfully registered ", newUser.getUsername());
        else logger.error("User failed to register", newUser.getUsername());

    }

    @AfterReturning(value = "execution(* com..projekat_osa.controller.UserController.changePassword(..))", returning = "returnValue")
    public void logAfterPasswordChanged(JoinPoint joinPoint, ResponseEntity<Object> returnValue) {
        if (returnValue.getStatusCode() == HttpStatus.OK)
            logger.info("User with id of {} successfully changed his password", joinPoint.getArgs()[0]);
        else if (returnValue.getStatusCode().equals(HttpStatus.BAD_REQUEST))
            logger.warn("User with id of {} tried to change the password with wrong current password", joinPoint.getArgs()[0]);
        else
            logger.error("User with id of {} failed to change his password and got {} status code", joinPoint.getArgs()[0], returnValue.getStatusCode().toString());
    }

    @AfterReturning(value = "execution(* com..projekat_osa.controller.UserController.updateUser(..))", returning = "returnValue")
    public void logAfterUserUpdate(JoinPoint joinPoint, ResponseEntity<Object> returnValue) {
        if (returnValue.getStatusCode() == HttpStatus.OK)
            logger.info("User with id of {} successfully updated his profile", joinPoint.getArgs()[0]);
        else
            logger.error("User with id of {} failed to update his account and got {} status code", joinPoint.getArgs()[0], returnValue.getStatusCode().toString());

    }

    @AfterReturning(value = "execution(* com..projekat_osa.controller.MessageController.sendMessage(..))", returning = "returnValue")
    public void logAfterSendMessage(JoinPoint joinPoint, ResponseEntity<MessageDTO> returnValue) {
        Object[] joinPointArgs = joinPoint.getArgs();
        MessageDTO newMessage = (MessageDTO) joinPointArgs[0];
        if (returnValue.getStatusCode() == HttpStatus.OK)
            logger.info("Message was successfully send ");
        else logger.error("Message failed to send");
    }

    @AfterReturning(value = "execution(* com..projekat_osa.controller.AccountController.updateAccount(..))", returning = "returnValue")
    public void logAfterUpdateAccount(JoinPoint joinPoint, ResponseEntity<AccountDTO> returnValue) {
        Object[] joinPointArgs = joinPoint.getArgs();
        AccountDTO newAccount = (AccountDTO) joinPointArgs[0];
        if (returnValue.getStatusCode() == HttpStatus.OK)
            logger.info("Account with id of {} successfully updated", joinPoint.getArgs()[0]);
        else logger.error("Account with id of {} failed to update", joinPoint.getArgs()[0]);
    }

    @AfterReturning(value = "execution(* com..projekat_osa.controller.AccountController.addAccountRule(..))", returning = "returnValue")
    public void logAfterAddAccountRule(JoinPoint joinPoint, ResponseEntity<RuleDTO> returnValue) {
        Object[] joinPointArgs = joinPoint.getArgs();
        RuleDTO newRule = (RuleDTO) joinPointArgs[0];
        if (returnValue.getStatusCode() == HttpStatus.OK)
            logger.info("Rule successfully added");
        else logger.error("Rule  failed to added");
    }

    @AfterReturning(value = "execution(* com..projekat_osa.controller.UserController.addUserContact(..))", returning = "returnValue")
    public void logAfterAddContact(JoinPoint joinPoint, ResponseEntity<ContactDTO> returnValue) {
        Object[] joinPointArgs = joinPoint.getArgs();
        ContactDTO newContact = (ContactDTO) joinPointArgs[0];
        if (returnValue.getStatusCode() == HttpStatus.OK)
            logger.info("Contact successfully added");
        else logger.error("Contact  failed to added");
    }

    @AfterReturning(value = "execution(* com..projekat_osa.controller.FolderController.saveFolder(..))", returning = "returnValue")
    public void logAfterAddFolder(JoinPoint joinPoint, ResponseEntity<FolderDTO> returnValue) {
        Object[] joinPointArgs = joinPoint.getArgs();
        FolderDTO newFolder = (FolderDTO) joinPointArgs[0];
        if (returnValue.getStatusCode() == HttpStatus.OK)
            logger.info("Folder successfully added");
        else logger.error("Folder failed to added");
    }

    @AfterReturning(value = "execution(* com..projekat_osa.controller.FolderController.updateFolder(..))", returning = "returnValue")
    public void logAfterUpdateFolder(JoinPoint joinPoint, ResponseEntity<FolderDTO> returnValue) {
        Object[] joinPointArgs = joinPoint.getArgs();
        FolderDTO newFolder = (FolderDTO) joinPointArgs[0];
        if (returnValue.getStatusCode() == HttpStatus.OK)
            logger.info("Folder with id of {} successfully updated", joinPoint.getArgs()[0]);
        else logger.error("Folder with id of {} failed to update", joinPoint.getArgs()[0]);
    }

    @AfterReturning(value = "execution(* com..projekat_osa.controller.RuleController.saveRule(..))", returning = "returnValue")
    public void logAfterAddRule(JoinPoint joinPoint, ResponseEntity<RuleDTO> returnValue) {
        Object[] joinPointArgs = joinPoint.getArgs();
        RuleDTO newRule = (RuleDTO) joinPointArgs[0];
        if (returnValue.getStatusCode() == HttpStatus.OK)
            logger.info("Rule successfully added");
        else logger.error("Rule failed to added");
    }

    @AfterReturning(value = "execution(* com..projekat_osa.controller.RuleController.updateRule(..))", returning = "returnValue")
    public void logAfterUpdateRule(JoinPoint joinPoint, ResponseEntity<RuleDTO> returnValue) {
        Object[] joinPointArgs = joinPoint.getArgs();
        RuleDTO newRule = (RuleDTO) joinPointArgs[0];
        if (returnValue.getStatusCode() == HttpStatus.OK)
            logger.info("Rule with id of {} successfully updated", joinPoint.getArgs()[0]);
        else logger.error("Rule with id of {} failed to update", joinPoint.getArgs()[0]);
    }

    @AfterReturning(value = "execution(* com..projekat_osa.controller.UserController.addUserTag(..))", returning = "returnValue")
    public void logAfterAddTag(JoinPoint joinPoint, ResponseEntity<TagDTO> returnValue) {
        Object[] joinPointArgs = joinPoint.getArgs();
        TagDTO newTag = (TagDTO) joinPointArgs[0];
        if (returnValue.getStatusCode() == HttpStatus.OK)
            logger.info("Tag successfully added");
        else logger.error("Tag failed to added");
    }
}
