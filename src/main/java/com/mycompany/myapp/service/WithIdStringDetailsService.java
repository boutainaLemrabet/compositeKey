package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.WithIdStringDetailsDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.WithIdStringDetails}.
 */
public interface WithIdStringDetailsService {
    /**
     * Save a withIdStringDetails.
     *
     * @param withIdStringDetailsDTO the entity to save.
     * @return the persisted entity.
     */
    WithIdStringDetailsDTO save(WithIdStringDetailsDTO withIdStringDetailsDTO);

    /**
     * Partially updates a withIdStringDetails.
     *
     * @param withIdStringDetailsDTO the entity to update partially.
     * @return the persisted entity.
     */
    WithIdStringDetailsDTO partialUpdate(WithIdStringDetailsDTO withIdStringDetailsDTO);

    /**
     * Get all the withIdStringDetails.
     *
     * @return the list of entities.
     */
    List<WithIdStringDetailsDTO> findAll();

    /**
     * Get the "id" withIdStringDetails.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WithIdStringDetailsDTO> findOne(String id);

    /**
     * Delete the "id" withIdStringDetails.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
