package ru.edu.ui.services;

import com.vaadin.flow.server.VaadinSession;
import feign.FeignException;
import ru.edu.ui.exceptions.BadRequestException;
import ru.edu.ui.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudOperationException;
import ru.edu.ui.clients.CountryClient;
import ru.edu.ui.exceptions.AuthException;
import ru.edu.ui.models.requests.CountryRequest;
import ru.edu.ui.models.responses.CountryResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryService {
    private final CountryClient countryClient;

    public List<CountryResponse> findAll() {
        String token = (String) VaadinSession.getCurrent().getAttribute("token");
        try {
            return countryClient.findAll("Bearer " + token);
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

    public CountryResponse create(CountryResponse countryResponse) {
        var countryRequest = new CountryRequest(countryResponse.getTitle());
        String token = (String) VaadinSession.getCurrent().getAttribute("token");
        try {
            return countryClient.create(countryRequest, "Bearer " + token);
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

    public CountryResponse edit(CountryResponse countryResponse) {
        var countryRequset = new CountryRequest(countryResponse.getTitle());
        String token = (String) VaadinSession.getCurrent().getAttribute("token");
        try {
            return countryClient.edit(countryResponse.getId(), countryRequset, "Bearer " + token);
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

    public void delete(CountryResponse countryResponse) {
        String token = (String) VaadinSession.getCurrent().getAttribute("token");
        try {
        countryClient.delete(countryResponse.getId(),"Bearer " + token);
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
