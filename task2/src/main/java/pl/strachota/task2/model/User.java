package pl.strachota.task2.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
@Table(name = "users")
public class User extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(unique = true, nullable = false)
    private String email;

}