package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A EmployeeSkillCertificate.
 */
@Entity
@Table(name = "employee_skill_certificate")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EmployeeSkillCertificate implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    EmployeeSkillCertificateId id;

    @NotNull
    @Column(name = "grade", nullable = false)
    private Integer grade;

    @NotNull
    @Column(name = "date", nullable = false)
    private LocalDate date;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "employeeSkillCertificates" }, allowSetters = true)
    @JoinColumn(insertable = false, updatable = false)
    private CertificateType type;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties(value = { "employeeSkillCertificates", "tasks", "employee", "teacher" }, allowSetters = true)
    @JoinColumns({ @JoinColumn(insertable = false, updatable = false), @JoinColumn(insertable = false, updatable = false) })
    private EmployeeSkill skill;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public EmployeeSkillCertificateId getId() {
        return id;
    }

    public void setId(EmployeeSkillCertificateId id) {
        this.id = id;
    }

    public EmployeeSkillCertificate id(EmployeeSkillCertificateId id) {
        this.id = id;
        return this;
    }

    public Integer getGrade() {
        return this.grade;
    }

    public EmployeeSkillCertificate grade(Integer grade) {
        this.grade = grade;
        return this;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public EmployeeSkillCertificate date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public CertificateType getType() {
        return this.type;
    }

    public EmployeeSkillCertificate type(CertificateType certificateType) {
        this.setType(certificateType);
        return this;
    }

    public void setType(CertificateType certificateType) {
        this.type = certificateType;
    }

    public EmployeeSkill getSkill() {
        return this.skill;
    }

    public EmployeeSkillCertificate skill(EmployeeSkill employeeSkill) {
        this.setSkill(employeeSkill);
        return this;
    }

    public void setSkill(EmployeeSkill employeeSkill) {
        this.skill = employeeSkill;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployeeSkillCertificate)) {
            return false;
        }
        return id != null && id.equals(((EmployeeSkillCertificate) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return Objects.hashCode(id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeSkillCertificate{" +
            "id=" + getId() +
            ", grade=" + getGrade() +
            ", date='" + getDate() + "'" +
            "}";
    }
}
