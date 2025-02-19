package hexlet.code.mapper;

import hexlet.code.dto.user.UserCreateDTO;
import hexlet.code.dto.user.UserShowDTO;
import hexlet.code.dto.user.UserUpdateDTO;
import hexlet.code.model.User;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.BeforeMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Abstract mapper class for handling user entity transformations.
 * Uses Spring's component model and implements null handling strategies.
 *
 * @author [Your Name]
 * @since [Version]
 */
@Mapper(
        uses = { JsonNullableMapper.class },
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class UserMapper {

    /**
     * Password encoder for secure password hashing.
     */
    @Autowired
    private PasswordEncoder encoder;

    /**
     * Pre-mapping hook that encrypts the password before mapping.
     * This method is automatically called before any mapping operation
     * that involves a UserCreateDTO object.
     *
     * @param data the UserCreateDTO object containing the password to encrypt
     */
    @BeforeMapping
    public void encryptPassword(UserCreateDTO data) {
        var password = data.getPassword();
        data.setPassword(encoder.encode(password));
    }

    /**
     * Maps a UserCreateDTO to a User entity, transforming the password to a password digest.
     *
     * @param userCreateDTO the DTO containing user creation data
     * @return the mapped User entity with encrypted password
     */
    @Mapping(source = "password", target = "passwordDigest")
    public abstract User map(UserCreateDTO userCreateDTO);

    /**
     * Updates an existing User entity with data from UserUpdateDTO.
     * Transforms the password to a password digest during the update process.
     *
     * @param userUpdateDTO the DTO containing user update data
     * @param user the target User entity to update
     */
    @Mapping(source = "password", target = "passwordDigest")
    public abstract void map(UserUpdateDTO userUpdateDTO, @MappingTarget User user);

    /**
     * Maps a User entity to a UserShowDTO for display purposes.
     *
     * @param user the User entity to transform
     * @return the mapped UserShowDTO
     */
    public abstract UserShowDTO map(User user);
}
