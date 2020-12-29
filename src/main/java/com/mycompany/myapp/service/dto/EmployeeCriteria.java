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
 * Criteria class for the {@link com.mycompany.myapp.domain.Employee} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.EmployeeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /employees?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EmployeeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private StringFilter username;

    private StringFilter fullname;

    private EmployeeCriteria teamMember;

    private EmployeeSkillCriteria skill;

    private EmployeeSkillCriteria taughtSkill;

    private EmployeeCriteria manager;

    private String globalFilter;

    public EmployeeCriteria() {}

    public EmployeeCriteria(EmployeeCriteria other) {
        this.username = other.username == null ? null : other.username.copy();
        this.fullname = other.fullname == null ? null : other.fullname.copy();
        this.teamMember = other.teamMember == null ? null : other.teamMember.copy();
        this.skill = other.skill == null ? null : other.skill.copy();
        this.taughtSkill = other.taughtSkill == null ? null : other.taughtSkill.copy();
        this.manager = other.manager == null ? null : other.manager.copy();
        this.globalFilter = other.globalFilter;
    }

    @Override
    public EmployeeCriteria copy() {
        return new EmployeeCriteria(this);
    }

    public StringFilter getUsername() {
        return username;
    }

    public void setUsername(StringFilter username) {
        this.username = username;
    }

    public StringFilter getFullname() {
        return fullname;
    }

    public void setFullname(StringFilter fullname) {
        this.fullname = fullname;
    }

    public EmployeeCriteria getTeamMember() {
        return teamMember;
    }

    public void setTeamMember(EmployeeCriteria teamMember) {
        this.teamMember = teamMember;
    }

    public EmployeeSkillCriteria getSkill() {
        return skill;
    }

    public void setSkill(EmployeeSkillCriteria skill) {
        this.skill = skill;
    }

    public EmployeeSkillCriteria getTaughtSkill() {
        return taughtSkill;
    }

    public void setTaughtSkill(EmployeeSkillCriteria taughtSkill) {
        this.taughtSkill = taughtSkill;
    }

    public EmployeeCriteria getManager() {
        return manager;
    }

    public void setManager(EmployeeCriteria manager) {
        this.manager = manager;
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
        final EmployeeCriteria that = (EmployeeCriteria) o;
        return (
            Objects.equals(username, that.username) &&
            Objects.equals(fullname, that.fullname) &&
            Objects.equals(teamMember, that.teamMember) &&
            Objects.equals(skill, that.skill) &&
            Objects.equals(taughtSkill, that.taughtSkill) &&
            Objects.equals(manager, that.manager) &&
            Objects.equals(globalFilter, that.globalFilter)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, fullname, teamMember, skill, taughtSkill, manager, globalFilter);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeCriteria{" +
            (username != null ? "username=" + username + ", " : "") +
            (fullname != null ? "fullname=" + fullname + ", " : "") +
            (teamMember != null ? "teamMember=" + teamMember + ", " : "") +
            (skill != null ? "skill=" + skill + ", " : "") +
            (taughtSkill != null ? "taughtSkill=" + taughtSkill + ", " : "") +
            (manager != null ? "manager=" + manager + ", " : "") +
            (globalFilter != null ? "globalFilter=" + globalFilter + ", " : "") +
            "}";
    }
}
