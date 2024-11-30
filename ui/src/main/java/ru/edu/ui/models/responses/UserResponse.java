package ru.edu.ui.models.responses;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserResponse {
    private Long id;
    private String name;
    private String surname;
    private String nickname;
    private String email;
    private String role;
}
