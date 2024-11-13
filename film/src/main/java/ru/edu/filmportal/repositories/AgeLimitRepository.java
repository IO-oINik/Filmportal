package ru.edu.filmportal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.edu.filmportal.models.database.AgeLimit;

public interface AgeLimitRepository extends JpaRepository<AgeLimit, Long> {
    boolean existsByAge(Integer age);
}
