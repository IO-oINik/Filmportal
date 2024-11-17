package ru.edu.filmportal.models.database;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String hashPassword;

    @Column(nullable = false)
    private Role role;

    @CreatedDate
    private LocalDateTime dateOfCreation;

    @LastModifiedDate
    private LocalDateTime dateOfModified;

    @OneToOne(optional = true, fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private Token token;

    public User(String name, String surname, String nickname, String email, String hashPassword) {
        this.name = name;
        this.surname = surname;
        this.nickname = nickname;
        this.email = email;
        this.hashPassword = hashPassword;
    }
}
