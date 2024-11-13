package ru.edu.filmportal.models.database;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String text;

    @ManyToOne
    @JoinColumn(nullable = false)
    private TypeReview type;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long filmId;

    @CreatedDate
    @Column(nullable = false)
    private LocalDate dateOfCreation;

    public Review(String title, String text, TypeReview type, Long userId) {
        this.title = title;
        this.text = text;
        this.type = type;
        this.userId = userId;
    }
}
