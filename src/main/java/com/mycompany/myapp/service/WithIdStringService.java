package com.mycompany.myapp.service;

import com.mycompany.myapp.service.dto.WithIdStringDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.WithIdString}.
 */
public interface WithIdStringService {
    /**
     * Save a withIdString.
     *
     * @param withIdStringDTO the entity to save.
     * @return the persisted entity.
     */
    WithIdStringDTO save(WithIdStringDTO withIdStringDTO);

    /**
     * Partially updates a withIdString.
     *
     * @param withIdStringDTO the entity to update partially.
     * @return the persisted entity.
     */
    WithIdStringDTO partialUpdate(WithIdStringDTO withIdStringDTO);

    /**
     * Get all the withIdStrings.
     *
     * @return the list of entities.
     */
    List<WithIdStringDTO> findAll();
    /**
     * Get all the WithIdStringDTO where WithIdStringDetails is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<WithIdStringDTO> findAllWhereWithIdStringDetailsIsNull();

    /**
     * Get the "id" withIdString.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WithIdStringDTO> findOne(String id);

    /**
     * Delete the "id" withIdString.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
