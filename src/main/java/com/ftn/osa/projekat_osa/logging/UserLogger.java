package com.ftn.osa.projekat_osa.logging;

import com.ftn.osa.projekat_osa.android_dto.MessageDTO;
import com.ftn.osa.projekat_osa.android_dto.UserDTO;
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

    @AfterReturning(value = "execution(* com..projekat_osa.controller.MessageController.moveMessage(..))")
    public void logAfterMoveMessage(JoinPoint joinPoint, ResponseEntity<MessageDTO> returnValue){
        if (returnValue.getStatusCode() == HttpStatus.OK)
            logger.info("Message successfully moved");
        else
            logger.error("Message move failed");

    }
}
