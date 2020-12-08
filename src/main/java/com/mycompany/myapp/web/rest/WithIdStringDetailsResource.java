package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.WithIdStringDetailsQueryService;
import com.mycompany.myapp.service.WithIdStringDetailsService;
import com.mycompany.myapp.service.dto.WithIdStringDetailsCriteria;
import com.mycompany.myapp.service.dto.WithIdStringDetailsDTO;
import com.mycompany.myapp.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.mycompany.myapp.domain.WithIdStringDetails}.
 */
@RestController
@RequestMapping("/api")
public class WithIdStringDetailsResource {

    private final Logger log = LoggerFactory.getLogger(WithIdStringDetailsResource.class);

    private static final String ENTITY_NAME = "withIdStringDetails";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WithIdStringDetailsService withIdStringDetailsService;

    private final WithIdStringDetailsQueryService withIdStringDetailsQueryService;

    public WithIdStringDetailsResource(
        WithIdStringDetailsService withIdStringDetailsService,
        WithIdStringDetailsQueryService withIdStringDetailsQueryService
    ) {
        this.withIdStringDetailsService = withIdStringDetailsService;
        this.withIdStringDetailsQueryService = withIdStringDetailsQueryService;
    }

    /**
     * {@code POST  /with-id-string-details} : Create a new withIdStringDetails.
     *
     * @param withIdStringDetailsDTO the withIdStringDetailsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new withIdStringDetailsDTO, or with status {@code 400 (Bad Request)} if the withIdStringDetails has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/with-id-string-details")
    public ResponseEntity<WithIdStringDetailsDTO> createWithIdStringDetails(@RequestBody WithIdStringDetailsDTO withIdStringDetailsDTO)
        throws URISyntaxException {
        log.debug("REST request to save WithIdStringDetails : {}", withIdStringDetailsDTO);
        if (withIdStringDetailsDTO.getWithIdStringId() != null) {
            throw new BadRequestAlertException("A new withIdStringDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        if (Objects.isNull(withIdStringDetailsDTO.getWithIdString())) {
            throw new BadRequestAlertException("Invalid association value provided", ENTITY_NAME, "null");
        }
        WithIdStringDetailsDTO result = withIdStringDetailsService.save(withIdStringDetailsDTO);
        return ResponseEntity
            .created(new URI("/api/with-id-string-details/" + result.getWithIdStringId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getWithIdStringId()))
            .body(result);
    }

    /**
     * {@code PUT  /with-id-string-details} : Updates an existing withIdStringDetails.
     *
     * @param withIdStringDetailsDTO the withIdStringDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated withIdStringDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the withIdStringDetailsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the withIdStringDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/with-id-string-details")
    public ResponseEntity<WithIdStringDetailsDTO> updateWithIdStringDetails(@RequestBody WithIdStringDetailsDTO withIdStringDetailsDTO)
        throws URISyntaxException {
        log.debug("REST request to update WithIdStringDetails : {}", withIdStringDetailsDTO);
        if (withIdStringDetailsDTO.getWithIdStringId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WithIdStringDetailsDTO result = withIdStringDetailsService.save(withIdStringDetailsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, withIdStringDetailsDTO.getWithIdStringId()))
            .body(result);
    }

    /**
     * {@code PATCH  /with-id-string-details} : Updates given fields of an existing withIdStringDetails.
     *
     * @param withIdStringDetailsDTO the withIdStringDetailsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated withIdStringDetailsDTO,
     * or with status {@code 400 (Bad Request)} if the withIdStringDetailsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the withIdStringDetailsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the withIdStringDetailsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/with-id-string-details", consumes = "application/merge-patch+json")
    public ResponseEntity<WithIdStringDetailsDTO> partialUpdateWithIdStringDetails(
        @RequestBody WithIdStringDetailsDTO withIdStringDetailsDTO
    )
        throws URISyntaxException {
        log.debug("REST request to update WithIdStringDetails partially : {}", withIdStringDetailsDTO);
        if (withIdStringDetailsDTO.getWithIdStringId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }

        Optional<WithIdStringDetailsDTO> result = Optional.ofNullable(withIdStringDetailsService.partialUpdate(withIdStringDetailsDTO));

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, withIdStringDetailsDTO.getWithIdStringId())
        );
    }

    /**
     * {@code GET  /with-id-string-details} : get all the withIdStringDetails.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of withIdStringDetails in body.
     */
    @GetMapping("/with-id-string-details")
    public ResponseEntity<List<WithIdStringDetailsDTO>> getAllWithIdStringDetails(WithIdStringDetailsCriteria criteria) {
        log.debug("REST request to get WithIdStringDetails by criteria: {}", criteria);
        List<WithIdStringDetailsDTO> entityList = withIdStringDetailsQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * {@code GET  /with-id-string-details/count} : count all the withIdStringDetails.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/with-id-string-details/count")
    public ResponseEntity<Long> countWithIdStringDetails(WithIdStringDetailsCriteria criteria) {
        log.debug("REST request to count WithIdStringDetails by criteria: {}", criteria);
        return ResponseEntity.ok().body(withIdStringDetailsQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /with-id-string-details/:id} : get the "id" withIdStringDetails.
     *
     * @param id the id of the withIdStringDetailsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the withIdStringDetailsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/with-id-string-details/{id}")
    public ResponseEntity<WithIdStringDetailsDTO> getWithIdStringDetails(@PathVariable String id) {
        log.debug("REST request to get WithIdStringDetails : {}", id);
        Optional<WithIdStringDetailsDTO> withIdStringDetailsDTO = withIdStringDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(withIdStringDetailsDTO);
    }

    /**
     * {@code DELETE  /with-id-string-details/:id} : delete the "id" withIdStringDetails.
     *
     * @param id the id of the withIdStringDetailsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/with-id-string-details/{id}")
    public ResponseEntity<Void> deleteWithIdStringDetails(@PathVariable String id) {
        log.debug("REST request to delete WithIdStringDetails : {}", id);
        withIdStringDetailsService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
