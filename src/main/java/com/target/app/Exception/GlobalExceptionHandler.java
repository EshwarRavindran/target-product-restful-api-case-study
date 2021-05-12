package com.target.app.Exception;

import org.springframework.data.cassandra.CassandraConnectionFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponseMessage> resourceNotFoundException(ResourceNotFoundException e, WebRequest request)
    {
        ExceptionResponseMessage exceptionResponseMessage = new ExceptionResponseMessage(e.getMessage(),
                                    LocalDateTime.now(), request.getDescription(false));

        return new ResponseEntity<ExceptionResponseMessage>(exceptionResponseMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CassandraConnectionFailureException.class)
    public ResponseEntity<ExceptionResponseMessage> CassandraConnectionFailureException(CassandraConnectionFailureException e, WebRequest request)
    {
        ExceptionResponseMessage exceptionResponseMessage = new ExceptionResponseMessage("Database is not available",
                LocalDateTime.now(), request.getDescription(false));

        return new ResponseEntity<ExceptionResponseMessage>(exceptionResponseMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionResponseMessage> badRequestException(BadRequestException e, WebRequest request)
    {
        ExceptionResponseMessage exceptionResponseMessage = new ExceptionResponseMessage(e.getMessage(),
                LocalDateTime.now(), request.getDescription(false));

        return new ResponseEntity<ExceptionResponseMessage>(exceptionResponseMessage, HttpStatus.BAD_REQUEST);
    }


}
