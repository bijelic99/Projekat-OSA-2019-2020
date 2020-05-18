package com.ftn.osa.projekat_osa.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserLogger {

    Logger logger = LoggerFactory.getLogger(UserLogger.class);

    @AfterReturning("execution(* com..projekat_osa.controller.RegisterController.*(..))")
    public void logAfterNewUserRegister(JoinPoint joinPoint)
    {

        logger.info("User registered");
        logger.error("dada");

    }
}
