package hexlet.code.service;

import hexlet.code.dto.taskstatus.TaskStatusCreateDTO;
import hexlet.code.dto.taskstatus.TaskStatusShowDTO;
import hexlet.code.dto.taskstatus.TaskStatusUpdateDTO;
import hexlet.code.mapper.TaskStatusMapper;
import hexlet.code.model.TaskStatus;
import hexlet.code.repository.TaskStatusRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public final class TaskStatusService {
    private TaskStatusMapper taskStatusMapper;
    private TaskStatusRepository taskStatusRepository;

    public TaskStatusShowDTO create(TaskStatusCreateDTO taskStatusCreateDTO) {
        TaskStatus taskStatus = taskStatusMapper.map(taskStatusCreateDTO);
        return taskStatusMapper.map(taskStatusRepository.save(taskStatus));
    }

    public List<TaskStatusShowDTO> getAll() {
        return taskStatusRepository.findAll().stream().map(taskStatusMapper::map).toList();
    }

    public TaskStatusShowDTO get(long id) {
        return taskStatusMapper.map(
                taskStatusRepository.findById(id)
                        .orElseThrow(() -> new UsernameNotFoundException(
                                "Task Status with id = " + id + " not found")));
    }

    public TaskStatusShowDTO update(long id, TaskStatusUpdateDTO taskStatusUpdateDTO) {
        TaskStatus taskStatus = taskStatusRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Task Status with id = " + id + " not found"));
        taskStatusMapper.update(taskStatusUpdateDTO, taskStatus);
        return taskStatusMapper.map(taskStatusRepository.save(taskStatus));
    }

    public void delete(long id) {
        taskStatusRepository.deleteById(id);
    }
}
