package com.settlementproject.exceptions;

public class SchoolNotExistException extends Exception {

    public SchoolNotExistException(String message) {
        super("School with this id isn't exist: " + message);
    }

}