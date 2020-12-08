package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EmployeeSkillCertificateMapperTest {

    private EmployeeSkillCertificateMapper employeeSkillCertificateMapper;

    @BeforeEach
    public void setUp() {
        employeeSkillCertificateMapper = new EmployeeSkillCertificateMapperImpl();
    }
}
