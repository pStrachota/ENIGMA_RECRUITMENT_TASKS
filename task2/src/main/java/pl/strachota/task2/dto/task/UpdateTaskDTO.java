package pl.strachota.task2.dto.task;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static pl.strachota.task2.util.properties.AppConstants.MAX_DESCRIPTION_LENGTH;
import static pl.strachota.task2.util.properties.AppConstants.MAX_TITLE_LENGTH;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UpdateTaskDTO {

    @Size(min = 1, max = MAX_TITLE_LENGTH, message = "Title should be between 1 and" + MAX_TITLE_LENGTH + " characters")
    private String title;

    @Size(min = 1, max = MAX_DESCRIPTION_LENGTH, message = "Description should be between 1 and" + MAX_DESCRIPTION_LENGTH + " characters")
    private String description;

    @Future(message = "Due date should be in the future")
    private LocalDateTime dueDate;
}
