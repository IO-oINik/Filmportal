package ru.edu.ui.services;

import com.vaadin.flow.server.VaadinSession;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudOperationException;
import ru.edu.ui.clients.UserClient;
import ru.edu.ui.exceptions.AuthException;
import ru.edu.ui.exceptions.BadRequestException;
import ru.edu.ui.exceptions.NotFoundException;
import ru.edu.ui.models.requests.UserEditRequest;
import ru.edu.ui.models.responses.AgeLimitResponse;
import ru.edu.ui.models.responses.UserResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserClient userClient;

    public List<UserResponse> findAll() {
        String token = (String) VaadinSession.getCurrent().getAttribute("token");
        try {
            return userClient.findAll("Bearer " + token);
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

    public UserResponse edit(UserResponse userResponse) {
        var userEditRequest = new UserEditRequest(userResponse.getName(),
                userResponse.getSurname(),
                userResponse.getNickname(),
                userResponse.getEmail(),
                userResponse.getRole());
        String token = (String) VaadinSession.getCurrent().getAttribute("token");
        try {
            return userClient.edit(userResponse.getId(), userEditRequest, "Bearer " + token);
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

    public void delete(UserResponse userResponse) {
        String token = (String) VaadinSession.getCurrent().getAttribute("token");
        try {
            userClient.delete(userResponse.getId(), "Bearer " + token);
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
