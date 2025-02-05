package ru.edu.filmportal.services;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.edu.filmportal.exceptions.InvalidCredentialsException;
import ru.edu.filmportal.exceptions.InvalidTokenException;
import ru.edu.filmportal.models.database.RefreshToken;
import ru.edu.filmportal.models.database.User;
import ru.edu.filmportal.models.request.AuthRequest;
import ru.edu.filmportal.models.request.UserCreateRequest;
import ru.edu.filmportal.models.response.TokensResponse;
import ru.edu.filmportal.models.response.UserResponse;
import ru.edu.filmportal.repositories.RefreshTokenRepository;
import ru.edu.filmportal.repositories.UserRepository;
import ru.edu.filmportal.utils.JwtUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtUtils jwtUtils;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;


    public TokensResponse login(AuthRequest authRequest) {
        User user = userRepository.findByNickname(authRequest.nickname()).orElseThrow(() -> new InvalidCredentialsException("Invalid credentials"));
        if(!passwordEncoder.matches(authRequest.password(), user.getHashPassword())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }

        if (user.getToken() != null) {
            RefreshToken token = user.getToken();
            user.setToken(null);
            userRepository.save(user);
            refreshTokenRepository.delete(token);
        }

        return generateTokens(user);
    }

    public TokensResponse refresh(String refreshToken) {
        if (refreshToken == null) {
            throw new InvalidTokenException("Invalid refresh token");
        }
        String username;
        try {
            username = jwtUtils.extractUsername(refreshToken);
        } catch (ExpiredJwtException ex) {
            throw new InvalidTokenException("Token is expired");
        } catch (JwtException ex) {
            throw new InvalidTokenException("Token is invalid");
        }

        RefreshToken token = refreshTokenRepository.findByToken(refreshToken).orElseThrow(() -> new InvalidTokenException("Token is invalid"));
        User user = token.getUser();
        if (!user.getNickname().equals(username)) {
            throw new InvalidTokenException("Token is invalid");
        }
        user.setToken(null);
        userRepository.save(user);
        refreshTokenRepository.delete(token);

        return generateTokens(user);
    }

    public TokensResponse register(UserCreateRequest userCreateRequest) {
        UserResponse userResponse = userService.create(userCreateRequest);
        User user = userRepository.findById(userResponse.id()).get();
        return generateTokens(user);
    }

    public void logout(String refreshToken) {
        if (refreshToken == null) {
            return;
        }

        refreshTokenRepository.findByToken(refreshToken).ifPresent(token -> {
            User user = token.getUser();
            user.setToken(null);
            userRepository.save(user);
            refreshTokenRepository.delete(token);
        });
    }

    private TokensResponse generateTokens(User user) {
        String refreshToken = jwtUtils.generateRefreshToken(user.getNickname());
        LocalDateTime expiresAtRefreshToken = LocalDateTime.ofInstant(
                jwtUtils.extractExpiresAt(refreshToken),
                ZoneId.systemDefault()
        );

        RefreshToken token = refreshTokenRepository.save(new RefreshToken(refreshToken, expiresAtRefreshToken));
        user.setToken(token);
        userRepository.save(user);
        String accessToken = jwtUtils.generateAccessTokenWithRole(user.getNickname(), user.getRole().toString());
        return new TokensResponse(refreshToken, accessToken, jwtUtils.extractExpiresAt(accessToken).toEpochMilli());
    }
}
