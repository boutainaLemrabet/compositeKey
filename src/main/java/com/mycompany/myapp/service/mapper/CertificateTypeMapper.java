package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.*;
import com.mycompany.myapp.service.dto.CertificateTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CertificateType} and its DTO {@link CertificateTypeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CertificateTypeMapper extends EntityMapper<CertificateTypeDTO, CertificateType> {
    @Mapping(target = "employeeSkillCertificates", ignore = true)
    @Mapping(target = "removeEmployeeSkillCertificate", ignore = true)
    CertificateType toEntity(CertificateTypeDTO certificateTypeDTO);

    @Named("name")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    CertificateTypeDTO toDtoName(CertificateType certificateType);
}
