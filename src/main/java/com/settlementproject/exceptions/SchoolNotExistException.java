package com.settlementproject.exceptions;

public class SchoolNotExistException extends Exception {

    public SchoolNotExistException() {
        super("This school isn't exist.");
    }

}