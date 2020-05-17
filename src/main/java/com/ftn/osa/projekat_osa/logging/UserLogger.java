package com.ftn.osa.projekat_osa.logging;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserLogger {

    @AfterReturning("execution(* com..projekat_osa.controller.RegisterController.*(..))")
    public void logAfterNewUserRegister(JoinPoint joinPoint)
    {
        Logger logger = LogManager.getLogger(this.getClass().getName());
        logger.info("User registered");
        logger.error("dada");

    }
}
