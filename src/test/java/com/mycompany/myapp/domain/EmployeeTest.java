package com.mycompany.myapp.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.EmployeeResourceIT;
import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmployeeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Employee.class);
        Employee employee1 = new Employee();
        employee1.setUsername(EmployeeResourceIT.DEFAULT_USERNAME);
        Employee employee2 = new Employee();
        employee2.setUsername(EmployeeResourceIT.DEFAULT_USERNAME);
        assertThat(employee1).isEqualTo(employee2);
        employee2.setUsername(EmployeeResourceIT.UPDATED_USERNAME);
        assertThat(employee1).isNotEqualTo(employee2);
        employee1.setUsername(null);
        assertThat(employee1).isNotEqualTo(employee2);
    }
}
