package hexlet.code.service;

import hexlet.code.Repository.UserRepository;
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

@Service
@AllArgsConstructor
public final class UserService implements UserDetailsManager {

    private UserMapper userMapper;
    private UserRepository userRepository;

    public UserShowDTO create(UserCreateDTO userCreateDTO) {
        User user = userMapper.map(userCreateDTO);
        return userMapper.map(userRepository.save(user));
    }

    public List<UserShowDTO> getAll() {
        return userRepository.findAll().stream().map(userMapper::map).toList();
    }

    public UserShowDTO get(long id) {
        return userMapper.map(
                userRepository.findById(id)
                        .orElseThrow(() -> new UsernameNotFoundException("User with id = " + id + " not found")));
    }

    public UserShowDTO update(long id, UserUpdateDTO userUpdateDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User with id = " + id + " not found"));
        userMapper.map(userUpdateDTO, user);
        return userMapper.map(userRepository.save(user));
    }

    public void delete(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void createUser(UserDetails userData) {
        throw new UnsupportedOperationException("Unimplemented method 'createUser'");
    }

    @Override
    public void updateUser(UserDetails user) {
        throw new UnsupportedOperationException("Unimplemented method 'updateUser'");
    }

    @Override
    public void deleteUser(String username) {
        throw new UnsupportedOperationException("Unimplemented method 'deleteUser'");
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        throw new UnsupportedOperationException("Unimplemented method 'changePassword'");
    }

    @Override
    public boolean userExists(String username) {
        throw new UnsupportedOperationException("Unimplemented method 'userExist'");
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
