package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Employee.
 */
@Entity
@Table(name = "employee")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @NotNull
    @Column(name = "username", nullable = false)
    private String username;

    @NotNull
    @Column(name = "fullname", nullable = false)
    private String fullname;

    @OneToMany(mappedBy = "manager")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "teamMembers", "skills", "taughtSkills", "manager" }, allowSetters = true)
    private Set<Employee> teamMembers = new HashSet<>();

    @OneToMany(mappedBy = "employee")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "employeeSkillCertificates", "tasks", "employee", "teacher" }, allowSetters = true)
    private Set<EmployeeSkill> skills = new HashSet<>();

    @OneToMany(mappedBy = "teacher")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "employeeSkillCertificates", "tasks", "employee", "teacher" }, allowSetters = true)
    private Set<EmployeeSkill> taughtSkills = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "teamMembers", "skills", "taughtSkills", "manager" }, allowSetters = true)
    private Employee manager;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getUsername() {
        return this.username;
    }

    public Employee username(String username) {
        this.username = username;
        return this;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return this.fullname;
    }

    public Employee fullname(String fullname) {
        this.fullname = fullname;
        return this;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Set<Employee> getTeamMembers() {
        return this.teamMembers;
    }

    public Employee teamMembers(Set<Employee> employees) {
        this.setTeamMembers(employees);
        return this;
    }

    public Employee addTeamMember(Employee employee) {
        this.teamMembers.add(employee);
        employee.setManager(this);
        return this;
    }

    public Employee removeTeamMember(Employee employee) {
        this.teamMembers.remove(employee);
        employee.setManager(null);
        return this;
    }

    public void setTeamMembers(Set<Employee> employees) {
        if (this.teamMembers != null) {
            this.teamMembers.forEach(employee -> employee.setManager(null));
        }
        if (employees != null) {
            employees.forEach(employee -> employee.setManager(this));
        }
        this.teamMembers = employees;
    }

    public Set<EmployeeSkill> getSkills() {
        return this.skills;
    }

    public Employee skills(Set<EmployeeSkill> employeeSkills) {
        this.setSkills(employeeSkills);
        return this;
    }

    public Employee addSkill(EmployeeSkill employeeSkill) {
        this.skills.add(employeeSkill);
        employeeSkill.setEmployee(this);
        return this;
    }

    public Employee removeSkill(EmployeeSkill employeeSkill) {
        this.skills.remove(employeeSkill);
        employeeSkill.setEmployee(null);
        return this;
    }

    public void setSkills(Set<EmployeeSkill> employeeSkills) {
        if (this.skills != null) {
            this.skills.forEach(employeeSkill -> employeeSkill.setEmployee(null));
        }
        if (employeeSkills != null) {
            employeeSkills.forEach(employeeSkill -> employeeSkill.setEmployee(this));
        }
        this.skills = employeeSkills;
    }

    public Set<EmployeeSkill> getTaughtSkills() {
        return this.taughtSkills;
    }

    public Employee taughtSkills(Set<EmployeeSkill> employeeSkills) {
        this.setTaughtSkills(employeeSkills);
        return this;
    }

    public Employee addTaughtSkill(EmployeeSkill employeeSkill) {
        this.taughtSkills.add(employeeSkill);
        employeeSkill.setTeacher(this);
        return this;
    }

    public Employee removeTaughtSkill(EmployeeSkill employeeSkill) {
        this.taughtSkills.remove(employeeSkill);
        employeeSkill.setTeacher(null);
        return this;
    }

    public void setTaughtSkills(Set<EmployeeSkill> employeeSkills) {
        if (this.taughtSkills != null) {
            this.taughtSkills.forEach(employeeSkill -> employeeSkill.setTeacher(null));
        }
        if (employeeSkills != null) {
            employeeSkills.forEach(employeeSkill -> employeeSkill.setTeacher(this));
        }
        this.taughtSkills = employeeSkills;
    }

    public Employee getManager() {
        return this.manager;
    }

    public Employee manager(Employee employee) {
        this.setManager(employee);
        return this;
    }

    public void setManager(Employee employee) {
        this.manager = employee;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Employee)) {
            return false;
        }
        return username != null && username.equals(((Employee) o).username);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return Objects.hashCode(username);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Employee{" +
            "username='" + getUsername() + "'" +
            ", fullname='" + getFullname() + "'" +
            "}";
    }
}
