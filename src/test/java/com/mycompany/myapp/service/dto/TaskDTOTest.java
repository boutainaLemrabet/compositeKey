package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TaskDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaskDTO.class);
        TaskDTO taskDTO1 = new TaskDTO();
        taskDTO1.setId(1L);
        TaskDTO taskDTO2 = new TaskDTO();
        assertThat(taskDTO1).isNotEqualTo(taskDTO2);
        taskDTO2.setId(1L);
        assertThat(taskDTO1).isEqualTo(taskDTO2);
        TaskDTO taskDTO3 = new TaskDTO();
        taskDTO3.setId(3L);
        assertThat(taskDTO1).isNotEqualTo(taskDTO3);
        TaskDTO taskDTO4 = new TaskDTO();
        taskDTO4.setId(null);
        assertThat(taskDTO1).isNotEqualTo(taskDTO4);
    }
}
