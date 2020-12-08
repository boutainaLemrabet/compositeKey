package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.WithIdStringDetails} entity.
 */
public class WithIdStringDetailsDTO implements Serializable {

    private String withIdStringId;

    private String name;

    private WithIdStringDTO withIdString;

    public String getWithIdStringId() {
        return this.withIdStringId;
    }

    public void setWithIdStringId(String withIdStringId) {
        this.withIdStringId = withIdStringId;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public WithIdStringDTO getWithIdString() {
        return this.withIdString;
    }

    public void setWithIdString(WithIdStringDTO withIdString) {
        this.withIdString = withIdString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WithIdStringDetailsDTO)) {
            return false;
        }

        WithIdStringDetailsDTO withIdStringDetailsDTO = (WithIdStringDetailsDTO) o;
        if (this.withIdStringId == null) {
            return false;
        }
        return Objects.equals(this.withIdStringId, withIdStringDetailsDTO.withIdStringId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.withIdStringId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WithIdStringDetailsDTO{" +
            "withIdStringId='" + getWithIdStringId() + "'" +
            ", name='" + getName() + "'" +
            ", withIdString='" + getWithIdString() + "'" +
            "}";
    }
}
