package pl.strachota.task2.service.interfaces;

import org.springframework.data.jpa.domain.Specification;
import pl.strachota.task2.dto.task.CreateTaskDTO;
import pl.strachota.task2.dto.task.UpdateTaskDTO;
import pl.strachota.task2.model.Task;
import pl.strachota.task2.model.TaskStatus;

import java.util.List;

public interface TaskService {

    List<Task> getAllTasks(Specification<Task> spec, int pageNo, String sortBy,
                           String sortDir);

    Task updateTask(Long id, UpdateTaskDTO updatedTask);

    Task changeTaskStatus(Long id, TaskStatus newStatus);

    Task getTaskById(Long id);

    Task createTask(CreateTaskDTO createTaskDTO);

    void deleteTask(Long id);

    Task assignUsersToTask(Long id, List<Long> userIds);

    List<Task> getTaskByUserId(Long id);
}