package pl.strachota.task2.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TaskStatus {
    TODO("todo"),
    IN_PROGRESS("in_progress"),
    DONE("done");

    private final String status;

    public boolean canChangeTo(TaskStatus status) {
        return switch (this) {
            case TODO -> status == IN_PROGRESS;
            case IN_PROGRESS -> status == DONE;
            default -> false;
        };
    }

}

