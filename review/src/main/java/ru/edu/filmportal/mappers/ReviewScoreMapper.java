package ru.edu.filmportal.mappers;

import org.springframework.stereotype.Component;
import ru.edu.filmportal.models.database.Review;
import ru.edu.filmportal.models.database.ReviewScore;
import ru.edu.filmportal.models.requests.ReviewScoreRequest;
import ru.edu.filmportal.models.responses.ReviewScoreResponse;

@Component
public class ReviewScoreMapper {
    public ReviewScoreResponse toReviewScoreResponse(ReviewScore reviewScore) {
        return new ReviewScoreResponse(
                reviewScore.getId(),
                reviewScore.getUserId(),
                reviewScore.getReview().getId(),
                reviewScore.isAssessment());
    }

    public ReviewScore toReviewScore(ReviewScoreRequest reviewScoreRequest) {
        return ReviewScore.builder()
                .userId(reviewScoreRequest.userId())
                .build();
    }
}
