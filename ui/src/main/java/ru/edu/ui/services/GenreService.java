package ru.edu.ui.services;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudOperationException;
import ru.edu.ui.clients.GenreClient;
import ru.edu.ui.models.requests.GenreRequest;
import ru.edu.ui.models.responses.GenreResponse;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreClient genreClient;

    public List<GenreResponse> findAll() {
        return genreClient.findAll();
    }

    public GenreResponse create(GenreResponse genreResponse) {
        var genreRequest = new GenreRequest(genreResponse.getTitle());
        try {
            return genreClient.create(genreRequest);
        } catch (FeignException.FeignClientException e) {
            String message = new String(e.responseBody().orElseThrow(() -> new CrudOperationException("Неизвестная ошибка.\nПовтрите попытку позже")).array(), StandardCharsets.UTF_8);
            try {
                JSONObject json = new JSONObject(message);
                throw new CrudOperationException(json.getString("message"));
            } catch (JSONException e1) {
                throw new CrudOperationException("Неизвестная ошибка.\nПовтрите попытку позже");
            }
        }
    }

    public GenreResponse edit(GenreResponse genreResponse) {
        var genreRequest = new GenreRequest(genreResponse.getTitle());
        try {
            return genreClient.edit(genreResponse.getId(), genreRequest);
        } catch (FeignException.FeignClientException e) {
            String message = new String(e.responseBody().orElseThrow(() -> new CrudOperationException("Неизвестная ошибка.\nПовтрите попытку позже")).array(), StandardCharsets.UTF_8);
            try {
                JSONObject json = new JSONObject(message);
                throw new CrudOperationException(json.getString("message"));
            } catch (JSONException e1) {
                throw new CrudOperationException("Неизвестная ошибка.\nПовтрите попытку позже");
            }
        }
    }

    public void delete(GenreResponse genreResponse) {
        genreClient.delete(genreResponse.getId());
    }
}
