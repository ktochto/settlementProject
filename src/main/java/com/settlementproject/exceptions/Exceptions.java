package com.settlementproject.exceptions;

import lombok.Getter;

@Getter
public enum Exceptions {

    BAD_REQUEST("Bad Request."),
    INTERNAL_SERVER_ERROR("Internal Server Error"),
    WRONG_INPUT("Wrong date input format."),
    UNAUTHORIZED("Unauthorized."),

    NO_PARENT("Parent with this id isn't exist."),
    PARENT_EXIST("Parent with this id already exist."),

    DATE_EXCEPTION("Date of exit is before date of enter."),

    NO_SCHOOL("School with this id isn't exist."),

    ;

    private final String message;

    Exceptions(String message) {
        this.message = message;
    }


}