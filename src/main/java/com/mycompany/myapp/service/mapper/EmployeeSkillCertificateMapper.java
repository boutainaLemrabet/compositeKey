package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.EmployeeSkillCertificateDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link EmployeeSkillCertificate} and its DTO {@link EmployeeSkillCertificateDTO}.
 */
@Mapper(componentModel = "spring", uses = { CertificateTypeMapper.class, EmployeeSkillMapper.class })
public interface EmployeeSkillCertificateMapper extends EntityMapper<EmployeeSkillCertificateDTO, EmployeeSkillCertificate> {
    @Mapping(target = "type", source = "type", qualifiedByName = "name")
    @Mapping(target = "skill", source = "skill", qualifiedByName = "name")
    EmployeeSkillCertificateDTO toDto(EmployeeSkillCertificate employeeSkillCertificate);

    @Mapping(target = "id.typeId", source = "type.id")
    @Mapping(target = "id.skillName", source = "skill.name")
    @Mapping(target = "id.skillEmployeeUsername", source = "skill.employee.username")
    EmployeeSkillCertificate toEntity(EmployeeSkillCertificateDTO employeeSkillCertificateDTO);
}
