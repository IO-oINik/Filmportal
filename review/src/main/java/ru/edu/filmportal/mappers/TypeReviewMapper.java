package ru.edu.filmportal.mappers;

import org.springframework.stereotype.Component;
import ru.edu.filmportal.models.database.TypeReview;
import ru.edu.filmportal.models.requests.TypeReviewRequest;
import ru.edu.filmportal.models.responses.TypeReviewResponse;

@Component
public class TypeReviewMapper {
    public TypeReviewResponse toTypeReviewResponse(TypeReview typeReview) {
        return new TypeReviewResponse(typeReview.getId(), typeReview.getType());
    }

    public TypeReview toTypeReview(TypeReviewRequest typeReviewRequest) {
        return new TypeReview(typeReviewRequest.type());
    }
}
