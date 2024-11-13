package ru.edu.filmportal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.edu.filmportal.models.database.Film;
import ru.edu.filmportal.models.database.Person;


@Repository
public interface FilmRepository extends JpaRepository<Film, Long>, PagingAndSortingRepository<Film, Long> {
    boolean existsByActorsContains(Person actor);
    boolean existsByDirectorsContains(Person director);
    boolean existsByScreenwritersContains(Person screenwriter);
    boolean existsByProducersContains(Person producer);
}
