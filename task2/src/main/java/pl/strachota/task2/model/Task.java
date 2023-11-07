package pl.strachota.task2.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static pl.strachota.task2.util.properties.AppConstants.MAX_DESCRIPTION_LENGTH;
import static pl.strachota.task2.util.properties.AppConstants.MAX_TITLE_LENGTH;


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

    @Size(min = 1, max = MAX_TITLE_LENGTH, message = "Title should be between 1 and" + MAX_TITLE_LENGTH + " characters")
    @Column(nullable = false)
    private String title;

    @Size(min = 1, max = MAX_DESCRIPTION_LENGTH, message = "Description should be between 1 and" + MAX_DESCRIPTION_LENGTH + " characters")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status;

    @Future(message = "Due date should be in the future")
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