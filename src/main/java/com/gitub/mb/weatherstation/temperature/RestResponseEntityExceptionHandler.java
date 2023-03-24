package com.gitub.mb.weatherstation.temperature;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;
@RestControllerAdvice
public class RestResponseEntityExceptionHandler {

    @ResponseBody
    @ExceptionHandler(NoSuchElementException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String clientDataNotFoundHandler(NoSuchElementException ex) {
        return ex.getMessage();
    }
}
