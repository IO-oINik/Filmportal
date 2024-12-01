package ru.edu.ui.models.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CountryRequest {
    @NotNull(message = "Country title is required")
    @NotBlank(message = "Country title can not blank")
    @NotEmpty(message = "Country title can not empty")
    private String title;
}
