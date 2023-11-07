package pl.strachota.task2.events;

import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pl.strachota.task2.service.interfaces.EmailSenderService;

@Component
@AllArgsConstructor
public class MailSenderListener {

    private final EmailSenderService emailSenderServiceImpl;

    @EventListener
    public void handle(MailNewTaskCreatedEvent event) {
        event.getEmails().forEach(email -> emailSenderServiceImpl
                .sendEmail(email, "New task with title: " + event.getTitle() + " has been created!",
                        "Task description: " + event.getDescription() + "\nTask deadline: " + event.getDeadline()));
    }

    @EventListener
    public void handle(MailTaskStatusChangedEvent event) {
        event.getEmails().forEach(email -> emailSenderServiceImpl
                .sendEmail(email, "Task with title: " + event.getTitle() + " has changed status!",
                        "Previous status: " + event.getPreviousStatus() + "\nCurrent status: " + event.getCurrentStatus()));
    }

    @EventListener
    public void handle(MailNewUsersAssignedToTaskEvent event) {
        event.getEmails().forEach(email -> emailSenderServiceImpl
                .sendEmail(email, "You have been assigned to task with title: " + event.getTitle() + "!",
                        "Task deadline: " + event.getDeadline()));
    }

    @EventListener
    public void handle(MailTaskDeletedEvent event) {
        event.getEmails().forEach(email -> emailSenderServiceImpl
                .sendEmail(email, "Task with title: " + event.getTitle() + " has been deleted!",
                        "Task has been deleted!"));
    }

    @EventListener
    public void handle(MailTaskUpdatedEvent event) {
        event.getEmails().forEach(email -> emailSenderServiceImpl
                .sendEmail(email, "Task with title: " + event.getTitle() + " has been updated!",
                        "Task has been updated!"));
    }

}


