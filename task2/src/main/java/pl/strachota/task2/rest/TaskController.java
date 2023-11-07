package pl.strachota.task2.rest;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.strachota.task2.dto.task.CreateTaskDTO;
import pl.strachota.task2.dto.task.UpdateTaskDTO;
import pl.strachota.task2.model.Task;
import pl.strachota.task2.model.TaskStatus;
import pl.strachota.task2.service.impl.TaskServiceImpl;
import pl.strachota.task2.util.annotations.TaskSpec;


import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskServiceImpl taskService;

    @GetMapping
    public List<Task> getAllTasks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) TaskStatus taskStatus,
            @RequestParam(required = false) LocalDateTime dueDate,
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "id") String sortBy,
            TaskSpec taskSpec,
            @RequestParam(defaultValue = "asc") String sortDir) {
        return taskService.getAllTasks(taskSpec, pageNo, sortBy, sortDir);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @Valid @RequestBody UpdateTaskDTO updateTaskDTO) {
        return new ResponseEntity<>(taskService.updateTask(id, updateTaskDTO), HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<List<Task>> getTaskByUserId(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskByUserId(id));
    }

    @PatchMapping("/{id}/change-status")
    public ResponseEntity<String> changeTaskStatus(@PathVariable Long id, @RequestParam TaskStatus newStatus) {
        return ResponseEntity.status(HttpStatus.OK).body("Status changed to " + taskService.changeTaskStatus(id, newStatus));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody @Valid CreateTaskDTO createTaskDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(createTaskDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Task deleted");
    }
}