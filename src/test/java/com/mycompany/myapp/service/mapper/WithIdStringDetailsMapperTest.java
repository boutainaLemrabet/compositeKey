package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WithIdStringDetailsMapperTest {

    private WithIdStringDetailsMapper withIdStringDetailsMapper;

    @BeforeEach
    public void setUp() {
        withIdStringDetailsMapper = new WithIdStringDetailsMapperImpl();
    }
}
