package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.EmployeeSkillCertificate} entity.
 */
public class EmployeeSkillCertificateDTO implements Serializable {

    @NotNull
    private Integer grade;

    @NotNull
    private LocalDate date;

    private CertificateTypeDTO type;

    private EmployeeSkillDTO skill;

    public Integer getGrade() {
        return this.grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public CertificateTypeDTO getType() {
        return this.type;
    }

    public void setType(CertificateTypeDTO type) {
        this.type = type;
    }

    public EmployeeSkillDTO getSkill() {
        return this.skill;
    }

    public void setSkill(EmployeeSkillDTO skill) {
        this.skill = skill;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmployeeSkillCertificateDTO)) {
            return false;
        }

        EmployeeSkillCertificateDTO employeeSkillCertificateDTO = (EmployeeSkillCertificateDTO) o;
        if (this.type == null && this.skill == null) {
            return false;
        }
        return Objects.equals(this.type, employeeSkillCertificateDTO.type) && Objects.equals(this.skill, employeeSkillCertificateDTO.skill);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.type, this.skill);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EmployeeSkillCertificateDTO{" +
            "grade=" + getGrade() +
            ", date='" + getDate() + "'" +
            ", type=" + getType() +
            ", skill='" + getSkill() + "'" +
            "}";
    }
}
