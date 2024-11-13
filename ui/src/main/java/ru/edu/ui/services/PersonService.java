package ru.edu.ui.services;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudOperationException;
import ru.edu.ui.clients.PersonClient;
import ru.edu.ui.models.requests.PersonRequest;
import ru.edu.ui.models.responses.CareersResponse;
import ru.edu.ui.models.responses.PersonResponse;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonService {
    private final PersonClient personClient;

    public CareersResponse getCareers(PersonResponse personResponse) {
        return personClient.getCareers(personResponse.getId());
    }

    public List<PersonResponse> findAll() {
        return personClient.findAll();
    }

    public PersonResponse create(PersonResponse personResponse) {
        var personRequest = new PersonRequest(personResponse.getName(),
                personResponse.getSurname(),
                personResponse.getNameForeign(),
                personResponse.getDateOfBirth(),
                personResponse.getCountryOfBirth().getId());
        try {
            return personClient.create(personRequest);
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

    public PersonResponse edit(PersonResponse personResponse) {
        Long idCountry = null;
        if(personResponse.getCountryOfBirth() != null) {
            idCountry = personResponse.getCountryOfBirth().getId();
        }

        var personRequest = new PersonRequest(personResponse.getName(),
                personResponse.getSurname(),
                personResponse.getNameForeign(),
                personResponse.getDateOfBirth(),
                idCountry);
        try {
            return personClient.edit(personResponse.getId(), personRequest);
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

    public void delete(PersonResponse genreResponse) {
        personClient.delete(genreResponse.getId());
    }
}
