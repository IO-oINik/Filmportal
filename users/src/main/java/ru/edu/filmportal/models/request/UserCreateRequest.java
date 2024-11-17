package ru.edu.filmportal.models.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record UserCreateRequest(
        @NotNull(message = "User name is required")
        @NotBlank(message = "User name can not blank")
        @NotEmpty(message = "User name can not empty")
        String name,
        @NotNull(message = "User surname is required")
        @NotBlank(message = "User surname can not blank")
        @NotEmpty(message = "User surname can not empty")
        String surname,
        @NotNull(message = "User nickname is required")
        @NotBlank(message = "User nickname can not blank")
        @NotEmpty(message = "User nickname can not empty")
        String nickname,
        @NotNull(message = "User email is required")
        @NotBlank(message = "User email can not blank")
        @NotEmpty(message = "User email can not empty")
        @Email(message = "User email is incorrect")
        String email,
        @NotNull(message = "User password is required")
        @NotBlank(message = "User password can not blank")
        @NotEmpty(message = "User password can not empty")
        String password
) {
}
