package hexlet.code.dto.user;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public final class UserShowDTO {
    private long id;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDateTime createdAt;
}
