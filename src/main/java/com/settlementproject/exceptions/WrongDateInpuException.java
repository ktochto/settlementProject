package com.settlementproject.exceptions;

public class WrongDateInpuException extends Exception {

    public WrongDateInpuException(String message) {
        super("Wrong date input format. The input is " + message);
    }
}