package hexlet.code.controller.api;

import hexlet.code.dto.taskstatus.TaskStatusCreateDTO;
import hexlet.code.dto.taskstatus.TaskStatusShowDTO;
import hexlet.code.dto.taskstatus.TaskStatusUpdateDTO;

import hexlet.code.service.TaskStatusService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.List;

@RestController
@RequestMapping("/api/task_statuses")
@AllArgsConstructor
public final class TaskStatusController {

    private TaskStatusService taskStatusService;

    @GetMapping
    public ResponseEntity<List<TaskStatusShowDTO>> getAll() {
        List<TaskStatusShowDTO> result = taskStatusService.getAll();
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(result.size()))
                .body(result);
    }

    @GetMapping(path = "/{id}")
    public TaskStatusShowDTO getById(@PathVariable long id) {
        return taskStatusService.get(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TaskStatusShowDTO create(@Valid @RequestBody TaskStatusCreateDTO data) {
        return taskStatusService.create(data);
    }

    @PutMapping(path = "/{id}")
    public TaskStatusShowDTO update(@Valid @RequestBody TaskStatusUpdateDTO taskStatusUpdateDTO,
                                @PathVariable long id) {
        return taskStatusService.update(id, taskStatusUpdateDTO);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        taskStatusService.delete(id);
    }
}
