package hexlet.code.component;

import hexlet.code.Repository.UserRepository;
import hexlet.code.dto.user.UserCreateDTO;
import hexlet.code.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public final class DataInitializer implements ApplicationRunner {

    private final UserRepository userRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {

        String email = "hexlet@example.com";
        if (userRepository.findByEmail(email).isEmpty()) {
            UserCreateDTO userCreateDTO = new UserCreateDTO();
            userCreateDTO.setEmail(email);
            userCreateDTO.setPassword(passwordEncoder.encode("qwerty"));
            userService.create(userCreateDTO);
        }
    }
}
