package ru.edu.filmportal.handlers;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.NoHandlerFoundException;
import ru.edu.filmportal.exceptions.InvalidCredentialsException;
import ru.edu.filmportal.exceptions.InvalidToken;
import ru.edu.filmportal.models.response.MessageResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

    @ExceptionHandler({InvalidCredentialsException.class, InvalidToken.class})
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public MessageResponse handleCredentialsException(RuntimeException ex) {
        return new MessageResponse(ex.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageResponse handleEntityNotFoundException(EntityNotFoundException ex) {
        return new MessageResponse(ex.getMessage());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public MessageResponse handleNoHandlerFoundException(NoHandlerFoundException ex) {
        return new MessageResponse("Not Found");
    }
}
