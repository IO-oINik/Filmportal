package ru.edu.filmportal.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.edu.filmportal.exceptions.BadRequestException;
import ru.edu.filmportal.exceptions.InvalidTokenException;
import ru.edu.filmportal.exceptions.NotFoundException;
import ru.edu.filmportal.exceptions.UniqueFieldException;
import ru.edu.filmportal.models.responses.MessageObjectNotValidResponse;
import ru.edu.filmportal.models.responses.MessageResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public MessageResponse handle(NotFoundException exp) {
        return new MessageResponse(exp.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageResponse handle(BadRequestException exp) {
        return new MessageResponse(exp.getMessage());
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageObjectNotValidResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        MessageObjectNotValidResponse response = new MessageObjectNotValidResponse("Request body not valid");
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            response.getErrors().put(fieldName, errorMessage);
        });
        return response;
    }

}
