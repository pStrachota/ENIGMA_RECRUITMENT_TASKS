package pl.strachota.task2.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.strachota.task2.model.User;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class UserValidationTest {

    private Validator validator;

    @BeforeEach
    public void setupValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void testValidUser() {
        User user = User.builder()
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@example.com")
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty());
    }

    @Test
    void testInvalidFirstName() {
        User user = User.builder()
                .firstName("")  // Empty first name, should fail validation
                .lastName("Doe")
                .email("johndoe@example.com")
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
    }

    @Test
    void testInvalidLastName() {
        User user = User.builder()
                .firstName("John")
                .lastName("")  // Empty last name, should fail validation
                .email("johndoe@example.com")
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
    }

    @Test
    void testInvalidEmail() {
        User user = User.builder()
                .firstName("John")
                .lastName("Doe")
                .email("invalidemail")  // Invalid email format, should fail validation
                .build();

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
    }
}
