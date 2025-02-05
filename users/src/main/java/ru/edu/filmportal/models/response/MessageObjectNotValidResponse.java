package ru.edu.filmportal.models.response;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class MessageObjectNotValidResponse {
    String message;
    Map<String, String> errors;

    public MessageObjectNotValidResponse(String message) {
        this.message = message;
        errors = new HashMap<>();
    }
}
