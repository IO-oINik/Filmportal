package ru.edu.filmportal.models.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import ru.edu.filmportal.models.database.Role;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

public record UserResponse(
        Long id,
        String name,
        String surname,
        String nickname,
        String email,
        Role role,
        @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
        LocalDateTime dateOfCreation,
        @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
        LocalDateTime dateOfModified
) {
}
