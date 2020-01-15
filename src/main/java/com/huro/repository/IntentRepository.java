package com.huro.repository;

import com.huro.domain.Intent;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Intent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IntentRepository extends JpaRepository<Intent, Long> {

}
