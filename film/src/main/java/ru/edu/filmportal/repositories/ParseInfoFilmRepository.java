package ru.edu.filmportal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.edu.filmportal.models.database.ParseInfoFilm;

public interface ParseInfoFilmRepository extends JpaRepository<ParseInfoFilm, Long> {
}
