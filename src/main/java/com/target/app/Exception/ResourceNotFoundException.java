package com.target.app.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception class to throw when no matching resource is
 * found in the External API or DB
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends Exception{

    /**
     * Throws custom exception message
     * @param exceptionMessage
     */
    public ResourceNotFoundException(String exceptionMessage)
    {
        super(exceptionMessage);
    }
}
