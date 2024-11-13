package ru.edu.filmportal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.edu.filmportal.models.database.TypeReview;

@Repository
public interface TypeReviewRepository extends JpaRepository<TypeReview, Long> {
    boolean existsByType(String type);
}
