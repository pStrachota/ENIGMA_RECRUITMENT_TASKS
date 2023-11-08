package pl.strachota.task2.controller;

import pl.strachota.task2.dto.task.CreateTaskDTO;
import pl.strachota.task2.dto.user.CreateUserDTO;
import pl.strachota.task2.model.TaskStatus;

import java.time.LocalDateTime;
import java.util.Set;

public class DataForTests {


    public static CreateUserDTO createUserDTO = CreateUserDTO.builder()
            .firstName("Jan")
            .lastName("Kowalski")
            .email("test@example.com")
            .build();

    public static CreateUserDTO createUserDTOInvalidEmail = CreateUserDTO.builder()
            .firstName("Jan")
            .lastName("Kowalski")
            .email("ivalidemail")
            .build();

    public static CreateUserDTO createUserDTOInvalidName = CreateUserDTO.builder()
            .lastName("Kowalski123")
            .email("example@mail.com")
            .build();

    public static CreateTaskDTO createTaskDTO = CreateTaskDTO.builder()
            .title("Test task")
            .assignedUserIds(Set.of(2L))
            .dueDate(LocalDateTime.now().plusDays(2))
            .status(TaskStatus.TODO)
            .description("Test description")
            .build();

    public static CreateTaskDTO createTaskDTOInvalidDate = CreateTaskDTO.builder()
            .title("Test task")
            .assignedUserIds(Set.of(1L))
            .dueDate(LocalDateTime.now().minusDays(1))
            .status(TaskStatus.TODO)
            .description("Test description")
            .build();

    public static CreateTaskDTO createTaskDTOInvalidUserNumber = CreateTaskDTO.builder()
            .title("Test task")
            .assignedUserIds(Set.of(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L, 11L))
            .dueDate(LocalDateTime.now().plusDays(2))
            .status(TaskStatus.TODO)
            .description("Test description")
            .build();

    public static CreateTaskDTO updateTaskDTO = CreateTaskDTO.builder()
            .title("Updated task")
            .description("Updated description")
            .build();


}
