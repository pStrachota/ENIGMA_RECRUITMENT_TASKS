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
            .email("test@mail.com")
            .build();

    public static CreateTaskDTO createTaskDTO = CreateTaskDTO.builder()
            .title("Test task")
            .assignedUserIds(Set.of(1L))
            .dueDate(LocalDateTime.now().plusDays(1))
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

    public static CreateTaskDTO updateTaskDTO = CreateTaskDTO.builder()
            .title("Updated task")
            .description("Updated description")
            .build();


}
