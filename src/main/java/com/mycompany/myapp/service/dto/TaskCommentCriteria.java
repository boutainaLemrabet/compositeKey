package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.TaskComment} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.TaskCommentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /task-comments?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TaskCommentCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter value;

    private TaskCriteria task;

    private String globalFilter;

    public TaskCommentCriteria() {}

    public TaskCommentCriteria(TaskCommentCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.value = other.value == null ? null : other.value.copy();
        this.task = other.task == null ? null : other.task.copy();
        this.globalFilter = other.globalFilter;
    }

    @Override
    public TaskCommentCriteria copy() {
        return new TaskCommentCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getValue() {
        return value;
    }

    public void setValue(StringFilter value) {
        this.value = value;
    }

    public TaskCriteria getTask() {
        return task;
    }

    public void setTask(TaskCriteria task) {
        this.task = task;
    }

    public String getGlobalFilter() {
        return globalFilter;
    }

    public void setGlobalFilter(String globalFilter) {
        this.globalFilter = globalFilter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final TaskCommentCriteria that = (TaskCommentCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(value, that.value) &&
            Objects.equals(task, that.task) &&
            Objects.equals(globalFilter, that.globalFilter)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, value, task, globalFilter);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TaskCommentCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (value != null ? "value=" + value + ", " : "") +
            (task != null ? "task=" + task + ", " : "") +
            (globalFilter != null ? "globalFilter=" + globalFilter + ", " : "") +
            "}";
    }
}
