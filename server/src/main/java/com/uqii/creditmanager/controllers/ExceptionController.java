package com.uqii.creditmanager.controllers;

import com.uqii.creditmanager.utils.ResponseError;
import java.util.NoSuchElementException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Log4j2
@RestControllerAdvice
public class ExceptionController {

  @ExceptionHandler
  public ResponseEntity<ResponseError> handleException(IllegalArgumentException exception) {
    HttpStatus status = HttpStatus.BAD_REQUEST;
    ResponseError responseError = new ResponseError(status, exception.getMessage());
    log.error(exception.getMessage());
    return ResponseEntity
        .status(status)
        .body(responseError);
  }

  @ExceptionHandler
  public ResponseEntity<ResponseError> handleException(NoSuchElementException exception) {
    HttpStatus status = HttpStatus.NOT_FOUND;
    ResponseError responseError = new ResponseError(status, exception.getMessage());
    log.error(exception.getMessage());
    return ResponseEntity
        .status(status)
        .body(responseError);
  }
}
