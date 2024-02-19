package com.settlementproject.exceptions;

public class ParentNotExistException extends Exception {

    public ParentNotExistException() {
        super("This parent isn't exist.");
    }

}