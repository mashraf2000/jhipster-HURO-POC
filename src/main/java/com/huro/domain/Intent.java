package com.huro.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import com.huro.domain.enumeration.IntentStatus;

/**
 * A Intent.
 */
@Entity
@Table(name = "intent")
public class Intent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "expected_date_of_completion")
    private Instant expectedDateOfCompletion;

    @Column(name = "funding_amount_desired")
    private Long fundingAmountDesired;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private IntentStatus status;

    @Column(name = "date_created")
    private Instant dateCreated;

    @Column(name = "date_updated")
    private Instant dateUpdated;

    @ManyToMany(mappedBy = "intents")
    @JsonIgnore
    private Set<EntitySeekingFund> entitySeekingFunds = new HashSet<>();

    @ManyToMany(mappedBy = "intents")
    @JsonIgnore
    private Set<Investor> investors = new HashSet<>();

    @ManyToMany(mappedBy = "intents")
    @JsonIgnore
    private Set<Vendor> vendors = new HashSet<>();

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

    public Intent name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getExpectedDateOfCompletion() {
        return expectedDateOfCompletion;
    }

    public Intent expectedDateOfCompletion(Instant expectedDateOfCompletion) {
        this.expectedDateOfCompletion = expectedDateOfCompletion;
        return this;
    }

    public void setExpectedDateOfCompletion(Instant expectedDateOfCompletion) {
        this.expectedDateOfCompletion = expectedDateOfCompletion;
    }

    public Long getFundingAmountDesired() {
        return fundingAmountDesired;
    }

    public Intent fundingAmountDesired(Long fundingAmountDesired) {
        this.fundingAmountDesired = fundingAmountDesired;
        return this;
    }

    public void setFundingAmountDesired(Long fundingAmountDesired) {
        this.fundingAmountDesired = fundingAmountDesired;
    }

    public IntentStatus getStatus() {
        return status;
    }

    public Intent status(IntentStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(IntentStatus status) {
        this.status = status;
    }

    public Instant getDateCreated() {
        return dateCreated;
    }

    public Intent dateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Instant getDateUpdated() {
        return dateUpdated;
    }

    public Intent dateUpdated(Instant dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(Instant dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Set<EntitySeekingFund> getEntitySeekingFunds() {
        return entitySeekingFunds;
    }

    public Intent entitySeekingFunds(Set<EntitySeekingFund> entitySeekingFunds) {
        this.entitySeekingFunds = entitySeekingFunds;
        return this;
    }

    public Intent addEntitySeekingFund(EntitySeekingFund entitySeekingFund) {
        this.entitySeekingFunds.add(entitySeekingFund);
        entitySeekingFund.getIntents().add(this);
        return this;
    }

    public Intent removeEntitySeekingFund(EntitySeekingFund entitySeekingFund) {
        this.entitySeekingFunds.remove(entitySeekingFund);
        entitySeekingFund.getIntents().remove(this);
        return this;
    }

    public void setEntitySeekingFunds(Set<EntitySeekingFund> entitySeekingFunds) {
        this.entitySeekingFunds = entitySeekingFunds;
    }

    public Set<Investor> getInvestors() {
        return investors;
    }

    public Intent investors(Set<Investor> investors) {
        this.investors = investors;
        return this;
    }

    public Intent addInvestor(Investor investor) {
        this.investors.add(investor);
        investor.getIntents().add(this);
        return this;
    }

    public Intent removeInvestor(Investor investor) {
        this.investors.remove(investor);
        investor.getIntents().remove(this);
        return this;
    }

    public void setInvestors(Set<Investor> investors) {
        this.investors = investors;
    }

    public Set<Vendor> getVendors() {
        return vendors;
    }

    public Intent vendors(Set<Vendor> vendors) {
        this.vendors = vendors;
        return this;
    }

    public Intent addVendor(Vendor vendor) {
        this.vendors.add(vendor);
        vendor.getIntents().add(this);
        return this;
    }

    public Intent removeVendor(Vendor vendor) {
        this.vendors.remove(vendor);
        vendor.getIntents().remove(this);
        return this;
    }

    public void setVendors(Set<Vendor> vendors) {
        this.vendors = vendors;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Intent)) {
            return false;
        }
        return id != null && id.equals(((Intent) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Intent{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", expectedDateOfCompletion='" + getExpectedDateOfCompletion() + "'" +
            ", fundingAmountDesired=" + getFundingAmountDesired() +
            ", status='" + getStatus() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            "}";
    }
}
