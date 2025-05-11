package ru.edu.filmportal.parser.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class OkkoInfoFilm {
    @JsonProperty("name")
    private String name;

    @JsonProperty("alternateName")
    private String alternateName;

    @JsonProperty("description")
    private String description;

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

    private Integer ageLimit;

    public Integer getAgeLimit() {
        return ageLimit;
    }

    public void setAgeLimit(Integer ageLimit) {
        this.ageLimit = ageLimit;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }
}
