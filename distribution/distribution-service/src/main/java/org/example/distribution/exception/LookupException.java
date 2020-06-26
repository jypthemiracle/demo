package org.example.distribution.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class LookupException extends RuntimeException {
    private String message;

    public LookupException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
