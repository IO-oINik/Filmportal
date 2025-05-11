package ru.edu.filmportal.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.edu.filmportal.models.kafka.ResultParsing;
import ru.edu.filmportal.models.requests.ParseUrlRequest;
import ru.edu.filmportal.services.ParseInfoFilmService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/parse-info-film")
@RequiredArgsConstructor
public class ParseInfoFilmController {
    private final ParseInfoFilmService parseInfoFilmService;

    @PostMapping("/okko")
    public void sendUrl(@RequestBody @Valid ParseUrlRequest request) {
        parseInfoFilmService.sendUrl(request.url());
    }

    @GetMapping("/result/all")
    public List<ResultParsing> findResultParsingAll() {
        return parseInfoFilmService.findResultParsingAll();
    }

    @GetMapping("/result/find")
    public ResultParsing findResultParsing(@RequestParam long id) {
        return parseInfoFilmService.findResultParsingById(id);
    }

    @GetMapping("/result/save")
    public void saveResultParsing(@RequestParam long id) {
        parseInfoFilmService.saveResultParsingToDatabase(id);
    }
}
