package ru.edu.ui.models.requests;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PersonRequest {
    private String name;
    private String surname;
    private String nameForeign;
    private LocalDate dateOfBirth;
    private Long idCountry;
}
