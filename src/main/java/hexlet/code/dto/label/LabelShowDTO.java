package hexlet.code.dto.label;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class LabelShowDTO {
    private Long id;
    private String name;
    private LocalDateTime createdAt;
}
