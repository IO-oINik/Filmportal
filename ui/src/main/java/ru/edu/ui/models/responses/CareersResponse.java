package ru.edu.ui.models.responses;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CareersResponse {
    private List<String> careers;
}
