package ru.edu.ui.services;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudOperationException;
import ru.edu.ui.clients.AgeLimitClient;
import ru.edu.ui.models.requests.AgeLimitRequest;
import ru.edu.ui.models.responses.AgeLimitResponse;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AgeLimitService {
    private final AgeLimitClient ageLimitClient;

    public List<AgeLimitResponse> findAll() {
        return ageLimitClient.findAll();
    }

    public AgeLimitResponse create(AgeLimitResponse ageLimitResponse) {
        var ageLimitRequest = new AgeLimitRequest(ageLimitResponse.getAge());
        try {
            return ageLimitClient.create(ageLimitRequest);
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

    public AgeLimitResponse edit(AgeLimitResponse ageLimitResponse) {
        var ageLimitRequest = new AgeLimitRequest(ageLimitResponse.getAge());
        try {
            return ageLimitClient.edit(ageLimitResponse.getId(), ageLimitRequest);
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

    public void delete(AgeLimitResponse ageLimitResponse) {
        ageLimitClient.delete(ageLimitResponse.getId());
    }
}
