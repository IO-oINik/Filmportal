package ru.edu.ui.models.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class PersonRequest {
    @NotNull(message = "Person name is required")
    @NotBlank(message = "Person name can not blank")
    @NotEmpty(message = "Person name can not empty")
    private String name;
    private String surname;
    private String nameForeign;
    private LocalDate dateOfBirth;
    private Long idCountry;
}
