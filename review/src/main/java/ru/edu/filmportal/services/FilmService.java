package ru.edu.filmportal.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.edu.filmportal.clients.FilmClient;
import ru.edu.filmportal.exceptions.AuthException;
import ru.edu.filmportal.models.responses.FilmResponse;

@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmClient filmClient;

    public FilmResponse findById(long id) {
        return filmClient.findById("Bearer " + getCurrentAccessToken(), id);
    }

    private String getCurrentAccessToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return (String) authentication.getCredentials();
        } else {
            throw new AuthException("Unauthorized");
        }
    }
}
