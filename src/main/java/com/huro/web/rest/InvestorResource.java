package com.huro.web.rest;

import com.huro.domain.Investor;
import com.huro.repository.InvestorRepository;
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
 * REST controller for managing {@link com.huro.domain.Investor}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class InvestorResource {

    private final Logger log = LoggerFactory.getLogger(InvestorResource.class);

    private static final String ENTITY_NAME = "investor";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InvestorRepository investorRepository;

    public InvestorResource(InvestorRepository investorRepository) {
        this.investorRepository = investorRepository;
    }

    /**
     * {@code POST  /investors} : Create a new investor.
     *
     * @param investor the investor to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new investor, or with status {@code 400 (Bad Request)} if the investor has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/investors")
    public ResponseEntity<Investor> createInvestor(@RequestBody Investor investor) throws URISyntaxException {
        log.debug("REST request to save Investor : {}", investor);
        if (investor.getId() != null) {
            throw new BadRequestAlertException("A new investor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Investor result = investorRepository.save(investor);
        return ResponseEntity.created(new URI("/api/investors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /investors} : Updates an existing investor.
     *
     * @param investor the investor to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated investor,
     * or with status {@code 400 (Bad Request)} if the investor is not valid,
     * or with status {@code 500 (Internal Server Error)} if the investor couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/investors")
    public ResponseEntity<Investor> updateInvestor(@RequestBody Investor investor) throws URISyntaxException {
        log.debug("REST request to update Investor : {}", investor);
        if (investor.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Investor result = investorRepository.save(investor);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, investor.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /investors} : get all the investors.
     *

     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of investors in body.
     */
    @GetMapping("/investors")
    public ResponseEntity<List<Investor>> getAllInvestors(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Investors");
        Page<Investor> page;
        if (eagerload) {
            page = investorRepository.findAllWithEagerRelationships(pageable);
        } else {
            page = investorRepository.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /investors/:id} : get the "id" investor.
     *
     * @param id the id of the investor to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the investor, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/investors/{id}")
    public ResponseEntity<Investor> getInvestor(@PathVariable Long id) {
        log.debug("REST request to get Investor : {}", id);
        Optional<Investor> investor = investorRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(investor);
    }

    /**
     * {@code DELETE  /investors/:id} : delete the "id" investor.
     *
     * @param id the id of the investor to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/investors/{id}")
    public ResponseEntity<Void> deleteInvestor(@PathVariable Long id) {
        log.debug("REST request to delete Investor : {}", id);
        investorRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
