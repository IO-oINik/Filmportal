package ru.edu.filmportal.services;

import jakarta.ws.rs.ForbiddenException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.edu.filmportal.exceptions.BadRequestException;
import ru.edu.filmportal.exceptions.NotFoundException;
import ru.edu.filmportal.mappers.ReviewMapper;
import ru.edu.filmportal.models.requests.ReviewCreateRequest;
import ru.edu.filmportal.models.requests.ReviewEditRequest;
import ru.edu.filmportal.models.responses.FilmResponse;
import ru.edu.filmportal.models.responses.ReviewResponse;
import ru.edu.filmportal.models.responses.UserResponse;
import ru.edu.filmportal.repositories.ReviewRepository;
import ru.edu.filmportal.repositories.TypeReviewRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final UserService userService;
    private final FilmService filmService;
    private final ReviewRepository repository;
    private final TypeReviewRepository typeReviewRepository;
    private final ReviewMapper mapper;

    public ReviewResponse findById(long id) {
        return repository.findById(id).map(mapper::toReviewResponse).orElseThrow(()-> new NotFoundException("Review not found with ID: " + id));
    }

    public List<ReviewResponse> findByFilmId(long filmId) {
        try {
            filmService.findById(filmId);
        } catch (NotFoundException e) {
            throw new BadRequestException(e.getMessage());
        }
        return repository.findAllByFilmId(filmId).stream().map(mapper::toReviewResponse).toList();
    }

    public ReviewResponse create(ReviewCreateRequest request) {
        UserResponse userResponse = userService.findMe();

        try {
            filmService.findById(request.filmId());
        } catch (NotFoundException e) {
            throw new BadRequestException(e.getMessage());
        }

        var typeReview = typeReviewRepository.findById(request.typeId()).orElseThrow(()-> new NotFoundException("TypeReview not found with ID: " + request.typeId()));
        var review = mapper.toReview(request);
        review.setType(typeReview);
        review.setUserId(userResponse.id());

        return mapper.toReviewResponse(repository.save(review));
    }

    public void deleteById(long id) {
        UserResponse userResponse = userService.findMe();
        if (userResponse.role().equals("ADMIN")) {
            repository.deleteById(id);
            return;
        }

        repository.findById(id).ifPresent(review -> {
            if (review.getUserId().equals(userResponse.id())) {
                repository.deleteById(id);
            } else {
                throw new ForbiddenException("You do not have permission to delete this review");
            }
        });
    }

    public ReviewResponse findByFilmIdAndUserId(long filmId, long userId) {

        return repository.findReviewByFilmIdAndUserId(filmId, userId).map(mapper::toReviewResponse).orElseThrow(()-> new NotFoundException(String.format("Review not found with filmID %d and userId %d", filmId, userId)));
    }

    public List<ReviewResponse> findAll() {
        return repository.findAll().stream().map(mapper::toReviewResponse).toList();
    }

    public ReviewResponse update(long id, ReviewEditRequest request) {
        var review = repository.findById(id).orElseThrow(()-> new NotFoundException("Review not found with ID: " + id));
        if(request.text() != null && !request.text().isEmpty()) {
            review.setText(request.text());
        }
        if(request.title() != null && !request.title().isEmpty()) {
            review.setTitle(request.title());
        }

        return mapper.toReviewResponse(repository.save(review));
    }
}
