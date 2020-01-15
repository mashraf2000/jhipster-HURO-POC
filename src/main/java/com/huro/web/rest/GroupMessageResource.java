package com.huro.web.rest;

import com.huro.domain.GroupMessage;
import com.huro.repository.GroupMessageRepository;
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
 * REST controller for managing {@link com.huro.domain.GroupMessage}.
 */
@RestController
@RequestMapping("/api")
@Transactional
public class GroupMessageResource {

    private final Logger log = LoggerFactory.getLogger(GroupMessageResource.class);

    private static final String ENTITY_NAME = "groupMessage";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GroupMessageRepository groupMessageRepository;

    public GroupMessageResource(GroupMessageRepository groupMessageRepository) {
        this.groupMessageRepository = groupMessageRepository;
    }

    /**
     * {@code POST  /group-messages} : Create a new groupMessage.
     *
     * @param groupMessage the groupMessage to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new groupMessage, or with status {@code 400 (Bad Request)} if the groupMessage has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/group-messages")
    public ResponseEntity<GroupMessage> createGroupMessage(@RequestBody GroupMessage groupMessage) throws URISyntaxException {
        log.debug("REST request to save GroupMessage : {}", groupMessage);
        if (groupMessage.getId() != null) {
            throw new BadRequestAlertException("A new groupMessage cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GroupMessage result = groupMessageRepository.save(groupMessage);
        return ResponseEntity.created(new URI("/api/group-messages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /group-messages} : Updates an existing groupMessage.
     *
     * @param groupMessage the groupMessage to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated groupMessage,
     * or with status {@code 400 (Bad Request)} if the groupMessage is not valid,
     * or with status {@code 500 (Internal Server Error)} if the groupMessage couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/group-messages")
    public ResponseEntity<GroupMessage> updateGroupMessage(@RequestBody GroupMessage groupMessage) throws URISyntaxException {
        log.debug("REST request to update GroupMessage : {}", groupMessage);
        if (groupMessage.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        GroupMessage result = groupMessageRepository.save(groupMessage);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, groupMessage.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /group-messages} : get all the groupMessages.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of groupMessages in body.
     */
    @GetMapping("/group-messages")
    public ResponseEntity<List<GroupMessage>> getAllGroupMessages(Pageable pageable) {
        log.debug("REST request to get a page of GroupMessages");
        Page<GroupMessage> page = groupMessageRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /group-messages/:id} : get the "id" groupMessage.
     *
     * @param id the id of the groupMessage to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the groupMessage, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/group-messages/{id}")
    public ResponseEntity<GroupMessage> getGroupMessage(@PathVariable Long id) {
        log.debug("REST request to get GroupMessage : {}", id);
        Optional<GroupMessage> groupMessage = groupMessageRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(groupMessage);
    }

    /**
     * {@code DELETE  /group-messages/:id} : delete the "id" groupMessage.
     *
     * @param id the id of the groupMessage to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/group-messages/{id}")
    public ResponseEntity<Void> deleteGroupMessage(@PathVariable Long id) {
        log.debug("REST request to delete GroupMessage : {}", id);
        groupMessageRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
