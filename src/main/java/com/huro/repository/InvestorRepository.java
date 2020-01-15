package com.huro.repository;

import com.huro.domain.Investor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Investor entity.
 */
@Repository
public interface InvestorRepository extends JpaRepository<Investor, Long> {

    @Query(value = "select distinct investor from Investor investor left join fetch investor.intents",
        countQuery = "select count(distinct investor) from Investor investor")
    Page<Investor> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct investor from Investor investor left join fetch investor.intents")
    List<Investor> findAllWithEagerRelationships();

    @Query("select investor from Investor investor left join fetch investor.intents where investor.id =:id")
    Optional<Investor> findOneWithEagerRelationships(@Param("id") Long id);

}
