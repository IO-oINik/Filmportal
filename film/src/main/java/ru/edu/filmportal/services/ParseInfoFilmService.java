package ru.edu.filmportal.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.edu.filmportal.exceptions.ParseInfoFilmException;
import ru.edu.filmportal.models.database.AgeLimit;
import ru.edu.filmportal.models.database.Country;
import ru.edu.filmportal.models.database.Film;
import ru.edu.filmportal.models.database.Genre;
import ru.edu.filmportal.models.database.ParseInfoFilm;
import ru.edu.filmportal.models.kafka.InfoFilm;
import ru.edu.filmportal.models.kafka.ResultParsing;
import ru.edu.filmportal.repositories.AgeLimitRepository;
import ru.edu.filmportal.repositories.CountryRepository;
import ru.edu.filmportal.repositories.FilmRepository;
import ru.edu.filmportal.repositories.GenreRepository;
import ru.edu.filmportal.repositories.ParseInfoFilmRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ParseInfoFilmService {
    private final ParseInfoFilmRepository parseInfoFilmRepository;
    private final AgeLimitRepository ageLimitRepository;
    private final CountryRepository countryRepository;
    private final FilmRepository filmRepository;
    private final GenreRepository genreRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper mapper;

    public void sendUrl(String url) {
        kafkaTemplate.send("okko-parse-url", url);
    }

    @KafkaListener(topics = "info-film", groupId = "film-service")
    public void listen(String message) {
        ResultParsing resultParsing;
        try {
            resultParsing = mapper.readValue(message, ResultParsing.class);
        } catch (JsonProcessingException e) {
            throw new ParseInfoFilmException(e.getMessage());
        }

        ParseInfoFilm info = new ParseInfoFilm();
        try {
            info.setJson(mapper.writeValueAsString(resultParsing));
            parseInfoFilmRepository.save(info);
        } catch (JsonProcessingException | RuntimeException e) {
            log.error(e.getMessage());
        }
    }

    public List<ResultParsing> findResultParsingAll() {
        List<ParseInfoFilm> list = parseInfoFilmRepository.findAll();
        List<ResultParsing> resultParsingList = new ArrayList<>();
        try {
            for (ParseInfoFilm parseInfoFilm : list) {
                resultParsingList.add(mapper.readValue(parseInfoFilm.getJson(), ResultParsing.class));
            }
        } catch (JsonProcessingException e) {
            throw new ParseInfoFilmException(e.getMessage());
        }
        return resultParsingList;
    }

    public ResultParsing findResultParsingById(Long id) {
        ParseInfoFilm parseInfoFilm = parseInfoFilmRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Result parsing not found"));
        try {
            return mapper.readValue(parseInfoFilm.getJson(), ResultParsing.class);
        } catch (JsonProcessingException e) {
            throw new ParseInfoFilmException(e.getMessage());
        }
    }

    public void saveResultParsingToDatabase(Long id) {
        ParseInfoFilm parseInfoFilm = parseInfoFilmRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Result parsing not found"));
        ResultParsing resultParsing;
        try {
            resultParsing = mapper.readValue(parseInfoFilm.getJson(), ResultParsing.class);
        } catch (JsonProcessingException e) {
            throw new ParseInfoFilmException(e.getMessage());
        }

        if (!resultParsing.getSuccess()) {
            throw new ParseInfoFilmException("This data cannot be saved");
        }

        InfoFilm info = resultParsing.getInfoFilm();

        AgeLimit ageLimit;
        List<Country> countries = new ArrayList<>();
        List<Genre> genres = new ArrayList<>();

        // try get or save ageLimit
        Integer age = info.getAgeLimit();
        Optional<AgeLimit> optionalAgeLimit = ageLimitRepository.findByAge(age);
        ageLimit = optionalAgeLimit.orElseGet(() -> ageLimitRepository.save(new AgeLimit(age)));

        // try get or save countries
/*        List<String> parseCountries = info.getCountries();
        for (String country : parseCountries) {
            Optional<Country> optionalCountry = countryRepository.findByTitle(country);
            countries.add(optionalCountry.orElseGet(() -> countryRepository.save(new Country(country))));
        }*/

        // try get or save genres
        List<String> parseGenres = info.getGenres();
        for (String genre : parseGenres) {
            Optional<Genre> optionalGenre = genreRepository.findByTitle(genre);
            genres.add(optionalGenre.orElseGet(() -> genreRepository.save(new Genre(genre))));
        }

        Film newFilm = new Film();
        newFilm.setTitle(info.getName());
        newFilm.setTitleForeign(info.getAlternateName());
        newFilm.setDescription(info.getDescription());
        newFilm.setAgeLimit(ageLimit);
//        newFilm.setCountries(countries);
        newFilm.setGenres(genres);

        filmRepository.save(newFilm);
    }
}
