package pl.strachota.task2.dto.task;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.strachota.task2.model.TaskStatus;

import java.time.LocalDateTime;
import java.util.Set;

import static pl.strachota.task2.util.properties.AppConstants.MAX_DESCRIPTION_LENGTH;
import static pl.strachota.task2.util.properties.AppConstants.MAX_TITLE_LENGTH;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreateTaskDTO {

    @Size(min = 1, max = MAX_TITLE_LENGTH, message = "Title should be between 1 and" + MAX_TITLE_LENGTH + " characters")
    @NotNull(message = "Title cannot be null")
    private String title;

    @Size(min = 1, max = MAX_DESCRIPTION_LENGTH, message = "Description should be between 1 and" + MAX_DESCRIPTION_LENGTH + " characters")
    @NotNull(message = "Description cannot be null")
    private String description;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Status cannot be null")
    private TaskStatus status;

    @Future(message = "Due date should be in the future")
    @NotNull(message = "Due date cannot be null")
    private LocalDateTime dueDate;

    @NotNull(message = "At least one user should be assigned")
    private Set<Long> assignedUserIds;

}
