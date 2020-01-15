package com.huro.domain;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Compliance.
 */
@Entity
@Table(name = "compliance")
public class Compliance implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "date_created")
    private Instant dateCreated;

    @Column(name = "date_updated")
    private Instant dateUpdated;

    @ManyToMany
    @JoinTable(name = "compliance_region",
               joinColumns = @JoinColumn(name = "compliance_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "region_id", referencedColumnName = "id"))
    private Set<Region> regions = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "compliance_country",
               joinColumns = @JoinColumn(name = "compliance_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "country_id", referencedColumnName = "id"))
    private Set<Country> countries = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Compliance name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getDateCreated() {
        return dateCreated;
    }

    public Compliance dateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Instant getDateUpdated() {
        return dateUpdated;
    }

    public Compliance dateUpdated(Instant dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(Instant dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Set<Region> getRegions() {
        return regions;
    }

    public Compliance regions(Set<Region> regions) {
        this.regions = regions;
        return this;
    }

    public Compliance addRegion(Region region) {
        this.regions.add(region);
        region.getCompliances().add(this);
        return this;
    }

    public Compliance removeRegion(Region region) {
        this.regions.remove(region);
        region.getCompliances().remove(this);
        return this;
    }

    public void setRegions(Set<Region> regions) {
        this.regions = regions;
    }

    public Set<Country> getCountries() {
        return countries;
    }

    public Compliance countries(Set<Country> countries) {
        this.countries = countries;
        return this;
    }

    public Compliance addCountry(Country country) {
        this.countries.add(country);
        country.getCompliances().add(this);
        return this;
    }

    public Compliance removeCountry(Country country) {
        this.countries.remove(country);
        country.getCompliances().remove(this);
        return this;
    }

    public void setCountries(Set<Country> countries) {
        this.countries = countries;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Compliance)) {
            return false;
        }
        return id != null && id.equals(((Compliance) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Compliance{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            "}";
    }
}
