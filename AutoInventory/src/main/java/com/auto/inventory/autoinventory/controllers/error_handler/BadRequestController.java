package com.auto.inventory.autoinventory.controllers.error_handler;

import com.auto.inventory.autoinventory.models.dtos.ErrorDTO;
import com.auto.inventory.autoinventory.util.exceptions.IdNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestController {

    @ExceptionHandler(IdNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleIdNotFound(IdNotFoundException exception){
        ErrorDTO err = new ErrorDTO();
        err.setDate(new Date());
        err.setMessage(exception.getMessage());
        err.setStatus(HttpStatus.BAD_REQUEST.name());
        err.setCode(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(err);


    }

}
