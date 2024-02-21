package com.settlementproject.exceptions;

public class ParentNotExistException extends Exception {

    public ParentNotExistException(String message) {
        super("Parent with this id isn't exist: " + message);
    }

}