package hexlet.code.mapper;

import hexlet.code.model.BaseEntity;
import jakarta.persistence.EntityManager;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.TargetType;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class ReferenceMapper {
    @Autowired
    private EntityManager entityManager;

    /**
     * Converts an entity ID to its corresponding entity instance.
     *
     * @param <T> The type of entity to retrieve (must extend BaseEntity)
     * @param id The ID of the entity to find
     * @param entityClass The class of the entity to find
     * @return The entity instance if ID is not null, null otherwise
     */
    public <T extends BaseEntity> T idToEntity(Long id, @TargetType Class<T> entityClass) {
        return id != null ? entityManager.find(entityClass, id) : null;
    }
}
