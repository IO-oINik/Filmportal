package ru.edu.ui.services;

import com.vaadin.flow.server.VaadinSession;
import feign.FeignException;
import ru.edu.ui.exceptions.BadRequestException;
import ru.edu.ui.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudOperationException;
import ru.edu.ui.clients.FilmClient;
import ru.edu.ui.exceptions.AuthException;
import ru.edu.ui.models.requests.FilmRequest;
import ru.edu.ui.models.responses.CountryResponse;
import ru.edu.ui.models.responses.FilmResponse;
import ru.edu.ui.models.responses.GenreResponse;
import ru.edu.ui.models.responses.PersonResponse;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmClient filmClient;

    public List<FilmResponse> findAll() {
        try {
            return filmClient.findAll();
        } catch (AuthException e) {
            throw new CrudOperationException("Войдите или зарегистрируйтесь");
        } catch (NotFoundException e) {
            throw new CrudOperationException("Страница не найдена");
        } catch (BadRequestException e) {
            throw new CrudOperationException(e.getMessage());
        } catch (FeignException e) {
            throw new CrudOperationException("Ошибка сервера");
        }
    }

    public FilmResponse create(FilmResponse filmResponse) {
        Long ageLimitId = null;
        if(filmResponse.getAgeLimit() != null) {
            ageLimitId = filmResponse.getAgeLimit().getId();
        }
        var filmRequest = new FilmRequest(
                filmResponse.getTitle(),
                filmResponse.getTitleForeign(),
                filmResponse.getDescription(),
                filmResponse.getSlogan(),
                filmResponse.getYearOfProduction(),
                filmResponse.getReleaseDateInWorld(),
                filmResponse.getReleaseDateInRussia(),
                filmResponse.getBudget(),
                filmResponse.getDurationInSeconds(),
                ageLimitId,
                filmResponse.getCountries().stream().mapToLong(CountryResponse::getId).boxed().toList(),
                filmResponse.getGenres().stream().mapToLong(GenreResponse::getId).boxed().toList(),
                filmResponse.getDirectors().stream().mapToLong(PersonResponse::getId).boxed().toList(),
                filmResponse.getScreenwriters().stream().mapToLong(PersonResponse::getId).boxed().toList(),
                filmResponse.getProducers().stream().mapToLong(PersonResponse::getId).boxed().toList(),
                filmResponse.getActors().stream().mapToLong(PersonResponse::getId).boxed().toList());
        String token = (String) VaadinSession.getCurrent().getAttribute("token");
        try {
            return filmClient.create(filmRequest, "Bearer " + token);
        } catch (AuthException e) {
            throw new CrudOperationException("Войдите или зарегистрируйтесь");
        } catch (NotFoundException e) {
            throw new CrudOperationException("Страница не найдена");
        } catch (BadRequestException e) {
            throw new CrudOperationException(e.getMessage());
        } catch (FeignException e) {
            throw new CrudOperationException("Ошибка сервера");
        }
    }

    public FilmResponse edit(FilmResponse filmResponse) {
        Long ageLimitId = null;
        if(filmResponse.getAgeLimit() != null) {
            ageLimitId = filmResponse.getAgeLimit().getId();
        }
        var filmRequest = new FilmRequest(
                filmResponse.getTitle(),
                filmResponse.getTitleForeign(),
                filmResponse.getDescription(),
                filmResponse.getSlogan(),
                filmResponse.getYearOfProduction(),
                filmResponse.getReleaseDateInWorld(),
                filmResponse.getReleaseDateInRussia(),
                filmResponse.getBudget(),
                filmResponse.getDurationInSeconds(),
                ageLimitId,
                filmResponse.getCountries().stream().mapToLong(CountryResponse::getId).boxed().toList(),
                filmResponse.getGenres().stream().mapToLong(GenreResponse::getId).boxed().toList(),
                filmResponse.getDirectors().stream().mapToLong(PersonResponse::getId).boxed().toList(),
                filmResponse.getScreenwriters().stream().mapToLong(PersonResponse::getId).boxed().toList(),
                filmResponse.getProducers().stream().mapToLong(PersonResponse::getId).boxed().toList(),
                filmResponse.getActors().stream().mapToLong(PersonResponse::getId).boxed().toList());
        String token = (String) VaadinSession.getCurrent().getAttribute("token");
        try {
            return filmClient.edit(filmResponse.getId(), filmRequest, "Bearer " + token);
        } catch (AuthException e) {
            throw new CrudOperationException("Войдите или зарегистрируйтесь");
        } catch (NotFoundException e) {
            throw new CrudOperationException("Страница не найдена");
        } catch (BadRequestException e) {
            throw new CrudOperationException(e.getMessage());
        } catch (FeignException e) {
            throw new CrudOperationException("Ошибка сервера");
        }
    }

    public void delete(FilmResponse filmResponse) {
        String token = (String) VaadinSession.getCurrent().getAttribute("token");
        try {
            filmClient.delete(filmResponse.getId(), "Bearer " + token);
        } catch (AuthException e) {
            throw new CrudOperationException("Войдите или зарегистрируйтесь");
        } catch (NotFoundException e) {
            throw new CrudOperationException("Страница не найдена");
        } catch (BadRequestException e) {
            throw new CrudOperationException(e.getMessage());
        } catch (FeignException e) {
            throw new CrudOperationException("Ошибка сервера");
        }
    }
}
