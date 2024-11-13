package ru.edu.filmportal.controllers;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.edu.filmportal.models.responses.MessageResponse;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler({EntityNotFoundException.class, EntityExistsException.class})
    public ResponseEntity<MessageResponse> handle(EntityNotFoundException exp) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new MessageResponse(exp.getMessage()));
    }
}