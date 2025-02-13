package hexlet.code.app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/welcome")
public final class WelcomeController {

    @GetMapping
    public String getWelcome() {
        return "Welcome to Spring";
    }
}
