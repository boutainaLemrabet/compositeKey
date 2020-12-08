package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CertificateTypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CertificateTypeDTO.class);
        CertificateTypeDTO certificateTypeDTO1 = new CertificateTypeDTO();
        certificateTypeDTO1.setId(1L);
        CertificateTypeDTO certificateTypeDTO2 = new CertificateTypeDTO();
        assertThat(certificateTypeDTO1).isNotEqualTo(certificateTypeDTO2);
        certificateTypeDTO2.setId(1L);
        assertThat(certificateTypeDTO1).isEqualTo(certificateTypeDTO2);
        CertificateTypeDTO certificateTypeDTO3 = new CertificateTypeDTO();
        certificateTypeDTO3.setId(3L);
        assertThat(certificateTypeDTO1).isNotEqualTo(certificateTypeDTO3);
        CertificateTypeDTO certificateTypeDTO4 = new CertificateTypeDTO();
        certificateTypeDTO4.setId(null);
        assertThat(certificateTypeDTO1).isNotEqualTo(certificateTypeDTO4);
    }
}
