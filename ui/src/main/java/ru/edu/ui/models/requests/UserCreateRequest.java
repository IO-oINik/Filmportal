package ru.edu.ui.models.requests;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserCreateRequest {
    private String name;
    private String surname;
    private String nickname;
    private String email;
    private String password;
}
