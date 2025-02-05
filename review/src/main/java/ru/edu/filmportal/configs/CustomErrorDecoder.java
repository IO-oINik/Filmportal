package ru.edu.filmportal.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import ru.edu.filmportal.exceptions.AuthException;
import ru.edu.filmportal.exceptions.BadRequestException;
import ru.edu.filmportal.exceptions.NotFoundException;

import java.io.IOException;
import java.io.InputStream;

@Component
public class CustomErrorDecoder implements ErrorDecoder {
    @Setter
    @Getter
    static class ErrorResponse {
        private String message;

    }
    private final ErrorDecoder errorDecoder = new Default();

    @Override
    public Exception decode(String s, Response response) {
        ErrorResponse errorResponse;
        try (InputStream stream = response.body().asInputStream()) {
            ObjectMapper objectMapper = new ObjectMapper();
            errorResponse = objectMapper.readValue(stream, ErrorResponse.class);

        } catch (IOException | NullPointerException e) {
            errorResponse = new ErrorResponse();
            errorResponse.message = null;
        }

        return switch (response.status()) {
            case 400 -> new BadRequestException(errorResponse.message != null ? errorResponse.message : "Bad request");
            case 404 -> new NotFoundException(errorResponse.message != null ? errorResponse.message : "Not found");
            case 401 -> new AuthException(errorResponse.message != null ? errorResponse.message : "Unauthorized");
            case 403 -> new AuthException(errorResponse.message != null ? errorResponse.message : "Forbidden");
            default -> errorDecoder.decode(s, response);
        };
    }
}
