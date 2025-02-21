package hexlet.code.mapper;

import hexlet.code.dto.task.TaskCreateDTO;
import hexlet.code.dto.task.TaskShowDTO;
import hexlet.code.dto.task.TaskUpdateDTO;
import hexlet.code.model.Label;
import hexlet.code.model.Task;

import hexlet.code.model.TaskStatus;
import hexlet.code.repository.LabelRepository;
import hexlet.code.repository.TaskStatusRepository;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

/**
 * Mapper class for handling Task entity transformations.
 * Uses Spring component model and implements custom mapping strategies.
 */
@Mapper(
        uses = { JsonNullableMapper.class, ReferenceMapper.class },
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class TaskMapper {

    /**
     * Repository for accessing task status data.
     */
    @Autowired
    private TaskStatusRepository statusRepository;

    /**
     * Repository for accessing label data.
     */
    @Autowired
    private LabelRepository labelRepository;

    /**
     * Maps a TaskCreateDTO to a Task entity.
     * Handles field name transformations and complex object mapping.
     *
     * @param dto the DTO containing task creation data
     * @return the mapped Task entity
     */
    @Mapping(target = "assignee", source = "assigneeId")
    @Mapping(target = "name", source = "title")
    @Mapping(target = "description", source = "content")
    @Mapping(target = "taskStatus", source = "status")
    @Mapping(target = "labels", source = "taskLabelIds")
    public abstract Task map(TaskCreateDTO dto);

    /**
     * Maps a Task entity to a TaskShowDTO.
     * Handles field name transformations and complex object mapping.
     *
     * @param model the Task entity to transform
     * @return the mapped TaskShowDTO
     */
    @Mapping(source = "assignee.id", target = "assigneeId")
    @Mapping(source = "name", target = "title")
    @Mapping(source = "description", target = "content")
    @Mapping(source = "taskStatus.slug", target = "status")
    @Mapping(source = "labels", target = "taskLabelIds")
    public abstract TaskShowDTO map(Task model);

    /**
     * Updates an existing Task entity with data from TaskUpdateDTO.
     * Handles field name transformations and complex object mapping.
     *
     * @param dto the DTO containing update data
     * @param model the Task entity to update
     */
    @Mapping(target = "assignee", source = "assigneeId")
    @Mapping(target = "name", source = "title")
    @Mapping(target = "description", source = "content")
    @Mapping(target = "taskStatus", source = "status")
    @Mapping(target = "labels", source = "taskLabelIds")
    public abstract void update(TaskUpdateDTO dto, @MappingTarget Task model);

    /**
     * Converts a status slug to a TaskStatus entity.
     * Throws exception if status not found.
     *
     * @param slug the status slug to convert
     * @return the corresponding TaskStatus entity
     */
    protected TaskStatus taskStatusFromSlug(String slug) {
        return statusRepository.findBySlug(slug).orElseThrow();
    }

    /**
     * Converts a set of label IDs to a set of Label entities.
     * Returns empty set if input is null.
     *
     * @param taskLabelIds the set of label IDs to convert
     * @return the set of corresponding Label entities
     */
    protected Set<Label> labelsFromLabelIds(Set<Long> taskLabelIds) {
        if (taskLabelIds != null) {
            return new HashSet<>(labelRepository.findAllById(taskLabelIds));
        }
        return new HashSet<Label>();
    }

    /**
     * Converts a set of Label entities to a set of their IDs.
     *
     * @param labels the set of Label entities to convert
     * @return the set of corresponding label IDs
     */
    protected Set<Long> taskLabelIdsFromLabels(Set<Label> labels) {
        return new HashSet<>(labels.stream()
                .map(Label::getId)
                .toList()
        );
    }
}
