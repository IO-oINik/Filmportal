package ru.edu.filmportal.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.edu.filmportal.models.database.Review;
import ru.edu.filmportal.models.requests.ReviewCreateRequest;
import ru.edu.filmportal.models.responses.ReviewResponse;


@Component
@RequiredArgsConstructor
public class ReviewMapper {
    private final TypeReviewMapper mapper;

    public ReviewResponse toReviewResponse(Review review) {
        return new ReviewResponse(
                review.getId(),
                review.getTitle(),
                review.getText(),
                mapper.toTypeReviewResponse(review.getType()),
                review.getUserId(),
                review.getFilmId()
        );
    }

    public Review toReview(ReviewCreateRequest reviewCreateRequest) {
        return Review.builder()
                .title(reviewCreateRequest.title())
                .text(reviewCreateRequest.text())
                .userId(reviewCreateRequest.userId())
                .filmId(reviewCreateRequest.filmId())
                .build();
    }
}
