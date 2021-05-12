package com.target.app.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception class for Bad request to API
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends Exception{

    /**
     * Throws custom exception message
     * @param exceptionMessage
     */
    public BadRequestException(String exceptionMessage)
    {
        super(exceptionMessage);
    }
}
