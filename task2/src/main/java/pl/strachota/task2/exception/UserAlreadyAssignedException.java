package pl.strachota.task2.exception;

public class UserAlreadyAssignedException extends RuntimeException {

    public UserAlreadyAssignedException(String message) {
        super(message);
    }

}

