package com.mycompany.myapp.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.mycompany.myapp.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TaskCommentDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaskCommentDTO.class);
        TaskCommentDTO taskCommentDTO1 = new TaskCommentDTO();
        taskCommentDTO1.setId(1L);
        TaskCommentDTO taskCommentDTO2 = new TaskCommentDTO();
        assertThat(taskCommentDTO1).isNotEqualTo(taskCommentDTO2);
        taskCommentDTO2.setId(1L);
        assertThat(taskCommentDTO1).isEqualTo(taskCommentDTO2);
        TaskCommentDTO taskCommentDTO3 = new TaskCommentDTO();
        taskCommentDTO3.setId(3L);
        assertThat(taskCommentDTO1).isNotEqualTo(taskCommentDTO3);
        TaskCommentDTO taskCommentDTO4 = new TaskCommentDTO();
        taskCommentDTO4.setId(null);
        assertThat(taskCommentDTO1).isNotEqualTo(taskCommentDTO4);
    }
}
