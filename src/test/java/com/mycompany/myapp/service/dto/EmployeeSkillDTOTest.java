package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.EmployeeResourceIT;
import com.mycompany.myapp.web.rest.EmployeeSkillResourceIT;
import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmployeeSkillDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeSkillDTO.class);
        EmployeeSkillDTO employeeSkillDTO1 = new EmployeeSkillDTO();
        employeeSkillDTO1.setName(EmployeeSkillResourceIT.DEFAULT_NAME);
        EmployeeDTO employeeDTO1 = new EmployeeDTO();
        employeeDTO1.setUsername(EmployeeResourceIT.DEFAULT_USERNAME);
        employeeSkillDTO1.setEmployee(employeeDTO1);
        EmployeeSkillDTO employeeSkillDTO2 = new EmployeeSkillDTO();
        assertThat(employeeSkillDTO1).isNotEqualTo(employeeSkillDTO2);
        employeeSkillDTO2.setName(EmployeeSkillResourceIT.DEFAULT_NAME);
        employeeSkillDTO2.setEmployee(employeeDTO1);
        assertThat(employeeSkillDTO1).isEqualTo(employeeSkillDTO2);
        EmployeeSkillDTO employeeSkillDTO3 = new EmployeeSkillDTO();
        employeeSkillDTO3.setName(EmployeeSkillResourceIT.UPDATED_NAME);
        EmployeeDTO employeeDTO3 = new EmployeeDTO();
        employeeDTO3.setUsername(EmployeeResourceIT.UPDATED_USERNAME);
        employeeSkillDTO3.setEmployee(employeeDTO3);
        assertThat(employeeSkillDTO1).isNotEqualTo(employeeSkillDTO3);
        EmployeeSkillDTO employeeSkillDTO4 = new EmployeeSkillDTO();
        employeeSkillDTO4.setName(null);
        employeeSkillDTO4.setEmployee(null);
        assertThat(employeeSkillDTO1).isNotEqualTo(employeeSkillDTO4);
    }
}
