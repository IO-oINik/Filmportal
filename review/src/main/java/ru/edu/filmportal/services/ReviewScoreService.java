package ru.edu.filmportal.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.edu.filmportal.mappers.ReviewScoreMapper;
import ru.edu.filmportal.models.requests.ReviewScoreRequest;
import ru.edu.filmportal.models.responses.ReviewScoreResponse;
import ru.edu.filmportal.repositories.ReviewRepository;
import ru.edu.filmportal.repositories.ReviewScoreRepository;

@Service
@RequiredArgsConstructor
public class ReviewScoreService {
    private final ReviewScoreRepository repository;
    private final ReviewScoreMapper mapper;
    private final ReviewRepository reviewRepository;

    public ReviewScoreResponse addPositiveScore(ReviewScoreRequest request) {
        return null;
    }

    public void deleteByUserIdAndReviewId(long userId, long reviewId) {
        repository.deleteByUserIdAndReviewId(userId, reviewId);
    }
}
