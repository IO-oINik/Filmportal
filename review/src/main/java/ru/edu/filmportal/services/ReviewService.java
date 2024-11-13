package ru.edu.filmportal.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.edu.filmportal.mappers.ReviewMapper;
import ru.edu.filmportal.models.requests.ReviewCreateRequest;
import ru.edu.filmportal.models.requests.ReviewEditRequest;
import ru.edu.filmportal.models.responses.ReviewResponse;
import ru.edu.filmportal.repositories.ReviewRepository;
import ru.edu.filmportal.repositories.TypeReviewRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository repository;
    private final TypeReviewRepository typeReviewRepository;
    private final ReviewMapper mapper;

    public List<ReviewResponse> findAll() {
        return repository.findAll().stream().map(mapper::toReviewResponse).toList();
    }

    public ReviewResponse findById(long id) {
        return repository.findById(id).map(mapper::toReviewResponse).orElseThrow(()-> new EntityNotFoundException("Review not found with ID: " + id));
    }

    public ReviewResponse create(ReviewCreateRequest request) {
        var typeReview = typeReviewRepository.findById(request.typeId()).orElseThrow(()-> new EntityNotFoundException("TypeReview not found with ID: " + request.typeId()));
        var review = mapper.toReview(request);
        review.setType(typeReview);

        return mapper.toReviewResponse(repository.save(review));
    }

    public ReviewResponse update(long id, ReviewEditRequest request) {
        var review = repository.findById(id).orElseThrow(()-> new EntityNotFoundException("Review not found with ID: " + id));
        if(request.text() != null && !request.text().isEmpty()) {
            review.setText(request.text());
        }
        if(request.title() != null && !request.title().isEmpty()) {
            review.setTitle(request.title());
        }

        return mapper.toReviewResponse(repository.save(review));
    }

    public ReviewResponse findByFilmIdAndUserId(long filmId, long userId) {
        return repository.findReviewByFilmIdAndUserId(filmId, userId).map(mapper::toReviewResponse).orElseThrow(()-> new EntityNotFoundException(String.format("Review not found with filmID %d and userId %d", filmId, userId)));
    }

    public List<ReviewResponse> findByFilmId(long filmId) {
        return repository.findAllByFilmId(filmId).stream().map(mapper::toReviewResponse).toList();
    }

    public void deleteById(long id) {
        repository.deleteById(id);
    }
}
