package com.huro.repository;

import com.huro.domain.EntitySeekingFund;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the EntitySeekingFund entity.
 */
@Repository
public interface EntitySeekingFundRepository extends JpaRepository<EntitySeekingFund, Long> {

    @Query(value = "select distinct entitySeekingFund from EntitySeekingFund entitySeekingFund left join fetch entitySeekingFund.intents",
        countQuery = "select count(distinct entitySeekingFund) from EntitySeekingFund entitySeekingFund")
    Page<EntitySeekingFund> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct entitySeekingFund from EntitySeekingFund entitySeekingFund left join fetch entitySeekingFund.intents")
    List<EntitySeekingFund> findAllWithEagerRelationships();

    @Query("select entitySeekingFund from EntitySeekingFund entitySeekingFund left join fetch entitySeekingFund.intents where entitySeekingFund.id =:id")
    Optional<EntitySeekingFund> findOneWithEagerRelationships(@Param("id") Long id);

}
