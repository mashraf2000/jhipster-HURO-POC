package com.huro.repository;

import com.huro.domain.Compliance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Compliance entity.
 */
@Repository
public interface ComplianceRepository extends JpaRepository<Compliance, Long> {

    @Query(value = "select distinct compliance from Compliance compliance left join fetch compliance.regions left join fetch compliance.countries",
        countQuery = "select count(distinct compliance) from Compliance compliance")
    Page<Compliance> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct compliance from Compliance compliance left join fetch compliance.regions left join fetch compliance.countries")
    List<Compliance> findAllWithEagerRelationships();

    @Query("select compliance from Compliance compliance left join fetch compliance.regions left join fetch compliance.countries where compliance.id =:id")
    Optional<Compliance> findOneWithEagerRelationships(@Param("id") Long id);

}
