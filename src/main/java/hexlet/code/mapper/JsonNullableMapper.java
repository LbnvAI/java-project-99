package hexlet.code.mapper;

import org.mapstruct.Condition;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.openapitools.jackson.nullable.JsonNullable;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class JsonNullableMapper {

    /**
     * Wraps an entity in a JsonNullable container.
     *
     * @param <T> The type of the entity to wrap
     * @param entity The entity to wrap in JsonNullable
     * @return A JsonNullable instance containing the entity
     */
    public <T> JsonNullable<T> wrap(T entity) {
        return JsonNullable.of(entity);
    }

    /**
     * Unwraps a JsonNullable object to its underlying value.
     * Returns null if the JsonNullable instance is null or empty.
     *
     * @param <T> The type of the wrapped entity
     * @param jsonNullable The JsonNullable instance to unwrap
     * @return The unwrapped entity, or null if not present
     */
    public <T> T unwrap(JsonNullable<T> jsonNullable) {
        return jsonNullable == null ? null : jsonNullable.orElse(null);
    }

    /**
     * Checks if a JsonNullable instance contains a present value.
     *
     * @param <T> The type of the wrapped entity
     * @param nullable The JsonNullable instance to check
     * @return true if the value is present, false otherwise
     */
    @Condition
    public <T> boolean isPresent(JsonNullable<T> nullable) {
        return nullable != null && nullable.isPresent();
    }
}
