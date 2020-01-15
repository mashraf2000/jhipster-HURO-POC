package com.huro.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Vendor.
 */
@Entity
@Table(name = "vendor")
public class Vendor implements Serializable {

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

    @OneToMany(mappedBy = "vendor")
    private Set<Contact> contacts = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "vendor_intent",
               joinColumns = @JoinColumn(name = "vendor_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "intent_id", referencedColumnName = "id"))
    private Set<Intent> intents = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties("vendors")
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

    public Vendor name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public Vendor address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public Vendor telephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
        return this;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public Vendor emailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Instant getDateCreated() {
        return dateCreated;
    }

    public Vendor dateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
        return this;
    }

    public void setDateCreated(Instant dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Instant getDateUpdated() {
        return dateUpdated;
    }

    public Vendor dateUpdated(Instant dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public void setDateUpdated(Instant dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Set<Contact> getContacts() {
        return contacts;
    }

    public Vendor contacts(Set<Contact> contacts) {
        this.contacts = contacts;
        return this;
    }

    public Vendor addContact(Contact contact) {
        this.contacts.add(contact);
        contact.setVendor(this);
        return this;
    }

    public Vendor removeContact(Contact contact) {
        this.contacts.remove(contact);
        contact.setVendor(null);
        return this;
    }

    public void setContacts(Set<Contact> contacts) {
        this.contacts = contacts;
    }

    public Set<Intent> getIntents() {
        return intents;
    }

    public Vendor intents(Set<Intent> intents) {
        this.intents = intents;
        return this;
    }

    public Vendor addIntent(Intent intent) {
        this.intents.add(intent);
        intent.getVendors().add(this);
        return this;
    }

    public Vendor removeIntent(Intent intent) {
        this.intents.remove(intent);
        intent.getVendors().remove(this);
        return this;
    }

    public void setIntents(Set<Intent> intents) {
        this.intents = intents;
    }

    public Region getRegion() {
        return region;
    }

    public Vendor region(Region region) {
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
        if (!(o instanceof Vendor)) {
            return false;
        }
        return id != null && id.equals(((Vendor) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Vendor{" +
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
