package com.example.exceptions;

import java.time.LocalDateTime;

public class MyException extends RuntimeException {
    private String errorMessage;
    private LocalDateTime errorDate;

    public MyException(String errorMessage, LocalDateTime errorDate) {
        this.errorMessage = errorMessage;
        this.errorDate = errorDate;
    }

    @Override
    public String getMessage() {
        return errorMessage + " " + errorDate;
    }
}