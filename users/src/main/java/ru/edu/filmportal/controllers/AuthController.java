package ru.edu.filmportal.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.edu.filmportal.models.request.AuthRequest;
import ru.edu.filmportal.models.request.UserCreateRequest;
import ru.edu.filmportal.models.response.TokensResponse;
import ru.edu.filmportal.services.AuthService;
import ru.edu.filmportal.utils.JwtUtils;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Auth Controller", description = "Контролер для регистрации и получения токенов")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Регистрация нового пользователя")
    public ResponseEntity<TokensResponse> register(@Valid @RequestBody UserCreateRequest userCreateRequest, HttpServletResponse response) {
        TokensResponse tokensResponse = authService.register(userCreateRequest);

        setCookieRefreshToken(response, tokensResponse);
        return ResponseEntity.ok(tokensResponse);
    }

    @PostMapping("/login")
    @Operation(summary = "Получение токенов по учётным данным")
    public ResponseEntity<TokensResponse> login(@Valid @RequestBody AuthRequest authRequest, HttpServletResponse response) {
        TokensResponse tokensResponse = authService.login(authRequest);

        setCookieRefreshToken(response, tokensResponse);
        return ResponseEntity.ok(tokensResponse);
    }

    @GetMapping("/refresh-token")
    @Operation(summary = "Обновление токенов")
    public ResponseEntity<TokensResponse> refresh(@CookieValue(value = "refreshToken") String refreshToken, HttpServletResponse response) {
        TokensResponse tokensResponse = authService.refresh(refreshToken);

        setCookieRefreshToken(response, tokensResponse);
        return ResponseEntity.ok(tokensResponse);
    }

    @GetMapping("/logout")
    @Operation(summary = "Инвалидация токена (выход из системы)")
    public void logout(@CookieValue(value = "refreshToken") String refreshToken) {
        authService.logout(refreshToken);
    }

    private void setCookieRefreshToken(HttpServletResponse response, TokensResponse tokensResponse) {
        Cookie cookie = new Cookie("refreshToken", tokensResponse.refreshToken());
        cookie.setHttpOnly(true);
        cookie.setMaxAge((int) JwtUtils.EXPIRATION_TIME_REFRESH_TOKEN / 1_000);
        response.addCookie(cookie);
    }
}
