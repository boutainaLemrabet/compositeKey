package com.mycompany.myapp.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.validation.constraints.*;

/**
 * A DTO for the {@link com.mycompany.myapp.domain.WithIdString} entity.
 */
public class WithIdStringDTO implements Serializable {

    @NotNull
    private String id;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WithIdStringDTO)) {
            return false;
        }

        WithIdStringDTO withIdStringDTO = (WithIdStringDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, withIdStringDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WithIdStringDTO{" +
            "id='" + getId() + "'" +
            "}";
    }
}
