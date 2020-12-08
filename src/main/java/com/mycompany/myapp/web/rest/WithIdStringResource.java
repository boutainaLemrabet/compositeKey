package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.WithIdStringQueryService;
import com.mycompany.myapp.service.WithIdStringService;
import com.mycompany.myapp.service.dto.WithIdStringCriteria;
import com.mycompany.myapp.service.dto.WithIdStringDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.WithIdString}.
 */
@RestController
@RequestMapping("/api")
public class WithIdStringResource {

    private final Logger log = LoggerFactory.getLogger(WithIdStringResource.class);

    private static final String ENTITY_NAME = "withIdString";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WithIdStringService withIdStringService;

    private final WithIdStringQueryService withIdStringQueryService;

    public WithIdStringResource(WithIdStringService withIdStringService, WithIdStringQueryService withIdStringQueryService) {
        this.withIdStringService = withIdStringService;
        this.withIdStringQueryService = withIdStringQueryService;
    }

    /**
     * {@code POST  /with-id-strings} : Create a new withIdString.
     *
     * @param withIdStringDTO the withIdStringDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new withIdStringDTO, or with status {@code 400 (Bad Request)} if the withIdString has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/with-id-strings")
    public ResponseEntity<WithIdStringDTO> createWithIdString(@Valid @RequestBody WithIdStringDTO withIdStringDTO)
        throws URISyntaxException {
        log.debug("REST request to save WithIdString : {}", withIdStringDTO);
        if (withIdStringService.findOne(withIdStringDTO.getId()).isPresent()) {
            throw new BadRequestAlertException("This withIdString already exists", ENTITY_NAME, "idexists");
        }
        WithIdStringDTO result = withIdStringService.save(withIdStringDTO);
        return ResponseEntity
            .created(new URI("/api/with-id-strings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /with-id-strings} : Updates an existing withIdString.
     *
     * @param withIdStringDTO the withIdStringDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated withIdStringDTO,
     * or with status {@code 400 (Bad Request)} if the withIdStringDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the withIdStringDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/with-id-strings")
    public ResponseEntity<WithIdStringDTO> updateWithIdString(@Valid @RequestBody WithIdStringDTO withIdStringDTO)
        throws URISyntaxException {
        log.debug("REST request to update WithIdString : {}", withIdStringDTO);
        if (withIdStringDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!withIdStringService.findOne(withIdStringDTO.getId()).isPresent()) {
            throw new BadRequestAlertException("This withIdString doesn't exist", ENTITY_NAME, "idexists");
        }
        WithIdStringDTO result = withIdStringService.save(withIdStringDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, withIdStringDTO.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /with-id-strings} : Updates given fields of an existing withIdString.
     *
     * @param withIdStringDTO the withIdStringDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated withIdStringDTO,
     * or with status {@code 400 (Bad Request)} if the withIdStringDTO is not valid,
     * or with status {@code 404 (Not Found)} if the withIdStringDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the withIdStringDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/with-id-strings", consumes = "application/merge-patch+json")
    public ResponseEntity<WithIdStringDTO> partialUpdateWithIdString(@NotNull @RequestBody WithIdStringDTO withIdStringDTO)
        throws URISyntaxException {
        log.debug("REST request to update WithIdString partially : {}", withIdStringDTO);
        if (withIdStringDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!withIdStringService.findOne(withIdStringDTO.getId()).isPresent()) {
            throw new BadRequestAlertException("This withIdString doesn't exist", ENTITY_NAME, "idexists");
        }

        Optional<WithIdStringDTO> result = Optional.ofNullable(withIdStringService.partialUpdate(withIdStringDTO));

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, withIdStringDTO.getId())
        );
    }

    /**
     * {@code GET  /with-id-strings} : get all the withIdStrings.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of withIdStrings in body.
     */
    @GetMapping("/with-id-strings")
    public ResponseEntity<List<WithIdStringDTO>> getAllWithIdStrings(WithIdStringCriteria criteria) {
        log.debug("REST request to get WithIdStrings by criteria: {}", criteria);
        List<WithIdStringDTO> entityList = withIdStringQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /with-id-strings/count} : count all the withIdStrings.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/with-id-strings/count")
    public ResponseEntity<Long> countWithIdStrings(WithIdStringCriteria criteria) {
        log.debug("REST request to count WithIdStrings by criteria: {}", criteria);
        return ResponseEntity.ok().body(withIdStringQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /with-id-strings/:id} : get the "id" withIdString.
     *
     * @param id the id of the withIdStringDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the withIdStringDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/with-id-strings/{id}")
    public ResponseEntity<WithIdStringDTO> getWithIdString(@PathVariable String id) {
        log.debug("REST request to get WithIdString : {}", id);
        Optional<WithIdStringDTO> withIdStringDTO = withIdStringService.findOne(id);
        return ResponseUtil.wrapOrNotFound(withIdStringDTO);
    }

    /**
     * {@code DELETE  /with-id-strings/:id} : delete the "id" withIdString.
     *
     * @param id the id of the withIdStringDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/with-id-strings/{id}")
    public ResponseEntity<Void> deleteWithIdString(@PathVariable String id) {
        log.debug("REST request to delete WithIdString : {}", id);
        withIdStringService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
