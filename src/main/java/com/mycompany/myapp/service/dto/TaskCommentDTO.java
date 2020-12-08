package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.TaskComment} entity.
 */
public class TaskCommentDTO implements Serializable {

    private Long id;

    @NotNull
    private String value;

    private TaskDTO task;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public TaskDTO getTask() {
        return this.task;
    }

    public void setTask(TaskDTO task) {
        this.task = task;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TaskCommentDTO)) {
            return false;
        }

        TaskCommentDTO taskCommentDTO = (TaskCommentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, taskCommentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaskCommentDTO{" +
            "id=" + getId() +
            ", value='" + getValue() + "'" +
            ", task=" + getTask() +
            "}";
    }
}
