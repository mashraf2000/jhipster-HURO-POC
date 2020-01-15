package com.huro.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Country.
 */
@Entity
@Table(name = "country")
public class Country implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "country_name")
    private String countryName;

    @OneToMany(mappedBy = "country")
    private Set<Region> regions = new HashSet<>();

    @ManyToMany(mappedBy = "countries")
    @JsonIgnore
    private Set<Compliance> compliances = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public Country countryName(String countryName) {
        this.countryName = countryName;
        return this;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Set<Region> getRegions() {
        return regions;
    }

    public Country regions(Set<Region> regions) {
        this.regions = regions;
        return this;
    }

    public Country addRegion(Region region) {
        this.regions.add(region);
        region.setCountry(this);
        return this;
    }

    public Country removeRegion(Region region) {
        this.regions.remove(region);
        region.setCountry(null);
        return this;
    }

    public void setRegions(Set<Region> regions) {
        this.regions = regions;
    }

    public Set<Compliance> getCompliances() {
        return compliances;
    }

    public Country compliances(Set<Compliance> compliances) {
        this.compliances = compliances;
        return this;
    }

    public Country addCompliance(Compliance compliance) {
        this.compliances.add(compliance);
        compliance.getCountries().add(this);
        return this;
    }

    public Country removeCompliance(Compliance compliance) {
        this.compliances.remove(compliance);
        compliance.getCountries().remove(this);
        return this;
    }

    public void setCompliances(Set<Compliance> compliances) {
        this.compliances = compliances;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Country)) {
            return false;
        }
        return id != null && id.equals(((Country) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Country{" +
            "id=" + getId() +
            ", countryName='" + getCountryName() + "'" +
            "}";
    }
}
