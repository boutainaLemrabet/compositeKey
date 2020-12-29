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
 * Criteria class for the {@link com.mycompany.myapp.domain.EmployeeSkill} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.EmployeeSkillResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /employee-skills?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EmployeeSkillCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private StringFilter name;

    private IntegerFilter level;

    private EmployeeSkillCertificateCriteria employeeSkillCertificate;

    private TaskCriteria task;

    private EmployeeCriteria employee;

    private EmployeeCriteria teacher;

    private String globalFilter;

    public EmployeeSkillCriteria() {}

    public EmployeeSkillCriteria(EmployeeSkillCriteria other) {
        this.name = other.name == null ? null : other.name.copy();
        this.level = other.level == null ? null : other.level.copy();
        this.employeeSkillCertificate = other.employeeSkillCertificate == null ? null : other.employeeSkillCertificate.copy();
        this.task = other.task == null ? null : other.task.copy();
        this.employee = other.employee == null ? null : other.employee.copy();
        this.teacher = other.teacher == null ? null : other.teacher.copy();
        this.globalFilter = other.globalFilter;
    }

    @Override
    public EmployeeSkillCriteria copy() {
        return new EmployeeSkillCriteria(this);
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public IntegerFilter getLevel() {
        return level;
    }

    public void setLevel(IntegerFilter level) {
        this.level = level;
    }

    public EmployeeSkillCertificateCriteria getEmployeeSkillCertificate() {
        return employeeSkillCertificate;
    }

    public void setEmployeeSkillCertificate(EmployeeSkillCertificateCriteria employeeSkillCertificate) {
        this.employeeSkillCertificate = employeeSkillCertificate;
    }

    public TaskCriteria getTask() {
        return task;
    }

    public void setTask(TaskCriteria task) {
        this.task = task;
    }

    public EmployeeCriteria getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeCriteria employee) {
        this.employee = employee;
    }

    public EmployeeCriteria getTeacher() {
        return teacher;
    }

    public void setTeacher(EmployeeCriteria teacher) {
        this.teacher = teacher;
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
        final EmployeeSkillCriteria that = (EmployeeSkillCriteria) o;
        return (
            Objects.equals(name, that.name) &&
            Objects.equals(level, that.level) &&
            Objects.equals(employeeSkillCertificate, that.employeeSkillCertificate) &&
            Objects.equals(task, that.task) &&
            Objects.equals(employee, that.employee) &&
            Objects.equals(teacher, that.teacher) &&
            Objects.equals(globalFilter, that.globalFilter)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, level, employeeSkillCertificate, task, employee, teacher);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeSkillCriteria{" +
            (name != null ? "name=" + name + ", " : "") +
            (level != null ? "level=" + level + ", " : "") +
            (employeeSkillCertificate != null ? "employeeSkillCertificate=" + employeeSkillCertificate + ", " : "") +
            (task != null ? "task=" + task + ", " : "") +
            (employee != null ? "employee=" + employee + ", " : "") +
            (teacher != null ? "teacher=" + teacher + ", " : "") +
            (globalFilter != null ? "globalFilter=" + globalFilter + ", " : "") +
            "}";
    }
}
