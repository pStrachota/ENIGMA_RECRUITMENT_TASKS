package pl.strachota.task2.events;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import pl.strachota.task2.model.TaskStatus;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MailSenderPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public void publishNewTaskCreated(List<String> emails, String title, String description, LocalDateTime deadline) {
        applicationEventPublisher.publishEvent(new MailNewTaskCreatedEvent(emails, title, description, deadline));
    }
    public void publishTaskStatusChanged(List<String> emails, String title, TaskStatus previousStatus, TaskStatus currentStatus) {
        applicationEventPublisher.publishEvent(new MailTaskStatusChangedEvent(emails, title, previousStatus, currentStatus));
    }
    public void publishUsersAssignedToTask(List<String> emailList, String title, LocalDateTime dueDate) {
        applicationEventPublisher.publishEvent(new MailNewUsersAssignedToTaskEvent(emailList, title, dueDate));
    }
    public void publishTaskDeleted(List<String> list, String title) {
        applicationEventPublisher.publishEvent(new MailTaskDeletedEvent(list, title));
    }
    public void publishTaskUpdated(List<String> list, String title) {
        applicationEventPublisher.publishEvent(new MailTaskUpdatedEvent(list, title));
    }
}
