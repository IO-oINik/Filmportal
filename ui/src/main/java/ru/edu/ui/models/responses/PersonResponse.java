package ru.edu.ui.models.responses;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PersonResponse {
    private Long id;
    private String name;
    private String surname;
    private String nameForeign;
    private LocalDate dateOfBirth;
    private CountryResponse countryOfBirth;

    @Override
    public String toString() {
        return String.format("%s %s", name, surname);
    }
}
