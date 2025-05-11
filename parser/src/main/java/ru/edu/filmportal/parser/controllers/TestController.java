package ru.edu.filmportal.parser.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.edu.filmportal.parser.models.OkkoInfoFilm;
import ru.edu.filmportal.parser.models.ResultParsing;
import ru.edu.filmportal.parser.services.OkkoParserService;

import java.util.List;

@RestController("")
public class TestController {
    private final OkkoParserService service;

    public TestController(OkkoParserService service) {
        this.service = service;
    }

    @GetMapping("")
    public List<ResultParsing> getOkkoInfoFilm() {
        return service.parseInfoFilms("https://okko.tv/movie/palma-2", 5, 10);
    }
}
