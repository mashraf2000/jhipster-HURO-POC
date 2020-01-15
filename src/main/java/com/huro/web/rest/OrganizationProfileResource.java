package com.huro.web.rest;

import com.huro.domain.OrganizationProfile;
import com.huro.repository.OrganizationProfileRepository;
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
 * REST controller for managing {@link com.huro.domain.OrganizationProfile}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class OrganizationProfileResource {

    private final Logger log = LoggerFactory.getLogger(OrganizationProfileResource.class);

    private static final String ENTITY_NAME = "organizationProfile";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrganizationProfileRepository organizationProfileRepository;

    public OrganizationProfileResource(OrganizationProfileRepository organizationProfileRepository) {
        this.organizationProfileRepository = organizationProfileRepository;
    }

    /**
     * {@code POST  /organization-profiles} : Create a new organizationProfile.
     *
     * @param organizationProfile the organizationProfile to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new organizationProfile, or with status {@code 400 (Bad Request)} if the organizationProfile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/organization-profiles")
    public ResponseEntity<OrganizationProfile> createOrganizationProfile(@RequestBody OrganizationProfile organizationProfile) throws URISyntaxException {
        log.debug("REST request to save OrganizationProfile : {}", organizationProfile);
        if (organizationProfile.getId() != null) {
            throw new BadRequestAlertException("A new organizationProfile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrganizationProfile result = organizationProfileRepository.save(organizationProfile);
        return ResponseEntity.created(new URI("/api/organization-profiles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /organization-profiles} : Updates an existing organizationProfile.
     *
     * @param organizationProfile the organizationProfile to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated organizationProfile,
     * or with status {@code 400 (Bad Request)} if the organizationProfile is not valid,
     * or with status {@code 500 (Internal Server Error)} if the organizationProfile couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/organization-profiles")
    public ResponseEntity<OrganizationProfile> updateOrganizationProfile(@RequestBody OrganizationProfile organizationProfile) throws URISyntaxException {
        log.debug("REST request to update OrganizationProfile : {}", organizationProfile);
        if (organizationProfile.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrganizationProfile result = organizationProfileRepository.save(organizationProfile);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, organizationProfile.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /organization-profiles} : get all the organizationProfiles.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of organizationProfiles in body.
     */
    @GetMapping("/organization-profiles")
    public ResponseEntity<List<OrganizationProfile>> getAllOrganizationProfiles(Pageable pageable) {
        log.debug("REST request to get a page of OrganizationProfiles");
        Page<OrganizationProfile> page = organizationProfileRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /organization-profiles/:id} : get the "id" organizationProfile.
     *
     * @param id the id of the organizationProfile to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the organizationProfile, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/organization-profiles/{id}")
    public ResponseEntity<OrganizationProfile> getOrganizationProfile(@PathVariable Long id) {
        log.debug("REST request to get OrganizationProfile : {}", id);
        Optional<OrganizationProfile> organizationProfile = organizationProfileRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(organizationProfile);
    }

    /**
     * {@code DELETE  /organization-profiles/:id} : delete the "id" organizationProfile.
     *
     * @param id the id of the organizationProfile to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/organization-profiles/{id}")
    public ResponseEntity<Void> deleteOrganizationProfile(@PathVariable Long id) {
        log.debug("REST request to delete OrganizationProfile : {}", id);
        organizationProfileRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
