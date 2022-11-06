package ru.ashepelev.drones.api.common;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.WebExchangeBindException;

import javax.validation.ValidationException;
import java.util.NoSuchElementException;

import static org.springframework.http.HttpStatus.*;
import static ru.ashepelev.drones.api.common.Response.error;

@ControllerAdvice
public class AdviceController {
    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Response<Object, String>> handleNotFound(NoSuchElementException ex) {
        return new ResponseEntity<>(error(ex.getMessage()), NOT_FOUND);
    }

    @ResponseStatus(BAD_REQUEST)
    @ExceptionHandler({
            IllegalArgumentException.class,
            IllegalStateException.class,
            WebExchangeBindException.class,
            ValidationException.class
    })
    public ResponseEntity<Response<Object, String>> handleBadRequest(RuntimeException ex) {
        return new ResponseEntity<>(error(ex.getMessage()), BAD_REQUEST);
    }

    @ResponseStatus(INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response<Object, String>> handleException(Exception ex) {
        return new ResponseEntity<>(error(ex.getMessage()), INTERNAL_SERVER_ERROR);
    }
}
