package ru.edu.ui.services;

import com.vaadin.flow.server.VaadinSession;
import feign.FeignException;
import ru.edu.ui.exceptions.BadRequestException;
import ru.edu.ui.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudOperationException;
import ru.edu.ui.clients.PersonClient;
import ru.edu.ui.exceptions.AuthException;
import ru.edu.ui.models.requests.PersonRequest;
import ru.edu.ui.models.responses.CareersResponse;
import ru.edu.ui.models.responses.PersonResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonService {
    private final PersonClient personClient;

    public CareersResponse getCareers(PersonResponse personResponse) {
        String token = (String) VaadinSession.getCurrent().getAttribute("token");
        try {
            return personClient.getCareers(personResponse.getId(), "Bearer " + token);
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

    public List<PersonResponse> findAll() {
        String token = (String) VaadinSession.getCurrent().getAttribute("token");
        try {
            return personClient.findAll("Bearer " + token);
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

    public PersonResponse create(PersonResponse personResponse) {
        var personRequest = new PersonRequest(personResponse.getName(),
                personResponse.getSurname(),
                personResponse.getNameForeign(),
                personResponse.getDateOfBirth(),
                personResponse.getCountryOfBirth().getId());
        String token = (String) VaadinSession.getCurrent().getAttribute("token");
        try {
            return personClient.create(personRequest, "Bearer " + token);
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
        String token = (String) VaadinSession.getCurrent().getAttribute("token");
        try {
            return personClient.edit(personResponse.getId(), personRequest, "Bearer " + token);
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

    public void delete(PersonResponse genreResponse) {
        String token = (String) VaadinSession.getCurrent().getAttribute("token");
        try {
            personClient.delete(genreResponse.getId(), "Bearer " + token);
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
