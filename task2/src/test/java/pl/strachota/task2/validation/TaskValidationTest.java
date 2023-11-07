package pl.strachota.task2.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.strachota.task2.model.Task;
import pl.strachota.task2.model.TaskStatus;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TaskValidationTest {
    private Validator validator;

    @BeforeEach
    public void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidTask() {
        Task task = Task.builder()
                .title("Valid Title")
                .description("Valid Description")
                .status(TaskStatus.TODO)
                .dueDate(LocalDateTime.now().plusDays(1))
                .build();

        Set<ConstraintViolation<Task>> violations = validator.validate(task);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testInvalidTitle() {
        Task task = Task.builder()
                .title("")  // Empty title, should fail validation
                .description("Valid Description")
                .status(TaskStatus.TODO)
                .dueDate(LocalDateTime.now().plusDays(1))
                .build();

        Set<ConstraintViolation<Task>> violations = validator.validate(task);
        assertEquals(1, violations.size());
    }

    @Test
    void testInvalidDescription() {
        Task task = Task.builder()
                .title("Valid Title")
                .description("")  // Empty description, should fail validation
                .status(TaskStatus.TODO)
                .dueDate(LocalDateTime.now().plusDays(1))
                .build();

        Set<ConstraintViolation<Task>> violations = validator.validate(task);
        assertEquals(1, violations.size());
    }

    @Test
    void testInvalidDueDate() {
        Task task = Task.builder()
                .title("Valid Title")
                .description("Valid Description")
                .status(TaskStatus.TODO)
                .dueDate(LocalDateTime.now().minusDays(1))  // Past due date, should fail validation
                .build();

        Set<ConstraintViolation<Task>> violations = validator.validate(task);
        assertEquals(1, violations.size());
    }
}