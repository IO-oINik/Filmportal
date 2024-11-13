package ru.edu.filmportal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.edu.filmportal.models.database.ReviewScore;

@Repository
public interface ReviewScoreRepository extends JpaRepository<ReviewScore, Long> {
    boolean existsByReviewIdAndUserId(Long reviewId, Long userId);
    void deleteByUserIdAndReviewId(Long userId, Long reviewId);
}
