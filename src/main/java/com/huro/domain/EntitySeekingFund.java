package com.huro.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A EntitySeekingFund.
 */
@Entity
@Table(name = "entity_seeking_fund")
public class EntitySeekingFund implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "telephone_number")
    private String telephoneNumber;

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "date_created")
    private Instant dateCreated;

    @Column(name = "date_updated")
    private Instant dateUpdated;

    @OneToMany(mappedBy = "entitySeekingFund")
    private Set<Contact> contacts = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "entity_seeking_fund_intent",
               joinColumns = @JoinColumn(name = "entity_seeking_fund_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "intent_id", referencedColumnName = "id"))
    private Set<Intent> intents = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("entitySeekingFunds")
    private Region region;

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

    public EntitySeekingFund name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public EntitySeekingFund address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public EntitySeekingFund telephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
        return this;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public EntitySeekingFund emailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Instant getDateCreated() {
        return dateCreated;
    }

    public EntitySeekingFund dateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Instant getDateUpdated() {
        return dateUpdated;
    }

    public EntitySeekingFund dateUpdated(Instant dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(Instant dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Set<Contact> getContacts() {
        return contacts;
    }

    public EntitySeekingFund contacts(Set<Contact> contacts) {
        this.contacts = contacts;
        return this;
    }

    public EntitySeekingFund addContact(Contact contact) {
        this.contacts.add(contact);
        contact.setEntitySeekingFund(this);
        return this;
    }

    public EntitySeekingFund removeContact(Contact contact) {
        this.contacts.remove(contact);
        contact.setEntitySeekingFund(null);
        return this;
    }

    public void setContacts(Set<Contact> contacts) {
        this.contacts = contacts;
    }

    public Set<Intent> getIntents() {
        return intents;
    }

    public EntitySeekingFund intents(Set<Intent> intents) {
        this.intents = intents;
        return this;
    }

    public EntitySeekingFund addIntent(Intent intent) {
        this.intents.add(intent);
        intent.getEntitySeekingFunds().add(this);
        return this;
    }

    public EntitySeekingFund removeIntent(Intent intent) {
        this.intents.remove(intent);
        intent.getEntitySeekingFunds().remove(this);
        return this;
    }

    public void setIntents(Set<Intent> intents) {
        this.intents = intents;
    }

    public Region getRegion() {
        return region;
    }

    public EntitySeekingFund region(Region region) {
        this.region = region;
        return this;
    }

    public void setRegion(Region region) {
        this.region = region;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EntitySeekingFund)) {
            return false;
        }
        return id != null && id.equals(((EntitySeekingFund) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EntitySeekingFund{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", address='" + getAddress() + "'" +
            ", telephoneNumber='" + getTelephoneNumber() + "'" +
            ", emailAddress='" + getEmailAddress() + "'" +
            ", dateCreated='" + getDateCreated() + "'" +
            ", dateUpdated='" + getDateUpdated() + "'" +
            "}";
    }
}
