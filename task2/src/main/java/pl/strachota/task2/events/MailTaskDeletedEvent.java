package pl.strachota.task2.events;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MailTaskDeletedEvent extends AbstractEvent {

    public MailTaskDeletedEvent(List<String> emails, String title) {
        super(emails, title);
    }
}
