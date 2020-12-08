package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.EmployeeResourceIT;
import com.mycompany.myapp.web.rest.EmployeeSkillResourceIT;
import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmployeeSkillCertificateDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeSkillCertificateDTO.class);
        EmployeeSkillCertificateDTO employeeSkillCertificateDTO1 = new EmployeeSkillCertificateDTO();
        CertificateTypeDTO certificateTypeDTO1 = new CertificateTypeDTO();
        certificateTypeDTO1.setId(1L);
        employeeSkillCertificateDTO1.setType(certificateTypeDTO1);
        EmployeeSkillDTO employeeSkillDTO1 = new EmployeeSkillDTO();
        employeeSkillDTO1.setName(EmployeeSkillResourceIT.DEFAULT_NAME);
        EmployeeDTO employeeDTO1 = new EmployeeDTO();
        employeeDTO1.setUsername(EmployeeResourceIT.DEFAULT_USERNAME);
        employeeSkillDTO1.setEmployee(employeeDTO1);
        employeeSkillCertificateDTO1.setSkill(employeeSkillDTO1);
        EmployeeSkillCertificateDTO employeeSkillCertificateDTO2 = new EmployeeSkillCertificateDTO();
        assertThat(employeeSkillCertificateDTO1).isNotEqualTo(employeeSkillCertificateDTO2);
        employeeSkillCertificateDTO2.setType(certificateTypeDTO1);
        employeeSkillCertificateDTO2.setSkill(employeeSkillDTO1);
        assertThat(employeeSkillCertificateDTO1).isEqualTo(employeeSkillCertificateDTO2);
        EmployeeSkillCertificateDTO employeeSkillCertificateDTO3 = new EmployeeSkillCertificateDTO();
        CertificateTypeDTO certificateTypeDTO3 = new CertificateTypeDTO();
        certificateTypeDTO3.setId(3L);
        employeeSkillCertificateDTO3.setType(certificateTypeDTO3);
        EmployeeSkillDTO employeeSkillDTO3 = new EmployeeSkillDTO();
        employeeSkillDTO3.setName(EmployeeSkillResourceIT.UPDATED_NAME);
        EmployeeDTO employeeDTO3 = new EmployeeDTO();
        employeeDTO3.setUsername(EmployeeResourceIT.UPDATED_USERNAME);
        employeeSkillDTO3.setEmployee(employeeDTO3);
        employeeSkillCertificateDTO3.setSkill(employeeSkillDTO3);
        assertThat(employeeSkillCertificateDTO1).isNotEqualTo(employeeSkillCertificateDTO3);
        EmployeeSkillCertificateDTO employeeSkillCertificateDTO4 = new EmployeeSkillCertificateDTO();
        employeeSkillCertificateDTO4.setType(null);
        employeeSkillCertificateDTO4.setSkill(null);
        assertThat(employeeSkillCertificateDTO1).isNotEqualTo(employeeSkillCertificateDTO4);
    }
}
