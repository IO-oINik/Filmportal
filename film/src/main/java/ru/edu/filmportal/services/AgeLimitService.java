package ru.edu.filmportal.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.edu.filmportal.exceptions.UniqueFieldException;
import ru.edu.filmportal.mappers.AgeLimitMapper;
import ru.edu.filmportal.models.requests.AgeLimitRequest;
import ru.edu.filmportal.models.responses.AgeLimitResponse;
import ru.edu.filmportal.repositories.AgeLimitRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AgeLimitService implements EntityService<AgeLimitResponse, AgeLimitRequest, AgeLimitRequest> {
    private final AgeLimitRepository repository;
    private final AgeLimitMapper mapper;

    public AgeLimitResponse findById(long id) {
        return repository.findById(id)
                .map(mapper::toAgeLimitResponse)
                .orElseThrow(() -> new EntityNotFoundException("AgeLimit not found with the ID: " + id));
    }

    public List<AgeLimitResponse> findAll() {
        return repository.findAll()
                .stream().map(mapper::toAgeLimitResponse).toList();
    }

    public AgeLimitResponse create(AgeLimitRequest request) {
        if(repository.existsByAge(request.age())) {
            throw new UniqueFieldException("AgeLimit = " + request.age() + " already exists");
        }
        var ageLimit = repository.save(mapper.toAgeLimit(request));
        return mapper.toAgeLimitResponse(ageLimit);
    }

    public AgeLimitResponse update(long id, AgeLimitRequest request) {
        var ageLimit = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("AgeLimit not found with the ID: " + id));

        if(request.age() != ageLimit.getAge()) {
            if(repository.existsByAge(request.age())) {
                throw new UniqueFieldException("AgeLimit = " + request.age() + " already exists");
            }
            ageLimit.setAge(request.age());
            repository.save(ageLimit);
        }
        return mapper.toAgeLimitResponse(ageLimit);
    }

    public void delete(long id) {
        repository.deleteById(id);
    }
}
