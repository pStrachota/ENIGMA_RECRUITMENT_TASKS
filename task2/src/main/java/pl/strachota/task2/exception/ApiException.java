package pl.strachota.task2.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Getter
public class ApiException {

    private final String exceptionMessage;
    private final HttpStatusCode httpStatus;
    private final List<String> errors;
    private final LocalDateTime timestamp;
}