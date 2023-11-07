package pl.strachota.task2.events;

import lombok.Getter;
import lombok.Setter;
import pl.strachota.task2.model.TaskStatus;

import java.util.List;

@Getter
@Setter
public class MailTaskStatusChangedEvent extends AbstractEvent {

    private TaskStatus previousStatus;
    private TaskStatus currentStatus;

    public MailTaskStatusChangedEvent(List<String> emails, String title, TaskStatus previousStatus, TaskStatus currentStatus) {
        super(emails, title);
        this.previousStatus = previousStatus;
        this.currentStatus = currentStatus;
    }
}
