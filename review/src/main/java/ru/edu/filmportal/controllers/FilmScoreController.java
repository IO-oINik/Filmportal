package ru.edu.filmportal.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import ru.edu.filmportal.services.FilmScoreService;

@RestController("/api/v1/film-score")
@RequiredArgsConstructor
public class FilmScoreController {
    private final FilmScoreService filmScoreService;


}
