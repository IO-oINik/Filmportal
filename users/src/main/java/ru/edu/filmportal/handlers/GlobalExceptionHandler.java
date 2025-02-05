package ru.edu.filmportal.handlers;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.NoHandlerFoundException;
import ru.edu.filmportal.exceptions.AuthException;
import ru.edu.filmportal.exceptions.InvalidCredentialsException;
import ru.edu.filmportal.exceptions.InvalidTokenException;
import ru.edu.filmportal.models.response.MessageObjectNotValidResponse;
import ru.edu.filmportal.models.response.MessageResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({InvalidCredentialsException.class, InvalidTokenException.class, AuthException.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public MessageResponse handleCredentialsException(RuntimeException ex) {
        return new MessageResponse(ex.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageResponse handleEntityNotFoundException(EntityNotFoundException ex) {
        return new MessageResponse(ex.getMessage());
    }

    @ExceptionHandler(EntityExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageResponse handleEntityExistsException(EntityExistsException ex) {
        return new MessageResponse(ex.getMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public MessageResponse handleNoHandlerFoundException(NoHandlerFoundException ex) {
        return new MessageResponse("Not Found");
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
