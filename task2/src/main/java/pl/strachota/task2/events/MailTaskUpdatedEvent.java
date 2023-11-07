package pl.strachota.task2.events;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MailTaskUpdatedEvent extends AbstractEvent {

    public MailTaskUpdatedEvent(List<String> emails, String title) {
        super(emails, title);
    }
}
