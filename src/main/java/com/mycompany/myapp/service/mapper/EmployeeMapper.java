package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.EmployeeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Employee} and its DTO {@link EmployeeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface EmployeeMapper extends EntityMapper<EmployeeDTO, Employee> {
    @Mapping(target = "manager", source = "manager", qualifiedByName = "fullname")
    EmployeeDTO toDto(Employee employee);

    @Mapping(target = "teamMembers", ignore = true)
    @Mapping(target = "removeTeamMember", ignore = true)
    @Mapping(target = "skills", ignore = true)
    @Mapping(target = "removeSkill", ignore = true)
    @Mapping(target = "taughtSkills", ignore = true)
    @Mapping(target = "removeTaughtSkill", ignore = true)
    Employee toEntity(EmployeeDTO employeeDTO);

    @Named("fullname")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "username", source = "username")
    @Mapping(target = "fullname", source = "fullname")
    EmployeeDTO toDtoFullname(Employee employee);
}
