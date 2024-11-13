package ru.edu.ui.models.requests;

import lombok.*;
import ru.edu.ui.models.Role;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserRequest {
    private String name;
    private String surname;
    private String nickname;
    private String email;
    private String hashPassword;
    private Role role;
}
