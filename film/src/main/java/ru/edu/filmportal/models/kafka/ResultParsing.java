package ru.edu.filmportal.models.kafka;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ResultParsing {
    @JsonProperty("url")
    private String url;
    @JsonProperty("infoFilm")
    private InfoFilm infoFilm;
    @JsonProperty("messageError")
    private String messageError;
    @JsonProperty("success")
    private Boolean success;
}
