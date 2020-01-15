package com.huro.web.rest;

import com.huro.domain.EntitySeekingFund;
import com.huro.repository.EntitySeekingFundRepository;
import com.huro.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional; 
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.huro.domain.EntitySeekingFund}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class EntitySeekingFundResource {

    private final Logger log = LoggerFactory.getLogger(EntitySeekingFundResource.class);

    private static final String ENTITY_NAME = "entitySeekingFund";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EntitySeekingFundRepository entitySeekingFundRepository;

    public EntitySeekingFundResource(EntitySeekingFundRepository entitySeekingFundRepository) {
        this.entitySeekingFundRepository = entitySeekingFundRepository;
    }

    /**
     * {@code POST  /entity-seeking-funds} : Create a new entitySeekingFund.
     *
     * @param entitySeekingFund the entitySeekingFund to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new entitySeekingFund, or with status {@code 400 (Bad Request)} if the entitySeekingFund has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/entity-seeking-funds")
    public ResponseEntity<EntitySeekingFund> createEntitySeekingFund(@RequestBody EntitySeekingFund entitySeekingFund) throws URISyntaxException {
        log.debug("REST request to save EntitySeekingFund : {}", entitySeekingFund);
        if (entitySeekingFund.getId() != null) {
            throw new BadRequestAlertException("A new entitySeekingFund cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EntitySeekingFund result = entitySeekingFundRepository.save(entitySeekingFund);
        return ResponseEntity.created(new URI("/api/entity-seeking-funds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /entity-seeking-funds} : Updates an existing entitySeekingFund.
     *
     * @param entitySeekingFund the entitySeekingFund to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated entitySeekingFund,
     * or with status {@code 400 (Bad Request)} if the entitySeekingFund is not valid,
     * or with status {@code 500 (Internal Server Error)} if the entitySeekingFund couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/entity-seeking-funds")
    public ResponseEntity<EntitySeekingFund> updateEntitySeekingFund(@RequestBody EntitySeekingFund entitySeekingFund) throws URISyntaxException {
        log.debug("REST request to update EntitySeekingFund : {}", entitySeekingFund);
        if (entitySeekingFund.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EntitySeekingFund result = entitySeekingFundRepository.save(entitySeekingFund);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, entitySeekingFund.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /entity-seeking-funds} : get all the entitySeekingFunds.
     *

     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entitySeekingFunds in body.
     */
    @GetMapping("/entity-seeking-funds")
    public ResponseEntity<List<EntitySeekingFund>> getAllEntitySeekingFunds(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of EntitySeekingFunds");
        Page<EntitySeekingFund> page;
        if (eagerload) {
            page = entitySeekingFundRepository.findAllWithEagerRelationships(pageable);
        } else {
            page = entitySeekingFundRepository.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /entity-seeking-funds/:id} : get the "id" entitySeekingFund.
     *
     * @param id the id of the entitySeekingFund to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the entitySeekingFund, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/entity-seeking-funds/{id}")
    public ResponseEntity<EntitySeekingFund> getEntitySeekingFund(@PathVariable Long id) {
        log.debug("REST request to get EntitySeekingFund : {}", id);
        Optional<EntitySeekingFund> entitySeekingFund = entitySeekingFundRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(entitySeekingFund);
    }

    /**
     * {@code DELETE  /entity-seeking-funds/:id} : delete the "id" entitySeekingFund.
     *
     * @param id the id of the entitySeekingFund to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/entity-seeking-funds/{id}")
    public ResponseEntity<Void> deleteEntitySeekingFund(@PathVariable Long id) {
        log.debug("REST request to delete EntitySeekingFund : {}", id);
        entitySeekingFundRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
