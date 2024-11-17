package ru.edu.filmportal.models.request;

import ru.edu.filmportal.models.database.Role;


public record UserEditRequest(
        String name,
        String surname,
        String nickname,
        String email
) {
}
