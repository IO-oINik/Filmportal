package ru.edu.filmportal.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.stereotype.Service;
import ru.edu.filmportal.exceptions.BadRequestException;
import ru.edu.filmportal.exceptions.NotFoundException;
import ru.edu.filmportal.mappers.FilmMapper;
import ru.edu.filmportal.models.database.Film;
import ru.edu.filmportal.models.requests.FilmCreateRequest;
import ru.edu.filmportal.models.requests.FilmEditRequest;
import ru.edu.filmportal.models.responses.FilmResponse;
import ru.edu.filmportal.models.responses.PageableDataResponse;
import ru.edu.filmportal.repositories.*;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmService implements EntityService<FilmResponse, FilmCreateRequest, FilmEditRequest> {
    private final FilmRepository filmRepository;
    private final FilmMapper filmMapper;
    private final GenreRepository genreRepository;
    private final CountryRepository countryRepository;
    private final AgeLimitRepository ageLimitRepository;
    private final PersonRepository personRepository;

    @Override
    public FilmResponse findById(long id) {
        return filmRepository.findById(id)
                .map(filmMapper::toFilmResponse)
                .orElseThrow(() -> new NotFoundException("Film not found with the ID: " + id));
    }

    @Override
    public List<FilmResponse> findAll() {
        return filmRepository.findAll().stream().map(filmMapper::toFilmResponse).toList();
    }

    public PageableDataResponse<FilmResponse> findAllWithParameters(int page, int pageSize, String sort, String order) {
        if (order == null || order.isBlank() || (!order.equalsIgnoreCase("asc") && !order.equalsIgnoreCase("desc"))) {
            throw new BadRequestException("Invalid sort order: " + order);
        }
        if (page < 1) {
            throw new BadRequestException("page must be larger than 1");
        }
        if (pageSize < 1) {
            throw new BadRequestException("page size must be larger than 1");
        }

        Sort.Direction direction;
        if (order.equalsIgnoreCase("asc")) {
            direction = Sort.Direction.ASC;
        } else {
            direction = Sort.Direction.DESC;
        }
        var pageRequest = PageRequest.of(page - 1, pageSize, Sort.by(direction, sort));

        Page<Film> result;
        try {
            result = filmRepository.findAll(pageRequest);
        } catch (PropertyReferenceException ex) {
            throw new BadRequestException("Invalid parameter sort: " + sort);
        }
        long totalElements = result.getTotalElements();
        List<FilmResponse> filmResponses = result.map(filmMapper::toFilmResponse).toList();
        return new PageableDataResponse<>(filmResponses, totalElements);
    }

    @Override
    public FilmResponse create(FilmCreateRequest request) {
        var film = filmMapper.toFilm(request);
        if (request.ageLimitId() != null) {
            var ageLimit = ageLimitRepository.findById(request.ageLimitId()).orElseThrow();
            film.setAgeLimit(ageLimit);
        }
        if (request.countriesId() != null) {
            var countries = countryRepository.findAllById(request.countriesId());
            film.setCountries(countries);
        }
        if (request.genresId() != null) {
            var genres = genreRepository.findAllById(request.genresId());
            film.setGenres(genres);
        }
        if (request.directorsId() != null) {
            var directors = personRepository.findAllById(request.directorsId());
            film.setDirectors(directors);
        }
        if (request.screenwritersId() != null) {
            var screenwriters = personRepository.findAllById(request.screenwritersId());
            film.setScreenwriters(screenwriters);
        }
        if(request.producersId() != null) {
            var producers = personRepository.findAllById(request.producersId());
            film.setProducers(producers);
        }
        if (request.actorsId() != null) {
            var actors = personRepository.findAllById(request.actorsId());
            film.setActors(actors);
        }
        film = filmRepository.save(film);

        return filmMapper.toFilmResponse(film);
    }

    @Override
    public FilmResponse update(long id, FilmEditRequest request) {
        var film = filmRepository.findById(id).orElseThrow(() -> new NotFoundException("Film not found with the ID: " + id));

        if(request.title() != null && !request.title().isBlank()) {
            film.setTitle(request.title());
        }
        if(request.titleForeign() != null && !request.titleForeign().isBlank()) {
            film.setTitleForeign(request.titleForeign());
        }
        if(request.description() != null && !request.description().isBlank()) {
            film.setDescription(request.description());
        }
        if(request.slogan() != null && !request.slogan().isBlank()) {
            film.setSlogan(request.slogan());
        }
        if(request.yearOfProduction() != null) {
            film.setYearOfProduction(request.yearOfProduction());
        }
        if(request.releaseDateInWorld() != null) {
            film.setReleaseDateInWorld(request.releaseDateInWorld());
        }
        if(request.releaseDateInRussia() != null) {
            film.setReleaseDateInRussia(request.releaseDateInRussia());
        }
        if(request.budget() != null) {
            film.setBudget(request.budget());
        }
        if(request.durationInSeconds() != null) {
            film.setDurationInSeconds(request.durationInSeconds());
        }
        if(request.ageLimitId() != null) {
            var ageLimit = ageLimitRepository.findById(request.ageLimitId()).orElseThrow(() -> new NotFoundException("AgeLimit not found with the ID: " + id));
            film.setAgeLimit(ageLimit);
        }
        if (request.countriesId() != null) {
            var countries = countryRepository.findAllById(request.countriesId());
            film.setCountries(countries);
        }
        if (request.genresId() != null) {
            var genres = genreRepository.findAllById(request.genresId());
            film.setGenres(genres);
        }
        if (request.directorsId() != null) {
            var directors = personRepository.findAllById(request.directorsId());
            film.setDirectors(directors);
        }
        if (request.screenwritersId() != null) {
            var screenwriters = personRepository.findAllById(request.screenwritersId());
            film.setScreenwriters(screenwriters);
        }
        if(request.producersId() != null) {
            var producers = personRepository.findAllById(request.producersId());
            film.setProducers(producers);
        }
        if (request.actorsId() != null) {
            var actors = personRepository.findAllById(request.actorsId());
            film.setActors(actors);
        }

        film = filmRepository.save(film);
        return filmMapper.toFilmResponse(film);
    }

    public FilmResponse addCountry(long idFilm, long idCountry) {
        var film = filmRepository.findById(idFilm).orElseThrow(() -> new NotFoundException("Film not found with the ID: " + idFilm));
        var country = countryRepository.findById(idCountry).orElseThrow(() -> new NotFoundException("Country not found with the ID: " + idCountry));

        film.getCountries().add(country);
        film = filmRepository.save(film);
        return filmMapper.toFilmResponse(film);
    }

    public FilmResponse deleteCountry(long idFilm, long idCountry) {
        var film = filmRepository.findById(idFilm).orElseThrow(() -> new NotFoundException("Film not found with the ID: " + idFilm));
        var country = countryRepository.findById(idCountry).orElseThrow(() -> new NotFoundException("Country not found with the ID: " + idCountry));
        film.getCountries().remove(country);

        filmRepository.save(film);
        return filmMapper.toFilmResponse(film);
    }

    public FilmResponse addGenre(long idFilm, long idGenre) {
        var film = filmRepository.findById(idFilm).orElseThrow(() -> new NotFoundException("Film not found with the ID: " + idFilm));
        var genre = genreRepository.findById(idGenre).orElseThrow(() -> new NotFoundException("Genre not found with the ID: " + idGenre));

        film.getGenres().add(genre);
        film = filmRepository.save(film);
        return filmMapper.toFilmResponse(film);
    }

    public FilmResponse deleteGenre(long idFilm, long idGenre) {
        var film = filmRepository.findById(idFilm).orElseThrow(() -> new NotFoundException("Film not found with the ID: " + idFilm));
        var genre = genreRepository.findById(idGenre).orElseThrow(() -> new NotFoundException("Genre not found with the ID: " + idGenre));
        film.getGenres().remove(genre);

        filmRepository.save(film);
        return filmMapper.toFilmResponse(film);
    }

    public FilmResponse addDirector(long idFilm, long idDirector) {
        var film = filmRepository.findById(idFilm).orElseThrow(() -> new NotFoundException("Film not found with the ID: " + idFilm));
        var director = personRepository.findById(idDirector).orElseThrow(() -> new NotFoundException("Director not found with the ID: " + idDirector));

        film.getDirectors().add(director);
        film = filmRepository.save(film);
        return filmMapper.toFilmResponse(film);
    }

    public FilmResponse deleteDirector(long idFilm, long idDirector) {
        var film = filmRepository.findById(idFilm).orElseThrow(() -> new NotFoundException("Film not found with the ID: " + idFilm));
        var director = personRepository.findById(idDirector).orElseThrow(() -> new NotFoundException("Director not found with the ID: " + idDirector));
        film.getDirectors().remove(director);

        filmRepository.save(film);
        return filmMapper.toFilmResponse(film);
    }

    public FilmResponse addScreenwriter(long idFilm, long idScreenwriter) {
        var film = filmRepository.findById(idFilm).orElseThrow(() -> new NotFoundException("Film not found with the ID: " + idFilm));
        var screenwriter = personRepository.findById(idScreenwriter).orElseThrow(() -> new NotFoundException("Screenwriter not found with the ID: " + idScreenwriter));

        film.getScreenwriters().add(screenwriter);
        film = filmRepository.save(film);
        return filmMapper.toFilmResponse(film);
    }

    public FilmResponse deleteScreenwriter(long idFilm, long idScreenwriter) {
        var film = filmRepository.findById(idFilm).orElseThrow(() -> new NotFoundException("Film not found with the ID: " + idFilm));
        var screenwriter = personRepository.findById(idScreenwriter).orElseThrow(() -> new NotFoundException("Screenwriter not found with the ID: " + idScreenwriter));
        film.getScreenwriters().remove(screenwriter);

        filmRepository.save(film);
        return filmMapper.toFilmResponse(film);
    }

    public FilmResponse addProducer(long idFilm, long idProducer) {
        var film = filmRepository.findById(idFilm).orElseThrow(() -> new NotFoundException("Film not found with the ID: " + idFilm));
        var producer = personRepository.findById(idProducer).orElseThrow(() -> new NotFoundException("Producer not found with the ID: " + idProducer));

        film.getProducers().add(producer);
        film = filmRepository.save(film);
        return filmMapper.toFilmResponse(film);
    }

    public FilmResponse deleteProducer(long idFilm, long idProducer) {
        var film = filmRepository.findById(idFilm).orElseThrow(() -> new NotFoundException("Film not found with the ID: " + idFilm));
        var producer = personRepository.findById(idProducer).orElseThrow(() -> new NotFoundException("Producer not found with the ID: " + idProducer));
        film.getProducers().remove(producer);

        filmRepository.save(film);
        return filmMapper.toFilmResponse(film);
    }

    public FilmResponse addActor(long idFilm, long idActor) {
        var film = filmRepository.findById(idFilm).orElseThrow(() -> new NotFoundException("Film not found with the ID: " + idFilm));
        var actor = personRepository.findById(idActor).orElseThrow(() -> new NotFoundException("Actor not found with the ID: " + idActor));

        film.getActors().add(actor);
        film = filmRepository.save(film);
        return filmMapper.toFilmResponse(film);
    }

    public FilmResponse deleteActor(long idFilm, long idActor) {
        var film = filmRepository.findById(idFilm).orElseThrow(() -> new NotFoundException("Film not found with the ID: " + idFilm));
        var actor = personRepository.findById(idActor).orElseThrow(() -> new NotFoundException("Actor not found with the ID: " + idActor));

        film.getActors().remove(actor);
        filmRepository.save(film);
        return filmMapper.toFilmResponse(film);
    }

    @Override
    public void delete(long id) {
        filmRepository.deleteById(id);
    }
}
