package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.TaskDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Task} and its DTO {@link TaskDTO}.
 */
@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface TaskMapper extends EntityMapper<TaskDTO, Task> {
    @Mapping(target = "user", source = "user", qualifiedByName = "login")
    TaskDTO toDto(Task task);

    @Mapping(target = "employeeSkills", ignore = true)
    @Mapping(target = "removeEmployeeSkill", ignore = true)
    Task toEntity(TaskDTO taskDTO);

    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    TaskDTO toDtoName(Task task);
}
