package com.ftn.osa.projekat_osa.exceptions;

public class WrongPasswordException extends Exception {
    public WrongPasswordException() {
        super("Wrong password given");
    }

    public WrongPasswordException(String message) {
        super(message);
    }
}
