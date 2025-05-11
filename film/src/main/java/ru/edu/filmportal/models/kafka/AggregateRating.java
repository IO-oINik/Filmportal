package ru.edu.filmportal.models.kafka;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AggregateRating {
    @JsonProperty("ratingValue")
    private Double ratingValue;
}
