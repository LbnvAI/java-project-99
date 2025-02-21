package hexlet.code.specification;

import hexlet.code.dto.task.TaskParamsDTO;
import hexlet.code.model.Task;
import hexlet.code.repository.LabelRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

/**
 * Component class implementing dynamic query specifications for Task entities.
 * Uses Spring Data JPA Specifications API to build flexible search criteria.
 */
@Component
@AllArgsConstructor
public class TaskSpecification {

    /**
     * Repository for accessing label data.
     */
    private final LabelRepository labelRepository;

    /**
     * Builds a Specification based on the provided search parameters.
     * Combines multiple search criteria using AND conditions.
     *
     * @param params the search parameters
     * @return the combined Specification
     */
    public Specification<Task> build(TaskParamsDTO params) {
        return withAssigneeId(params.getAssigneeId())
                .and(withLabelId(params.getLabelId()))
                .and(withTitleCont(params.getTitleCont()))
                .and(withTaskStatus(params.getStatus()));
    }

    /**
     * Creates a Specification for filtering tasks by assignee ID.
     * Returns a conjunction if assigneeId is null.
     *
     * @param assigneeId the ID of the assignee to filter by
     * @return the Specification for assignee filtering
     */
    private Specification<Task> withAssigneeId(Long assigneeId) {
        return (root, query, criteriaBuilder) -> assigneeId == null
                ? criteriaBuilder.conjunction()
                : criteriaBuilder.equal(root.get("assignee").get("id"),
                assigneeId);
    }

    /**
     * Creates a Specification for filtering tasks by label ID.
     * Returns a conjunction if labelId is null.
     * Uses JPA's isMember() predicate to check label membership.
     *
     * @param labelId the ID of the label to filter by
     * @return the Specification for label filtering
     */
    private Specification<Task> withLabelId(Long labelId) {
        return (root, query, criteriaBuilder) -> labelId == null
                ? criteriaBuilder.conjunction()
                : criteriaBuilder.isMember(labelRepository.findById(labelId).orElseThrow(),
                root.get("labels"));
    }

    /**
     * Creates a Specification for filtering tasks by status.
     * Returns a conjunction if status is null.
     *
     * @param status the status slug to filter by
     * @return the Specification for status filtering
     */
    private Specification<Task> withTaskStatus(String status) {
        return (root, query, criteriaBuilder) -> status == null
                ? criteriaBuilder.conjunction()
                : criteriaBuilder.equal(root.get("taskStatus").get("slug"), status);
    }

    /**
     * Creates a Specification for filtering tasks by title content.
     * Returns a conjunction if titleCont is null.
     * Uses case-insensitive LIKE operator for partial matching.
     *
     * @param titleCont the content to search for in task titles
     * @return the Specification for title filtering
     */
    private Specification<Task> withTitleCont(String titleCont) {
        return (root, query, criteriaBuilder) -> titleCont == null
                ? criteriaBuilder.conjunction()
                : criteriaBuilder.like(
                criteriaBuilder.lower(root.get("name")),
                "%" + titleCont + "%");
    }
}
