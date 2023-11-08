package pl.strachota.task2.rest;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.strachota.task2.dto.task.CreateTaskDTO;
import pl.strachota.task2.dto.task.UpdateTaskDTO;
import pl.strachota.task2.model.Task;
import pl.strachota.task2.model.TaskStatus;
import pl.strachota.task2.service.impl.TaskServiceImpl;
import pl.strachota.task2.util.annotations.TaskSpec;

import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskServiceImpl taskService;

    @GetMapping
    @Operation(summary = "Get all tasks")
    public List<Task> getAllTasks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) TaskStatus status,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "id") String sortBy,
            @Parameter(hidden = true) TaskSpec taskSpec,
            @RequestParam(defaultValue = "asc") String sortDir) {
        return taskService.getAllTasks(taskSpec, pageNo, sortBy, sortDir);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update task")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @Valid @RequestBody UpdateTaskDTO updateTaskDTO) {
        return new ResponseEntity<>(taskService.updateTask(id, updateTaskDTO), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    @Operation(summary = "Get task by user id")
    public ResponseEntity<List<Task>> getTaskByUserId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.getTaskByUserId(id));
    }

    @PatchMapping("/{id}/change-status")
    @Operation(summary = "Change task status")
    public ResponseEntity<Task> changeTaskStatus(@PathVariable Long id, @RequestParam TaskStatus newStatus) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.changeTaskStatus(id, newStatus));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get task by id")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.getTaskById(id));
    }

    @PatchMapping("/{id}/assign")
    @Operation(summary = "Assign task to user")
    public ResponseEntity<Task> assignUsersToTask(@PathVariable Long id, @RequestParam List<Long> userIds) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.assignUsersToTask(id, userIds));
    }

    @PostMapping
    @Operation(summary = "Create task")
    public ResponseEntity<Task> createTask(@RequestBody @Valid CreateTaskDTO createTaskDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(createTaskDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete task")
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Task deleted");
    }
}