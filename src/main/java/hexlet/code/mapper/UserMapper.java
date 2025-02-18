package hexlet.code.mapper;

import hexlet.code.dto.user.UserCreateDTO;
import hexlet.code.dto.user.UserShowDTO;
import hexlet.code.dto.user.UserUpdateDTO;
import hexlet.code.model.User;
import org.mapstruct.*;

@Mapper(
        uses = { JsonNullableMapper.class },
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public abstract class UserMapper {

    @Mapping(source = "password", target = "passwordDigest")
    public abstract User map(UserCreateDTO userCreateDTO);

    @Mapping(source = "password", target = "passwordDigest")
    public abstract void map(UserUpdateDTO userUpdateDTO, @MappingTarget User user);

    public abstract UserShowDTO map(User user);
}
