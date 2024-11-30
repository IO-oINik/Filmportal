package ru.edu.ui.services;

import com.vaadin.flow.server.VaadinSession;
import feign.FeignException;
import ru.edu.ui.exceptions.BadRequestException;
import ru.edu.ui.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudOperationException;
import ru.edu.ui.clients.AgeLimitClient;
import ru.edu.ui.exceptions.AuthException;
import ru.edu.ui.models.requests.AgeLimitRequest;
import ru.edu.ui.models.responses.AgeLimitResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AgeLimitService {
    private final AgeLimitClient ageLimitClient;

    public List<AgeLimitResponse> findAll() {
        try {
            return ageLimitClient.findAll();
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

    public AgeLimitResponse create(AgeLimitResponse ageLimitResponse) {
        var ageLimitRequest = new AgeLimitRequest(ageLimitResponse.getAge());
        String token = (String) VaadinSession.getCurrent().getAttribute("token");
        try {
            AgeLimitResponse response = ageLimitClient.create(ageLimitRequest, "Bearer " + token);
            return response;
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

    public AgeLimitResponse edit(AgeLimitResponse ageLimitResponse) {
        var ageLimitRequest = new AgeLimitRequest(ageLimitResponse.getAge());
        String token = (String) VaadinSession.getCurrent().getAttribute("token");
        try {
            return ageLimitClient.edit(ageLimitResponse.getId(), ageLimitRequest, "Bearer " + token);
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

    public void delete(AgeLimitResponse ageLimitResponse) {
        String token = (String) VaadinSession.getCurrent().getAttribute("token");
        try {
            ageLimitClient.delete(ageLimitResponse.getId(), "Bearer " + token);
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
