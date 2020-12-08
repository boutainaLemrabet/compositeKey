package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import com.mycompany.myapp.web.rest.WithIdStringResourceIT;
import org.junit.jupiter.api.Test;

class WithIdStringDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(WithIdStringDTO.class);
        WithIdStringDTO withIdStringDTO1 = new WithIdStringDTO();
        withIdStringDTO1.setId(WithIdStringResourceIT.DEFAULT_ID);
        WithIdStringDTO withIdStringDTO2 = new WithIdStringDTO();
        assertThat(withIdStringDTO1).isNotEqualTo(withIdStringDTO2);
        withIdStringDTO2.setId(WithIdStringResourceIT.DEFAULT_ID);
        assertThat(withIdStringDTO1).isEqualTo(withIdStringDTO2);
        WithIdStringDTO withIdStringDTO3 = new WithIdStringDTO();
        withIdStringDTO3.setId(WithIdStringResourceIT.UPDATED_ID);
        assertThat(withIdStringDTO1).isNotEqualTo(withIdStringDTO3);
        WithIdStringDTO withIdStringDTO4 = new WithIdStringDTO();
        withIdStringDTO4.setId(null);
        assertThat(withIdStringDTO1).isNotEqualTo(withIdStringDTO4);
    }
}
