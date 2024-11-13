package ru.edu.ui.services;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudOperationException;
import ru.edu.ui.clients.FilmClient;
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
        return filmClient.findAll();
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
        try {
            return filmClient.create(filmRequest);
        } catch (FeignException.FeignClientException e) {
            String message = new String(e.responseBody().orElseThrow(() -> new CrudOperationException("Неизвестная ошибка.\nПовтрите попытку позже")).array(), StandardCharsets.UTF_8);
            try {
                JSONObject json = new JSONObject(message);
                throw new CrudOperationException(json.getString("message"));
            } catch (JSONException e1) {
                throw new CrudOperationException("Неизвестная ошибка.\nПовтрите попытку позже");
            }
        } catch (FeignException e) {
            throw new CrudOperationException("Неизвестная ошибка.\nПовтрите попытку позже");
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
        try {
            return filmClient.edit(filmResponse.getId(), filmRequest);
        } catch (FeignException.FeignClientException e) {
            String message = new String(e.responseBody().orElseThrow(() -> new CrudOperationException("Неизвестная ошибка.\nПовтрите попытку позже")).array(), StandardCharsets.UTF_8);
            try {
                JSONObject json = new JSONObject(message);
                throw new CrudOperationException(json.getString("message"));
            } catch (JSONException e1) {
                throw new CrudOperationException("Неизвестная ошибка.\nПовтрите попытку позже");
            }
        } catch (FeignException e) {
            throw new CrudOperationException("Неизвестная ошибка.\nПовтрите попытку позже");
        }
    }

    public void delete(FilmResponse filmResponse) {
        filmClient.delete(filmResponse.getId());
    }
}
