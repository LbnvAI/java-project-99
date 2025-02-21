package hexlet.code.service;

import hexlet.code.dto.task.TaskCreateDTO;
import hexlet.code.dto.task.TaskShowDTO;
import hexlet.code.dto.task.TaskParamsDTO;
import hexlet.code.dto.task.TaskUpdateDTO;
import hexlet.code.mapper.TaskMapper;
import hexlet.code.model.Task;
import hexlet.code.repository.TaskRepository;
import hexlet.code.specification.TaskSpecification;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public final class TaskService {

    private final TaskRepository taskRepository;
    private final TaskMapper mapper;
    private final TaskSpecification specBuilder;

    public List<TaskShowDTO> getAll(TaskParamsDTO params) {
        Specification<Task> spec = specBuilder.build(params);
        List<Task> tasks = taskRepository.findAll(spec);
        return tasks.stream().map(mapper::map).toList();
    }

    public TaskShowDTO getById(long id) {
        Task task = taskRepository.findById(id).orElseThrow();
        return mapper.map(task);
    }

    public TaskShowDTO create(TaskCreateDTO data) {
        Task task = mapper.map(data);
        taskRepository.save(task);
        return mapper.map(task);
    }

    public TaskShowDTO update(TaskUpdateDTO data, long id) {
        Task task = taskRepository.findById(id).orElseThrow();
        mapper.update(data, task);
        taskRepository.save(task);
        return mapper.map(task);
    }

    public void deleteById(long id) {
        taskRepository.deleteById(id);
    }
}
