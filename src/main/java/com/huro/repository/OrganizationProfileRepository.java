package com.huro.repository;

import com.huro.domain.OrganizationProfile;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the OrganizationProfile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrganizationProfileRepository extends JpaRepository<OrganizationProfile, Long> {

}
