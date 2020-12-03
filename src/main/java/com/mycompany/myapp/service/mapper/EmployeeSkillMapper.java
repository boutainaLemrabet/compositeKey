package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.EmployeeSkillDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EmployeeSkill} and its DTO {@link EmployeeSkillDTO}.
 */
@Mapper(componentModel = "spring", uses = { TaskMapper.class, EmployeeMapper.class })
public interface EmployeeSkillMapper extends EntityMapper<EmployeeSkillDTO, EmployeeSkill> {
    @Mapping(target = "employee", source = "employee", qualifiedByName = "fullname")
    @Mapping(target = "teacher", source = "teacher", qualifiedByName = "fullname")
    EmployeeSkillDTO toDto(EmployeeSkill employeeSkill);

    @Mapping(target = "id.name", source = "name")
    @Mapping(target = "id.employeeUsername", source = "employee.username")
    @Mapping(target = "employeeSkillCertificates", ignore = true)
    @Mapping(target = "removeEmployeeSkillCertificate", ignore = true)
    @Mapping(target = "removeTask", ignore = true)
    EmployeeSkill toEntity(EmployeeSkillDTO employeeSkillDTO);

    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "name", source = "name")
    @Mapping(target = "employee", source = "employee", qualifiedByName = "fullname")
    EmployeeSkillDTO toDtoName(EmployeeSkill employeeSkill);
}
