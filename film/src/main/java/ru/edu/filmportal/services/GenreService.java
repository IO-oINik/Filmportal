package ru.edu.filmportal.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.edu.filmportal.exceptions.UniqueFieldException;
import ru.edu.filmportal.mappers.GenreMapper;
import ru.edu.filmportal.models.requests.GenreRequest;
import ru.edu.filmportal.models.responses.GenreResponse;
import ru.edu.filmportal.repositories.GenreRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreService implements EntityService<GenreResponse, GenreRequest, GenreRequest> {
    private final GenreRepository repository;
    private final GenreMapper mapper;

    public GenreResponse findById(long id) {
        return repository.findById(id)
                .map(mapper::toGenreResponse)
                .orElseThrow(() -> new EntityNotFoundException("Genre not found with the ID: " + id));
    }

    public List<GenreResponse> findAll() {
        return repository.findAll()
                .stream().map(mapper::toGenreResponse).toList();
    }

    public GenreResponse create(GenreRequest request) {
        if(repository.existsByTitle(request.title())) {
            throw new UniqueFieldException(request.title() + " already exists");
        }
        var genre = repository.save(mapper.toGenre(request));
        return mapper.toGenreResponse(genre);
    }

    public GenreResponse update(long id, GenreRequest request) {
        var genre = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Genre not found with the ID: " + id));

        if(!request.title().equals(genre.getTitle())) {
            if(repository.existsByTitle(request.title())) {
                throw new UniqueFieldException(request.title() + " already exists");
            }
            genre.setTitle(request.title());
            repository.save(genre);
        }
        return mapper.toGenreResponse(genre);
    }

    public void delete(long id) {
        repository.deleteById(id);
    }
}
