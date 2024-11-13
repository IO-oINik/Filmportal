package ru.edu.filmportal.services;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.edu.filmportal.mappers.TypeReviewMapper;
import ru.edu.filmportal.models.database.TypeReview;
import ru.edu.filmportal.models.requests.TypeReviewRequest;
import ru.edu.filmportal.models.responses.TypeReviewResponse;
import ru.edu.filmportal.repositories.TypeReviewRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TypeReviewService {
    private final TypeReviewRepository repository;
    private final TypeReviewMapper mapper;

    public List<TypeReviewResponse> findAll() {
        return repository.findAll().stream().map(mapper::toTypeReviewResponse).toList();
    }

    public TypeReviewResponse findById(long id) {
        return mapper.toTypeReviewResponse(repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Type review not found with the ID: " + id)));
    }

    public TypeReviewResponse create(TypeReviewRequest request) {
        if(repository.existsByType(request.type())) {
            throw new EntityExistsException("Type review already exists with the type: " + request.type());
        }
        TypeReview typeReview = mapper.toTypeReview(request);
        typeReview = repository.save(typeReview);
        return mapper.toTypeReviewResponse(typeReview);
    }

    public TypeReviewResponse update(long id, TypeReviewRequest request) {
        var typeReview = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Type review not found with the ID: " + id));

        if(!typeReview.getType().equals(request.type())) {
            if(repository.existsByType(request.type())) {
                throw new EntityExistsException("Type review already exists with the type: " + request.type());
            }
            typeReview.setType(request.type());
            typeReview = repository.save(typeReview);
        }
        return mapper.toTypeReviewResponse(typeReview);
    }

    public void delete(long id) {
        repository.deleteById(id);
    }
}
