package hexlet.code.controller.api;

import hexlet.code.dto.user.UserCreateDTO;
import hexlet.code.dto.user.UserShowDTO;
import hexlet.code.dto.user.UserUpdateDTO;
import hexlet.code.service.UserService;
import hexlet.code.util.UserUtils;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for managing user operations.
 * Handles CRUD operations for users with proper security constraints.
 */
@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    /**
     * Service layer for user operations.
     */
    private UserService userService;
    private UserUtils userUtils;

    /**
     * Retrieves all users from the system.
     *
     * @return list of user DTOs
     */
    @GetMapping
    public ResponseEntity<List<UserShowDTO>> getAll() {
        List<UserShowDTO> result = userService.getAll();
        return ResponseEntity.ok()
                .header("X-Total-Count", String.valueOf(result.size()))
                .body(result);
    }

    /**
     * Retrieves a user by ID.
     *
     * @param id the user ID to retrieve
     * @return user DTO
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserShowDTO get(@PathVariable long id) {
        return userService.get(id);
    }

    /**
     * Creates a new user.
     *
     * @param userCreateDTO user creation data
     * @return created user DTO
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserShowDTO create(@Valid @RequestBody UserCreateDTO userCreateDTO) {
        return userService.create(userCreateDTO);
    }

    /**
     * Updates an existing user.
     * Only the user themselves can update their own information.
     *
     * @param id            user ID to update
     * @param userUpdateDTO user update data
     * @return updated user DTO
     */
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize(value = "@userUtils.getCurrentUser().getId() == #id")
    public UserShowDTO update(@PathVariable long id, @Valid @RequestBody UserUpdateDTO userUpdateDTO) {
        return userService.update(id, userUpdateDTO);
    }

    /**
     * Deletes a user.
     * Only the user themselves can delete their own account.
     *
     * @param id user ID to delete
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize(value = "@userUtils.getCurrentUser().getId() == #id")
    public void delete(@PathVariable long id) throws AccessDeniedException {
        userService.delete(id);
    }
}
