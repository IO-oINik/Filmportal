package ru.edu.filmportal.models.database;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class FilmScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long filmId;

    @Column(nullable = false)
    private int score;

    public FilmScore(Long userId, Long filmId, int score) {
        this.userId = userId;
        this.filmId = filmId;
        this.score = score;
    }
}
