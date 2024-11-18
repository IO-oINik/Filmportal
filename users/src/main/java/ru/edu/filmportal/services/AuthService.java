package ru.edu.filmportal.services;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.edu.filmportal.exceptions.InvalidCredentialsException;
import ru.edu.filmportal.models.database.Token;
import ru.edu.filmportal.models.database.User;
import ru.edu.filmportal.models.request.AuthRequest;
import ru.edu.filmportal.models.request.UserCreateRequest;
import ru.edu.filmportal.models.response.UserResponse;
import ru.edu.filmportal.repositories.TokenRepository;
import ru.edu.filmportal.repositories.UserRepository;
import ru.edu.filmportal.utils.JwtUtils;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtUtils jwtUtils;
    private final TokenRepository tokenRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;


    public String getToken(AuthRequest authRequest) {
        User user = userRepository.findByNickname(authRequest.nickname()).orElseThrow(() -> new InvalidCredentialsException("Invalid credentials"));
        if(!passwordEncoder.matches(authRequest.password(), user.getHashPassword())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }

        String tokenString = "";
        try {
            if (user.getToken() != null && jwtUtils.extractUsername(user.getToken().getToken()).equals(user.getNickname())) {
                tokenString = user.getToken().getToken();
            } else {
                tokenString = jwtUtils.generateToken(user.getNickname(), Collections.singletonMap("ROLE", user.getRole().toString()));
                Token token = tokenRepository.save(new Token(tokenString));

                user.setToken(token);
                userRepository.save(user);
            }
        } catch (ExpiredJwtException e) {
            tokenString = jwtUtils.generateToken(user.getNickname(), Collections.singletonMap("ROLE", user.getRole().toString()));
            Token token = tokenRepository.save(new Token(tokenString));

            user.setToken(token);
            userRepository.save(user);
        }

        return tokenString;
    }

    public UserResponse register(UserCreateRequest userCreateRequest) {
        return userService.create(userCreateRequest);
    }

    public void disableToken(String token) {
        tokenRepository.findByToken(token).ifPresent(tokenRepository::delete);
    }
}
