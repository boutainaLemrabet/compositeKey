package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CertificateType.
 */
@Entity
@Table(name = "certificate_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CertificateType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "type")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "type", "skill" }, allowSetters = true)
    private Set<EmployeeSkillCertificate> employeeSkillCertificates = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CertificateType id(Long id) {
        this.id = id;
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public CertificateType name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<EmployeeSkillCertificate> getEmployeeSkillCertificates() {
        return this.employeeSkillCertificates;
    }

    public CertificateType employeeSkillCertificates(Set<EmployeeSkillCertificate> employeeSkillCertificates) {
        this.setEmployeeSkillCertificates(employeeSkillCertificates);
        return this;
    }

    public CertificateType addEmployeeSkillCertificate(EmployeeSkillCertificate employeeSkillCertificate) {
        this.employeeSkillCertificates.add(employeeSkillCertificate);
        employeeSkillCertificate.setType(this);
        return this;
    }

    public CertificateType removeEmployeeSkillCertificate(EmployeeSkillCertificate employeeSkillCertificate) {
        this.employeeSkillCertificates.remove(employeeSkillCertificate);
        employeeSkillCertificate.setType(null);
        return this;
    }

    public void setEmployeeSkillCertificates(Set<EmployeeSkillCertificate> employeeSkillCertificates) {
        if (this.employeeSkillCertificates != null) {
            this.employeeSkillCertificates.forEach(employeeSkillCertificate -> employeeSkillCertificate.setType(null));
        }
        if (employeeSkillCertificates != null) {
            employeeSkillCertificates.forEach(employeeSkillCertificate -> employeeSkillCertificate.setType(this));
        }
        this.employeeSkillCertificates = employeeSkillCertificates;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CertificateType)) {
            return false;
        }
        return id != null && id.equals(((CertificateType) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CertificateType{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
