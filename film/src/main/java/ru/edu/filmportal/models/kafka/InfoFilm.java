package ru.edu.filmportal.models.kafka;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


import java.util.List;

@Data
public class InfoFilm {
    @JsonProperty("name")
    private String name;

    @JsonProperty("alternateName")
    private String alternateName;

    @JsonProperty("description")
    private String description;

    @JsonProperty("genres")
    private List<String> genres;

    @JsonProperty("actor")
    private List<Person> actors;

    @JsonProperty("director")
    private List<Person> directors;

    @JsonProperty("image")
    private String imageUrl;

    @JsonProperty("aggregateRating")
    private AggregateRating aggregateRating;

    @JsonProperty("url")
    private String url;

    @JsonProperty("countries")
    private List<String> countries;

    @JsonProperty("ageLimit")
    private Integer ageLimit;
}
