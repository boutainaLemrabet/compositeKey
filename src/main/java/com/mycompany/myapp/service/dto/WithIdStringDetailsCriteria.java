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
 * Criteria class for the {@link com.mycompany.myapp.domain.WithIdStringDetails} entity. This class is used
 * in {@link com.mycompany.myapp.web.rest.WithIdStringDetailsResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /with-id-string-details?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class WithIdStringDetailsCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private StringFilter withIdStringId;

    private StringFilter name;

    private WithIdStringCriteria withIdString;

    private String globalFilter;

    public WithIdStringDetailsCriteria() {}

    public WithIdStringDetailsCriteria(WithIdStringDetailsCriteria other) {
        this.withIdStringId = other.withIdStringId == null ? null : other.withIdStringId.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.withIdString = other.withIdString == null ? null : other.withIdString.copy();
        this.globalFilter = other.globalFilter;
    }

    @Override
    public WithIdStringDetailsCriteria copy() {
        return new WithIdStringDetailsCriteria(this);
    }

    public StringFilter getWithIdStringId() {
        return withIdStringId;
    }

    public void setWithIdStringId(StringFilter withIdStringId) {
        this.withIdStringId = withIdStringId;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public WithIdStringCriteria getWithIdString() {
        return withIdString;
    }

    public void setWithIdString(WithIdStringCriteria withIdString) {
        this.withIdString = withIdString;
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
        final WithIdStringDetailsCriteria that = (WithIdStringDetailsCriteria) o;
        return (
            Objects.equals(withIdStringId, that.withIdStringId) &&
            Objects.equals(name, that.name) &&
            Objects.equals(withIdString, that.withIdString) &&
            Objects.equals(globalFilter, that.globalFilter)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(withIdStringId, name, withIdString, globalFilter);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WithIdStringDetailsCriteria{" +
            (withIdStringId != null ? "withIdStringId=" + withIdStringId + ", " : "") +
            (name != null ? "name=" + name + ", " : "") +
            (withIdString != null ? "withIdString=" + withIdString + ", " : "") +
            (globalFilter != null ? "globalFilter=" + globalFilter + ", " : "") +
            "}";
    }
}
