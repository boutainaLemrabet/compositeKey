package com.mycompany.myapp.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TaskCommentMapperTest {

    private TaskCommentMapper taskCommentMapper;

    @BeforeEach
    public void setUp() {
        taskCommentMapper = new TaskCommentMapperImpl();
    }
}
