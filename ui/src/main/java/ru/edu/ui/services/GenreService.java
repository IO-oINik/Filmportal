package ru.edu.ui.services;

import com.vaadin.flow.server.VaadinSession;
import feign.FeignException;
import ru.edu.ui.exceptions.BadRequestException;
import ru.edu.ui.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudOperationException;
import ru.edu.ui.clients.GenreClient;
import ru.edu.ui.exceptions.AuthException;
import ru.edu.ui.models.requests.GenreRequest;
import ru.edu.ui.models.responses.GenreResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService {
    private final GenreClient genreClient;

    public List<GenreResponse> findAll() {
        try {
            return genreClient.findAll();
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

    public GenreResponse create(GenreResponse genreResponse) {
        var genreRequest = new GenreRequest(genreResponse.getTitle());
        String token = (String) VaadinSession.getCurrent().getAttribute("token");
        try {
            return genreClient.create(genreRequest, "Bearer " + token);
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

    public GenreResponse edit(GenreResponse genreResponse) {
        var genreRequest = new GenreRequest(genreResponse.getTitle());
        String token = (String) VaadinSession.getCurrent().getAttribute("token");
        try {
            return genreClient.edit(genreResponse.getId(), genreRequest, "Bearer " + token);
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

    public void delete(GenreResponse genreResponse) {
        String token = (String) VaadinSession.getCurrent().getAttribute("token");
        try {
            genreClient.delete(genreResponse.getId(), "Bearer " + token);
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
