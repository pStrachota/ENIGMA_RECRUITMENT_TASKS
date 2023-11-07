package pl.strachota.task2.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Builder
@Table(name = "tasks")
public class Task extends AbstractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status;

    @Column(nullable = false)
    private LocalDateTime dueDate;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "task_assigned_users",
            joinColumns = @JoinColumn(name = "task_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> assignedUsers = new HashSet<>();
}