package pl.strachota.task2.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class MailNewUsersAssignedToTaskEvent extends AbstractEvent {

    private LocalDateTime deadline;

    public MailNewUsersAssignedToTaskEvent(List<String> emails, String title, LocalDateTime deadline) {
        super(emails, title);
        this.deadline = deadline;
    }
}
