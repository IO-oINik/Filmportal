package ru.edu.filmportal.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.edu.filmportal.mappers.PersonMapper;
import ru.edu.filmportal.models.requests.PersonCreateRequest;
import ru.edu.filmportal.models.requests.PersonEditRequest;
import ru.edu.filmportal.models.responses.CareersResponse;
import ru.edu.filmportal.models.responses.PersonResponse;
import ru.edu.filmportal.repositories.CountryRepository;
import ru.edu.filmportal.repositories.FilmRepository;
import ru.edu.filmportal.repositories.PersonRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonService implements EntityService<PersonResponse, PersonCreateRequest, PersonEditRequest> {
    private final PersonRepository repository;
    private final CountryRepository countryRepository;
    private final FilmRepository filmRepository;
    private final PersonMapper mapper;

    public CareersResponse findCareers(long id) {
        var person = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Person not found with the ID: " + id));

        List<String> careers = new ArrayList<>();
        if(filmRepository.existsByProducersContains(person)) {
            careers.add("Producer");
        } else if(filmRepository.existsByActorsContains(person)) {
            careers.add("Actor");
        } else if(filmRepository.existsByDirectorsContains(person)) {
            careers.add("Director");
        } else if(filmRepository.existsByScreenwritersContains(person)) {
            careers.add("Screenwriter");
        }
        return new CareersResponse(careers);
    }

    public PersonResponse findById(long id) {
        return repository.findById(id)
                .map(mapper::toPersonResponse)
                .orElseThrow(() -> new EntityNotFoundException("Person not found with the ID: " + id));
    }

    public List<PersonResponse> findAll() {
        return repository.findAll()
                .stream().map(mapper::toPersonResponse)
                .toList();
    }

    public PersonResponse create(PersonCreateRequest request) {
        var person = mapper.toPerson(request);
        if(request.idCountry() != null) {
            var country = countryRepository.findById(request.idCountry()).orElseThrow(() -> new EntityNotFoundException("Country not found with the ID: " + request.idCountry()));
            person.setCountryOfBirth(country);
        }
        person = repository.save(person);

        return mapper.toPersonResponse(person);
    }

    public PersonResponse update(long id, PersonEditRequest request) {
        var person = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Person not found with the ID: " + id));

        if(request.name() != null && !request.name().isBlank()) {
            person.setName(request.name());
        }
        if(request.surname() != null && !request.surname().isBlank()) {
            person.setSurname(request.surname());
        }
        if(request.nameForeign() != null && !request.nameForeign().isBlank()) {
            person.setNameForeign(request.nameForeign());
        }
        if(request.dateOfBirth() != null) {
            person.setDateOfBirth(request.dateOfBirth());
        }
        if(request.idCountry() != null) {
            var newCountry = countryRepository.findById(request.idCountry()).orElseThrow(() -> new EntityNotFoundException("Country not found with the ID: " + request.idCountry()));
            person.setCountryOfBirth(newCountry);
        }
        person = repository.save(person);
        return mapper.toPersonResponse(person);
    }

    public void delete(long id) {
        repository.deleteById(id);
    }
}
