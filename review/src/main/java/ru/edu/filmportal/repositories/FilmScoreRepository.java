package ru.edu.filmportal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.edu.filmportal.models.database.FilmScore;

import java.util.List;
import java.util.Optional;

@Repository
public interface FilmScoreRepository extends JpaRepository<FilmScore, Long> {
    boolean existsByFilmIdAndUserId(long filmId, long userId);
    List<FilmScore> findAllByFilmId(long filmId);
    List<FilmScore> findAllByUserId(long userId);
    Optional<FilmScore> findByUserIdAndFilmId(long userId, long filmId);
}
