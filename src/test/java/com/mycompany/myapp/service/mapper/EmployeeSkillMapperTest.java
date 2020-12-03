package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EmployeeSkillMapperTest {

    private EmployeeSkillMapper employeeSkillMapper;

    @BeforeEach
    public void setUp() {
        employeeSkillMapper = new EmployeeSkillMapperImpl();
    }
}
