package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A WithIdStringDetails.
 */
@Entity
@Table(name = "with_id_string_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class WithIdStringDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "with_id_string_id")
    private String withIdStringId;

    @Column(name = "name")
    private String name;

    @JsonIgnoreProperties(value = { "withIdStringDetails" }, allowSetters = true)
    @OneToOne
    @MapsId
    @JoinColumn(name = "with_id_string_id ", insertable = false, updatable = false)
    private WithIdString withIdString;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getWithIdStringId() {
        return this.withIdStringId;
    }

    public WithIdStringDetails withIdStringId(String withIdStringId) {
        this.withIdStringId = withIdStringId;
        return this;
    }

    public void setWithIdStringId(String withIdStringId) {
        this.withIdStringId = withIdStringId;
    }

    public String getName() {
        return this.name;
    }

    public WithIdStringDetails name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public WithIdString getWithIdString() {
        return this.withIdString;
    }

    public WithIdStringDetails withIdString(WithIdString withIdString) {
        this.setWithIdString(withIdString);
        return this;
    }

    public void setWithIdString(WithIdString withIdString) {
        this.withIdString = withIdString;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WithIdStringDetails)) {
            return false;
        }
        return withIdStringId != null && withIdStringId.equals(((WithIdStringDetails) o).withIdStringId);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WithIdStringDetails{" +
            "withIdStringId='" + getWithIdStringId() + "'" +
            ", name='" + getName() + "'" +
            "}";
    }
}
