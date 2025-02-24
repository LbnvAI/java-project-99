package hexlet.code.component;

import hexlet.code.repository.TaskStatusRepository;
import hexlet.code.repository.UserRepository;
import hexlet.code.dto.user.UserCreateDTO;
import hexlet.code.model.TaskStatus;
import hexlet.code.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public final class DataInitializer implements ApplicationRunner {

    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final TaskStatusRepository taskStatusRepository;

    @Override
    public void run(ApplicationArguments args) {

        String email = "hexlet@example.com";
        if (userRepository.findByEmail(email).isEmpty()) {
            UserCreateDTO userCreateDTO = new UserCreateDTO();
            userCreateDTO.setEmail(email);
            userCreateDTO.setPassword("qwerty");
            userService.create(userCreateDTO);
        }

        List<TaskStatus> statuses = new ArrayList<>(List.of(
                new TaskStatus("draft", "draft"),
                new TaskStatus("on check", "to_review"),
                new TaskStatus("to fix", "to_be_fixed"),
                new TaskStatus("publish", "to_publish"),
                new TaskStatus("published", "published")));

        for (TaskStatus status : statuses) {
            if (taskStatusRepository.findByName(status.getName()).isEmpty()) {
                taskStatusRepository.save(status);
            }
        }
    }
}
