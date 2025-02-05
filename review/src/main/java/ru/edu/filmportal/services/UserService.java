package ru.edu.filmportal.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.edu.filmportal.clients.UserClient;
import ru.edu.filmportal.exceptions.AuthException;
import ru.edu.filmportal.models.responses.UserResponse;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserClient userClient;

    public UserResponse findMe() {
        return userClient.findMe("Bearer " + getCurrentAccessToken());
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
