package pl.strachota.task2.unit;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.strachota.task2.dto.task.CreateTaskDTO;
import pl.strachota.task2.dto.task.UpdateTaskDTO;
import pl.strachota.task2.exception.CannotChangeStatusException;
import pl.strachota.task2.exception.TaskNotFoundException;
import pl.strachota.task2.model.Task;
import pl.strachota.task2.model.TaskStatus;
import pl.strachota.task2.repository.TaskRepository;
import pl.strachota.task2.service.impl.TaskServiceImpl;
import pl.strachota.task2.util.mapper.TaskMapper;

import java.util.HashSet;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskUnitTest {
    @InjectMocks
    private TaskServiceImpl taskService;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    TaskMapper taskMapper;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void shouldCreateTask() {
        // Arrange
        CreateTaskDTO createTaskDTO = new CreateTaskDTO();
        Task task = new Task();
        when(taskMapper.mapToEntity(any(CreateTaskDTO.class))).thenReturn(task);
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        // Act
        Task result = taskService.createTask(createTaskDTO);

        // Assert
        assertThat(result).isNotNull();
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void shouldGetTaskById() {
        // Arrange
        Long taskId = 1L;
        Task task = new Task();
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        // Act
        Task result = taskService.getTaskById(taskId);

        // Assert
        assertThat(result).isNotNull();
        verify(taskRepository).findById(taskId);
    }

    @Test
    void shouldUpdateTask_WhenTaskExists() {
        // Arrange
        Long taskId = 1L;
        Task existingTask = new Task();
        existingTask.setId(taskId);
        existingTask.setTitle("Existing Task");
        existingTask.setDescription("Description");
        existingTask.setStatus(TaskStatus.TODO);

        UpdateTaskDTO updatedTask = UpdateTaskDTO.builder()
                .title("Updated Task")
                .description("Updated Description")
                .build();

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Task result = taskService.updateTask(taskId, updatedTask);

        // Assert
        assertNotNull(result);
        assertEquals(updatedTask.getTitle(), result.getTitle());
        assertEquals(existingTask.getDescription(), result.getDescription());
        assertEquals(existingTask.getStatus(), result.getStatus());
        verify(taskRepository).findById(taskId);
        verify(taskRepository).save(existingTask);
    }

    @Test
    void shouldThrowTaskNotFoundException_WhenUpdatingNonexistentTask() {
        // Arrange
        Long taskId = 1L;
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        // Act and Assert
        ThrowableAssert.ThrowingCallable callable = () -> taskService.changeTaskStatus(taskId, TaskStatus.IN_PROGRESS);
        Assertions.assertThatThrownBy(callable).isInstanceOf(TaskNotFoundException.class);
    }

    @Test
    void shouldThrowCannotChangeStatusException_WhenChangingStatusInappropriate() {
        // Arrange
        Long taskId = 1L;
        Task existingTask = new Task();
        existingTask.setId(taskId);
        existingTask.setStatus(TaskStatus.IN_PROGRESS); // Cannot change from IN_PROGRESS to TODO

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));

        // Act and Assert
        ThrowableAssert.ThrowingCallable callable = () -> taskService.changeTaskStatus(taskId, TaskStatus.TODO);
        Assertions.assertThatThrownBy(callable).isInstanceOf(CannotChangeStatusException.class);
    }


    @Test
    void shouldChangeTaskStatus() {
        // Arrange
        Long taskId = 1L;
        Task existingTask = new Task();
        existingTask.setId(taskId);
        existingTask.setStatus(TaskStatus.TODO);

        TaskStatus newStatus = TaskStatus.IN_PROGRESS;

        when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any(Task.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Task result = taskService.changeTaskStatus(taskId, newStatus);

        // Assert
        assertNotNull(result);
        assertEquals(newStatus, result.getStatus());
        verify(taskRepository).findById(taskId);
        verify(taskRepository).save(existingTask);
    }

//    @Test
//    public void testGetAllTasks() {
//        // Arrange
//        List<Task> tasks = List.of(new Task(), new Task(), new Task());
//        when(taskRepository.findAll()).thenReturn(tasks);
//
//        // Act
//        List<Task> result = taskService.getAllTasks(null, null);
//
//        // Assert
//        assertThat(result).hasSize(3);
//        verify(taskRepository).findAll();
//    }


    @Test
    void shouldDeleteTask() {
        // Arrange
        Long taskId = 1L;
        Task task = Task.builder()
                .id(taskId)
                .title("Title")
                .description("Description")
                .assignedUsers(new HashSet<>())
                .status(TaskStatus.TODO)
                .build();
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(task));

        // Act
        taskService.deleteTask(taskId);

        // Assert
        verify(taskRepository).deleteById(taskId);
    }

    @Test
    void shouldThrowTaskNotFoundException_WhenDeletingNonexistentTask() {
        // Arrange
        Long taskId = 1L;
        when(taskRepository.findById(taskId)).thenReturn(Optional.empty());

        // Act and Assert
        ThrowableAssert.ThrowingCallable callable = () -> taskService.deleteTask(taskId);
        Assertions.assertThatThrownBy(callable).isInstanceOf(TaskNotFoundException.class);
    }


}
