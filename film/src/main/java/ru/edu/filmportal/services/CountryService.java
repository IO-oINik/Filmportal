package ru.edu.filmportal.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.edu.filmportal.exceptions.UniqueFieldException;
import ru.edu.filmportal.mappers.CountryMapper;
import ru.edu.filmportal.models.requests.CountryRequest;
import ru.edu.filmportal.models.responses.AgeLimitResponse;
import ru.edu.filmportal.models.responses.CountryResponse;
import ru.edu.filmportal.repositories.CountryRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CountryService implements EntityService<CountryResponse, CountryRequest, CountryRequest> {
    private final CountryRepository repository;
    private final CountryMapper mapper;

    public CountryResponse findById(long id) {
        return repository.findById(id)
                .map(mapper::toCountryResponse)
                .orElseThrow(() -> new EntityNotFoundException("Country not found with the ID: " + id));
    }

    public List<CountryResponse> findAll() {
        return repository.findAll()
                .stream().map(mapper::toCountryResponse).toList();
    }

    public CountryResponse create(CountryRequest request) {
        if(repository.existsByTitle(request.title())) {
            throw new UniqueFieldException(request.title() + " already exists");
        }
        var country = repository.save(mapper.toCountry(request));
        return mapper.toCountryResponse(country);
    }

    public CountryResponse update(long id, CountryRequest request) {
        var country = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Country not found with the ID: " + id));

        if(!request.title().equals(country.getTitle())) {
            if(repository.existsByTitle(request.title())) {
                throw new UniqueFieldException(request.title() + " already exists");
            }
            country.setTitle(request.title());
            repository.save(country);
        }
        return mapper.toCountryResponse(country);
    }

    public void delete(long id) {
        repository.deleteById(id);
    }
}