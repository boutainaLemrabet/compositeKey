package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.TaskCommentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TaskComment} and its DTO {@link TaskCommentDTO}.
 */
@Mapper(componentModel = "spring", uses = { TaskMapper.class })
public interface TaskCommentMapper extends EntityMapper<TaskCommentDTO, TaskComment> {
    @Mapping(target = "task", source = "task", qualifiedByName = "name")
    TaskCommentDTO toDto(TaskComment taskComment);
}
