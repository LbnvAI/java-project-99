package hexlet.code.service;

import hexlet.code.repository.UserRepository;
import hexlet.code.dto.user.UserCreateDTO;
import hexlet.code.dto.user.UserShowDTO;
import hexlet.code.dto.user.UserUpdateDTO;
import hexlet.code.mapper.UserMapper;
import hexlet.code.model.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class responsible for managing user operations
 * and implementing Spring Security's UserDetailsManager interface.
 * Handles CRUD operations for users and provides authentication functionality.
 */
@Service
@AllArgsConstructor
public class UserService implements UserDetailsManager {

    /**
     * Mapper for converting between user entities and DTOs.
     */
    private UserMapper userMapper;

    /**
     * Repository for database operations on user entities.
     */
    private UserRepository userRepository;

    /**
     * Creates a new user from the provided DTO.
     * Maps the DTO to a User entity, saves it to the database, and returns the result as a DTO.
     *
     * @param userCreateDTO the DTO containing user creation data
     * @return the created user as a DTO
     */
    public UserShowDTO create(UserCreateDTO userCreateDTO) {
        User user = userMapper.map(userCreateDTO);
        return userMapper.map(userRepository.save(user));
    }

    /**
     * Retrieves all users from the database.
     * Maps each User entity to a UserShowDTO and returns as a list.
     *
     * @return list of all users as DTOs
     */
    public List<UserShowDTO> getAll() {
        return userRepository.findAll().stream().map(userMapper::map).toList();
    }

    /**
     * Retrieves a user by ID.
     * Throws UsernameNotFoundException if the user is not found.
     *
     * @param id the ID of the user to retrieve
     * @return the user as a DTO
     * @throws UsernameNotFoundException if the user is not found
     */
    public UserShowDTO get(long id) {
        return userMapper.map(
                userRepository.findById(id)
                        .orElseThrow(() -> new UsernameNotFoundException("User with id = " + id + " not found")));
    }

    /**
     * Updates an existing user.
     * Throws UsernameNotFoundException if the user is not found.
     *
     * @param id the ID of the user to update
     * @param userUpdateDTO the DTO containing update data
     * @return the updated user as a DTO
     * @throws UsernameNotFoundException if the user is not found
     */
    public UserShowDTO update(long id, UserUpdateDTO userUpdateDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User with id = " + id + " not found"));
        userMapper.update(userUpdateDTO, user);
        return userMapper.map(userRepository.save(user));
    }

    /**
     * Deletes a user by ID.
     *
     * @param id the ID of the user to delete
     */
    public void delete(long id) {
        userRepository.deleteById(id);
    }

    /**
     * Creates a new user with the given UserDetails.
     * Subclasses must implement this method to provide their specific user creation logic.
     *
     * @param userData the UserDetails to create
     * @throws UnsupportedOperationException if not implemented by subclass
     */
    @Override
    public void createUser(UserDetails userData) {
        throw new UnsupportedOperationException("Unimplemented method 'createUser'");
    }

    /**
     * Updates an existing user with the given UserDetails.
     * Subclasses must implement this method to provide their specific user update logic.
     *
     * @param user the UserDetails to update
     * @throws UnsupportedOperationException if not implemented by subclass
     */
    @Override
    public void updateUser(UserDetails user) {
        throw new UnsupportedOperationException("Unimplemented method 'updateUser'");
    }

    /**
     * Deletes a user by username.
     * Subclasses must implement this method to provide their specific user deletion logic.
     *
     * @param username the username of the user to delete
     * @throws UnsupportedOperationException if not implemented by subclass
     */
    @Override
    public void deleteUser(String username) {
        throw new UnsupportedOperationException("Unimplemented method 'deleteUser'");
    }

    /**
     * Changes the password for a user.
     * Subclasses must implement this method to provide their specific password change logic.
     *
     * @param oldPassword the current password
     * @param newPassword the new password
     * @throws UnsupportedOperationException if not implemented by subclass
     */
    @Override
    public void changePassword(String oldPassword, String newPassword) {
        throw new UnsupportedOperationException("Unimplemented method 'changePassword'");
    }

    /**
     * Checks if a user exists with the given username.
     * Subclasses must implement this method to provide their specific user existence check logic.
     *
     * @param username the username to check
     * @return true if the user exists, false otherwise
     * @throws UnsupportedOperationException if not implemented by subclass
     */
    @Override
    public boolean userExists(String username) {
        throw new UnsupportedOperationException("Unimplemented method 'userExist'");
    }

    /**
     * Loads a user by their username (email) for authentication purposes.
     * Throws UsernameNotFoundException if the user is not found.
     *
     * @param username the username (email) to load
     * @return the user as UserDetails
     * @throws UsernameNotFoundException if the user is not found
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
