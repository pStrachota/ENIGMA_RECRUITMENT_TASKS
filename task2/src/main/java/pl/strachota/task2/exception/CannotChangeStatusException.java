package pl.strachota.task2.exception;

public class CannotChangeStatusException extends RuntimeException {

    public CannotChangeStatusException(String message) {
        super(message);
    }
}

