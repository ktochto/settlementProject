package com.settlementproject.exceptions;

import lombok.Getter;

@Getter
public enum Exceptions {

    BAD_REQUEST("Bad Request."),
    WRONG_INPUT("Wrong input format."),
    UNAUTHORIZED("Unauthorized."),

    NO_PARENT("This parent isn't exist."),
    PARENT_EXIST("This parent already exist."),
    DATE_EXCEPTION("Date of exit is before date of enter."),

    ;

    private final String message;

    Exceptions(String message) {
        this.message = message;
    }


}