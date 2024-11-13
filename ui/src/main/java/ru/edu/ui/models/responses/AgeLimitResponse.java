package ru.edu.ui.models.responses;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class AgeLimitResponse {
    private Long id;
    private Integer age;

    @Override
    public String toString() {
        return String.format("%d+", age);
    }
}
