package ru.edu.filmportal.models.database;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = true)
    private String titleForeign;

    @Column(nullable = true)
    private String description;

    @Column(nullable = true)
    private String slogan;

    @Column(nullable = false)
    private int yearOfProduction;

    @Column(nullable = true)
    private LocalDate releaseDateInWorld;

    @Column(nullable = true)
    private LocalDate releaseDateInRussia;

    @Column(nullable = true)
    private int budget;

    @Column(nullable = true)
    private int durationInSeconds;

    @ManyToOne
    private AgeLimit ageLimit;

    @Column(nullable = false)
    @ManyToMany
    @JoinTable(
            name = "film_country",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "country_id")
    )
    private List<Country> countries;

    @Column(nullable = true)
    @ManyToMany
    @JoinTable(
            name = "film_genre",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private List<Genre> genres;

    @Column(nullable = true)
    @ManyToMany
    @JoinTable(
            name = "film_director",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "director_id")
    )
    private List<Person> directors;

    @Column(nullable = true)
    @ManyToMany
    @JoinTable(
            name = "film_screenwriter",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "screenwriter_id")
    )
    private List<Person> screenwriters;

    @Column(nullable = true)
    @ManyToMany
    @JoinTable(
            name = "film_producer",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "producer_id")
    )
    private List<Person> producers;

    @Column(nullable = true)
    @ManyToMany
    @JoinTable(
            name = "film_actor",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private List<Person> actors;
}