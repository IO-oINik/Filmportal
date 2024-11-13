package ru.edu.filmportal.models.response;

import ru.edu.filmportal.models.database.Role;

import java.time.LocalDate;
import java.util.Set;

public record UserResponse(
        Long id,
        String name,
        String surname,
        String nickname,
        String email,
        String hashPassword,
        Role role,
        LocalDate dataOfCreation
) {
}
