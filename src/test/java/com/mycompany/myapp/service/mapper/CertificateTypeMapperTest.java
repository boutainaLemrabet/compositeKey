package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CertificateTypeMapperTest {

    private CertificateTypeMapper certificateTypeMapper;

    @BeforeEach
    public void setUp() {
        certificateTypeMapper = new CertificateTypeMapperImpl();
    }
}
