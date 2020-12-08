package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LocalDateFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.mycompany.myapp.domain.EmployeeSkillCertificate} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.EmployeeSkillCertificateResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /employee-skill-certificates?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EmployeeSkillCertificateCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private IntegerFilter grade;

    private LocalDateFilter date;

    private CertificateTypeCriteria type;

    private EmployeeSkillCriteria skill;

    public EmployeeSkillCertificateCriteria() {}

    public EmployeeSkillCertificateCriteria(EmployeeSkillCertificateCriteria other) {
        this.grade = other.grade == null ? null : other.grade.copy();
        this.date = other.date == null ? null : other.date.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.skill = other.skill == null ? null : other.skill.copy();
    }

    @Override
    public EmployeeSkillCertificateCriteria copy() {
        return new EmployeeSkillCertificateCriteria(this);
    }

    public IntegerFilter getGrade() {
        return grade;
    }

    public void setGrade(IntegerFilter grade) {
        this.grade = grade;
    }

    public LocalDateFilter getDate() {
        return date;
    }

    public void setDate(LocalDateFilter date) {
        this.date = date;
    }

    public CertificateTypeCriteria getType() {
        return type;
    }

    public void setType(CertificateTypeCriteria type) {
        this.type = type;
    }

    public EmployeeSkillCriteria getSkill() {
        return skill;
    }

    public void setSkill(EmployeeSkillCriteria skill) {
        this.skill = skill;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final EmployeeSkillCertificateCriteria that = (EmployeeSkillCertificateCriteria) o;
        return (
            Objects.equals(grade, that.grade) &&
            Objects.equals(date, that.date) &&
            Objects.equals(type, that.type) &&
            Objects.equals(skill, that.skill)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(grade, date, type, skill);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeSkillCertificateCriteria{" +
            (grade != null ? "grade=" + grade + ", " : "") +
            (date != null ? "date=" + date + ", " : "") +
            (type != null ? "type=" + type + ", " : "") +
            (skill != null ? "skill=" + skill + ", " : "") +
            "}";
    }
}
