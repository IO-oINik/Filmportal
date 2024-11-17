package ru.edu.filmportal.models.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record AuthRequest(
        @NotNull(message = "User nickname is required")
        @NotBlank(message = "User nickname can not blank")
        @NotEmpty(message = "User nickname can not empty")
        String nickname,
        @NotNull(message = "User password is required")
        @NotBlank(message = "User password can not blank")
        @NotEmpty(message = "User password can not empty")
        String password
) {}
