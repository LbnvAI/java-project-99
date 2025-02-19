package hexlet.code.dto.taskstatus;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TaskStatusShowDTO {
    private long id;
    private String name;
    private String slug;
    private LocalDateTime createdAt;
}
