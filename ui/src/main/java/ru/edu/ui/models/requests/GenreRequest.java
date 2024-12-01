package ru.edu.ui.models.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GenreRequest {
    @NotNull(message = "Genre title is required")
    @NotBlank(message = "Genre title can not blank")
    @NotEmpty(message = "Genre title can not empty")
    private String title;
}
