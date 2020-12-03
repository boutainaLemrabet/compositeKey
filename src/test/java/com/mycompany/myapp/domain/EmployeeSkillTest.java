package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.EmployeeResourceIT;
import com.mycompany.myapp.web.rest.EmployeeSkillResourceIT;
import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmployeeSkillTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeSkill.class);
        EmployeeSkill employeeSkill1 = new EmployeeSkill();
        employeeSkill1.setId(new EmployeeSkillId(EmployeeSkillResourceIT.DEFAULT_NAME, EmployeeResourceIT.DEFAULT_USERNAME));
        EmployeeSkill employeeSkill2 = new EmployeeSkill();
        employeeSkill2.setId(new EmployeeSkillId(EmployeeSkillResourceIT.DEFAULT_NAME, EmployeeResourceIT.DEFAULT_USERNAME));
        assertThat(employeeSkill1).isEqualTo(employeeSkill2);
        employeeSkill2.setId(new EmployeeSkillId(EmployeeSkillResourceIT.UPDATED_NAME, EmployeeResourceIT.UPDATED_USERNAME));
        assertThat(employeeSkill1).isNotEqualTo(employeeSkill2);
        employeeSkill1.setId(null);
        assertThat(employeeSkill1).isNotEqualTo(employeeSkill2);
    }
}
