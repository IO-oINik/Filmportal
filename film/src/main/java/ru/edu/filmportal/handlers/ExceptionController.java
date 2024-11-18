package ru.edu.filmportal.handlers;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.edu.filmportal.exceptions.InvalidTokenException;
import ru.edu.filmportal.exceptions.UniqueFieldException;
import ru.edu.filmportal.models.responses.MessageResponse;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<MessageResponse> handle(EntityNotFoundException exp) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new MessageResponse(exp.getMessage()));
    }

    @ExceptionHandler(UniqueFieldException.class)
    public ResponseEntity<MessageResponse> handle(UniqueFieldException exp) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new MessageResponse(exp.getMessage()));
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<MessageResponse> handle(InvalidTokenException exp) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new MessageResponse(exp.getMessage()));
    }
}
