package ru.edu.filmportal.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.edu.filmportal.exceptions.InvalidToken;
import ru.edu.filmportal.models.request.AuthRequest;
import ru.edu.filmportal.models.request.UserCreateRequest;
import ru.edu.filmportal.models.response.UserResponse;
import ru.edu.filmportal.services.AuthService;
import ru.edu.filmportal.utils.JwtUtils;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Auth Controller", description = "Контролер для регистрации и получения токенов")
public class AuthController {
    private final AuthService authService;
    private final JwtUtils jwtUtils;

    @PostMapping("/token/get")
    @Operation(summary = "Получить токен по учетным данным")
    public ResponseEntity<String> getToken(@RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok(authService.getToken(authRequest));
    }

    @PostMapping("/register")
    @Operation(summary = "Регистрация новых пользователей")
    public ResponseEntity<UserResponse> register(@RequestBody UserCreateRequest userCreateRequest) {
        return ResponseEntity.ok(authService.register(userCreateRequest));
    }

    @GetMapping("/token/disable")
    @Operation(summary = "Инвалидация токена (выход из системы)")
    public void disableToken(@RequestHeader("Authorization") String authorizationHeader) {
        String token = jwtUtils.extractJwt(authorizationHeader).orElseThrow(() -> new InvalidToken("Token is missing"));
        authService.disableToken(token);
    }
}
