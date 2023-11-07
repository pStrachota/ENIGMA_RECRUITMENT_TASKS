package pl.strachota.task2.service.interfaces;

public interface EmailSenderService {

        void sendEmail(String email, String subject, String text);
}
