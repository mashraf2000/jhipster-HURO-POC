package com.huro.web.rest;

import com.huro.domain.Compliance;
import com.huro.repository.ComplianceRepository;
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
 * REST controller for managing {@link com.huro.domain.Compliance}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class ComplianceResource {

    private final Logger log = LoggerFactory.getLogger(ComplianceResource.class);

    private static final String ENTITY_NAME = "compliance";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ComplianceRepository complianceRepository;

    public ComplianceResource(ComplianceRepository complianceRepository) {
        this.complianceRepository = complianceRepository;
    }

    /**
     * {@code POST  /compliances} : Create a new compliance.
     *
     * @param compliance the compliance to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new compliance, or with status {@code 400 (Bad Request)} if the compliance has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/compliances")
    public ResponseEntity<Compliance> createCompliance(@RequestBody Compliance compliance) throws URISyntaxException {
        log.debug("REST request to save Compliance : {}", compliance);
        if (compliance.getId() != null) {
            throw new BadRequestAlertException("A new compliance cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Compliance result = complianceRepository.save(compliance);
        return ResponseEntity.created(new URI("/api/compliances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /compliances} : Updates an existing compliance.
     *
     * @param compliance the compliance to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated compliance,
     * or with status {@code 400 (Bad Request)} if the compliance is not valid,
     * or with status {@code 500 (Internal Server Error)} if the compliance couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/compliances")
    public ResponseEntity<Compliance> updateCompliance(@RequestBody Compliance compliance) throws URISyntaxException {
        log.debug("REST request to update Compliance : {}", compliance);
        if (compliance.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Compliance result = complianceRepository.save(compliance);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, compliance.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /compliances} : get all the compliances.
     *

     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of compliances in body.
     */
    @GetMapping("/compliances")
    public ResponseEntity<List<Compliance>> getAllCompliances(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Compliances");
        Page<Compliance> page;
        if (eagerload) {
            page = complianceRepository.findAllWithEagerRelationships(pageable);
        } else {
            page = complianceRepository.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /compliances/:id} : get the "id" compliance.
     *
     * @param id the id of the compliance to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the compliance, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/compliances/{id}")
    public ResponseEntity<Compliance> getCompliance(@PathVariable Long id) {
        log.debug("REST request to get Compliance : {}", id);
        Optional<Compliance> compliance = complianceRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(compliance);
    }

    /**
     * {@code DELETE  /compliances/:id} : delete the "id" compliance.
     *
     * @param id the id of the compliance to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/compliances/{id}")
    public ResponseEntity<Void> deleteCompliance(@PathVariable Long id) {
        log.debug("REST request to delete Compliance : {}", id);
        complianceRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
