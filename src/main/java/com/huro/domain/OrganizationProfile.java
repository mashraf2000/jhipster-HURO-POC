package com.huro.domain;

import javax.persistence.*;

import java.io.Serializable;

import com.huro.domain.enumeration.Language;

/**
 * A OrganizationProfile.
 */
@Entity
@Table(name = "organization_profile")
public class OrganizationProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "language")
    private Language language;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Language getLanguage() {
        return language;
    }

    public OrganizationProfile language(Language language) {
        this.language = language;
        return this;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OrganizationProfile)) {
            return false;
        }
        return id != null && id.equals(((OrganizationProfile) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "OrganizationProfile{" +
            "id=" + getId() +
            ", language='" + getLanguage() + "'" +
            "}";
    }
}
