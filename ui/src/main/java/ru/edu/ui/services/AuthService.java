package ru.edu.ui.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.edu.ui.clients.AuthClient;
import ru.edu.ui.models.requests.AuthRequest;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthClient authClient;

    public String getToken(String username, String password) {
        return authClient.getToken(new AuthRequest(username, password));
    }

    public void disableToken(String token) {
        authClient.disableToken(token);
    }
}
