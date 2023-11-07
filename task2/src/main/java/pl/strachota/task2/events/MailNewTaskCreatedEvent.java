package pl.strachota.task2.events;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class MailNewTaskCreatedEvent extends AbstractEvent {

    private String description;
    private LocalDateTime deadline;

    public MailNewTaskCreatedEvent(List<String> emails, String title, String description, LocalDateTime deadline) {
        super(emails, title);
        this.description = description;
        this.deadline = deadline;
    }
}
