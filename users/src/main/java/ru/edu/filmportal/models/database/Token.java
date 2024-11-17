package ru.edu.filmportal.models.database;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "token")
public class Token {
    @Id
    private long id;
    @Column(nullable = false)
    private String token;
    @CreatedDate
    private Date createdAt;

    public Token(String token) {
        this.token = token;
    }
}
