package pl.strachota.task2.util.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.strachota.task2.dto.task.CreateTaskDTO;
import pl.strachota.task2.model.Task;
import pl.strachota.task2.model.TaskStatus;
import pl.strachota.task2.model.User;
import pl.strachota.task2.repository.UserRepository;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TaskMapper {

    private final UserRepository userRepository;

    public CreateTaskDTO mapToDTO(Task task) {
        CreateTaskDTO createTaskDTO = new CreateTaskDTO();
        createTaskDTO.setTitle(task.getTitle());
        createTaskDTO.setDescription(task.getDescription());
        createTaskDTO.setStatus(task.getStatus());
        createTaskDTO.setAssignedUserIds(
                task.getAssignedUsers().stream()
                        .map(User::getId)
                        .collect(Collectors.toSet())
        );
        return createTaskDTO;
    }

    public Task mapToEntity(CreateTaskDTO createTaskDTO) {
        return Task.builder()
                .title(createTaskDTO.getTitle())
                .description(createTaskDTO.getDescription())
                .status(TaskStatus.TODO)
                .dueDate(createTaskDTO.getDueDate())
                .assignedUsers(
                        createTaskDTO.getAssignedUserIds().stream()
                                .map(userId -> userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found")))
                                .collect(Collectors.toSet())
                )
                .build();
    }
}