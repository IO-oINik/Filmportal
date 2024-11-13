package ru.edu.ui.services;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudOperationException;
import ru.edu.ui.clients.CountryClient;
import ru.edu.ui.models.requests.CountryRequest;
import ru.edu.ui.models.responses.CountryResponse;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryService {
    private final CountryClient countryClient;

    public List<CountryResponse> findAll() {
        return countryClient.findAll();
    }

    public CountryResponse create(CountryResponse countryResponse) {
        var countryRequest = new CountryRequest(countryResponse.getTitle());
        try {
            return countryClient.create(countryRequest);
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

    public CountryResponse edit(CountryResponse countryResponse) {
        var countryRequset = new CountryRequest(countryResponse.getTitle());
        try {
            return countryClient.edit(countryResponse.getId(), countryRequset);
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

    public void delete(CountryResponse countryResponse) {
        countryClient.delete(countryResponse.getId());
    }
}
