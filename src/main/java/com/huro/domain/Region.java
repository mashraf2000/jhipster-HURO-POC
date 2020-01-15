package com.huro.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Region.
 */
@Entity
@Table(name = "region")
public class Region implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "region_name")
    private String regionName;

    @OneToMany(mappedBy = "region")
    private Set<EntitySeekingFund> entitySeekingFunds = new HashSet<>();

    @OneToMany(mappedBy = "region")
    private Set<Investor> investors = new HashSet<>();

    @OneToMany(mappedBy = "region")
    private Set<Vendor> vendors = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("regions")
    private Country country;

    @ManyToMany(mappedBy = "regions")
    @JsonIgnore
    private Set<Compliance> compliances = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegionName() {
        return regionName;
    }

    public Region regionName(String regionName) {
        this.regionName = regionName;
        return this;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public Set<EntitySeekingFund> getEntitySeekingFunds() {
        return entitySeekingFunds;
    }

    public Region entitySeekingFunds(Set<EntitySeekingFund> entitySeekingFunds) {
        this.entitySeekingFunds = entitySeekingFunds;
        return this;
    }

    public Region addEntitySeekingFund(EntitySeekingFund entitySeekingFund) {
        this.entitySeekingFunds.add(entitySeekingFund);
        entitySeekingFund.setRegion(this);
        return this;
    }

    public Region removeEntitySeekingFund(EntitySeekingFund entitySeekingFund) {
        this.entitySeekingFunds.remove(entitySeekingFund);
        entitySeekingFund.setRegion(null);
        return this;
    }

    public void setEntitySeekingFunds(Set<EntitySeekingFund> entitySeekingFunds) {
        this.entitySeekingFunds = entitySeekingFunds;
    }

    public Set<Investor> getInvestors() {
        return investors;
    }

    public Region investors(Set<Investor> investors) {
        this.investors = investors;
        return this;
    }

    public Region addInvestor(Investor investor) {
        this.investors.add(investor);
        investor.setRegion(this);
        return this;
    }

    public Region removeInvestor(Investor investor) {
        this.investors.remove(investor);
        investor.setRegion(null);
        return this;
    }

    public void setInvestors(Set<Investor> investors) {
        this.investors = investors;
    }

    public Set<Vendor> getVendors() {
        return vendors;
    }

    public Region vendors(Set<Vendor> vendors) {
        this.vendors = vendors;
        return this;
    }

    public Region addVendor(Vendor vendor) {
        this.vendors.add(vendor);
        vendor.setRegion(this);
        return this;
    }

    public Region removeVendor(Vendor vendor) {
        this.vendors.remove(vendor);
        vendor.setRegion(null);
        return this;
    }

    public void setVendors(Set<Vendor> vendors) {
        this.vendors = vendors;
    }

    public Country getCountry() {
        return country;
    }

    public Region country(Country country) {
        this.country = country;
        return this;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Set<Compliance> getCompliances() {
        return compliances;
    }

    public Region compliances(Set<Compliance> compliances) {
        this.compliances = compliances;
        return this;
    }

    public Region addCompliance(Compliance compliance) {
        this.compliances.add(compliance);
        compliance.getRegions().add(this);
        return this;
    }

    public Region removeCompliance(Compliance compliance) {
        this.compliances.remove(compliance);
        compliance.getRegions().remove(this);
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
        if (!(o instanceof Region)) {
            return false;
        }
        return id != null && id.equals(((Region) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Region{" +
            "id=" + getId() +
            ", regionName='" + getRegionName() + "'" +
            "}";
    }
}
