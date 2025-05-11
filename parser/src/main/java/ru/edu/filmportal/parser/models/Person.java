package ru.edu.filmportal.parser.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Person {
    @JsonProperty("name")
    private String name;
    @JsonProperty("url")
    private String url;
}
