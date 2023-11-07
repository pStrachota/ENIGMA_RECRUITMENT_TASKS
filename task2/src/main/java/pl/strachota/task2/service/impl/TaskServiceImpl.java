package pl.strachota.task2.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.strachota.task2.dto.task.CreateTaskDTO;
import pl.strachota.task2.dto.task.UpdateTaskDTO;
import pl.strachota.task2.exception.CannotChangeStatusException;
import pl.strachota.task2.exception.TaskNotFoundException;
import pl.strachota.task2.model.Task;
import pl.strachota.task2.model.TaskStatus;
import pl.strachota.task2.model.User;
import pl.strachota.task2.repository.TaskRepository;
import pl.strachota.task2.repository.UserRepository;
import pl.strachota.task2.service.interfaces.TaskService;
import pl.strachota.task2.util.mapper.TaskMapper;

import java.util.List;
import java.util.Set;

import static pl.strachota.task2.util.properties.AppConstants.PAGE_SIZE;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final TaskMapper taskMapper;

    @Transactional(readOnly = true)
    @Override
    public List<Task> getAllTasks(Specification<Task> spec, int pageNo, String sortBy,
                                  String sortDir) {
        Pageable pageable = PageRequest.of(pageNo, PAGE_SIZE, sortDir.equalsIgnoreCase(
                Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending());
        Page<Task> pagedResult = taskRepository.findAll(spec, pageable);
        return pagedResult.getContent();
    }

    @Override
    public Task updateTask(Long id, UpdateTaskDTO updatedTask) {
        Task existingTask = this.getTaskById(id);
        existingTask.setTitle(updatedTask.getTitle() != null ? updatedTask.getTitle() : existingTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription() != null ? updatedTask.getDescription() : existingTask.getDescription());
        existingTask.setDueDate(updatedTask.getDueDate() != null ? updatedTask.getDueDate() : existingTask.getDueDate());
        return taskRepository.save(existingTask);
    }

    @Override
    public Task changeTaskStatus(Long id, TaskStatus newStatus) {
        Task task = this.getTaskById(id);
        if (!task.getStatus().canChangeTo(newStatus)) {
            throw new CannotChangeStatusException("Cannot change status from " + task.getStatus() + " to " + newStatus);
        }
        task.setStatus(newStatus);
        return taskRepository.save(task);
    }

    @Override
    public Task getTaskById(Long id) {
        return taskRepository.findById(id).orElseThrow(() -> new TaskNotFoundException("Task not found"));
    }

    @Override
    public Task createTask(CreateTaskDTO createTaskDTO) {
        Task task = taskMapper.mapToEntity(createTaskDTO);
        return taskRepository.save(task);
    }

    @Override
    public void deleteTask(Long id) {
        Task task = getTaskById(id);
        if (task.getStatus().equals(TaskStatus.IN_PROGRESS)) {
            throw new CannotChangeStatusException("Cannot delete task in progress");
        }
        taskRepository.deleteById(id);
    }

    @Override
    public Task assignUsersToTask(Long taskId, List<Long> userIds) {
        Task task = getTaskById(taskId);
        List<User> assignedUsers = userRepository.findAllById(userIds);
        task.setAssignedUsers(Set.copyOf(assignedUsers));
        return taskRepository.save(task);
    }

    @Override
    public List<Task> getTaskByUserId(Long id) {
        return taskRepository.findAllByAssignedUsers_Id(id);
    }
}