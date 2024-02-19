package com.settlementproject.exceptions;

public class WrongDateInpuException extends Exception {

    public WrongDateInpuException() {
        super("Wrong date input format");
    }
}