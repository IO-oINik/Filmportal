package ru.edu.filmportal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.edu.filmportal.models.database.Genre;

import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
    boolean existsByTitle(String title);
    Optional<Genre> findByTitle(String title);
}
