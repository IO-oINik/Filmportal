package ru.edu.ui.models.responses;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class GenreResponse {
    private Long id;
    private String title;

    @Override
    public String toString() {
        return title;
    }
}
