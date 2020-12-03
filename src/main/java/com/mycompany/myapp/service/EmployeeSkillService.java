package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.EmployeeSkillId;
import com.mycompany.myapp.service.dto.EmployeeSkillDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.EmployeeSkill}.
 */
public interface EmployeeSkillService {
    /**
     * Save a employeeSkill.
     *
     * @param employeeSkillDTO the entity to save.
     * @return the persisted entity.
     */
    EmployeeSkillDTO save(EmployeeSkillDTO employeeSkillDTO);

    /**
     * Partially updates a employeeSkill.
     *
     * @param employeeSkillDTO the entity to update partially.
     * @return the persisted entity.
     */
    EmployeeSkillDTO partialUpdate(EmployeeSkillDTO employeeSkillDTO);

    /**
     * Get all the employeeSkills.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EmployeeSkillDTO> findAll(Pageable pageable);

    /**
     * Get all the employeeSkills with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EmployeeSkillDTO> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" employeeSkill.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EmployeeSkillDTO> findOne(EmployeeSkillId id);

    /**
     * Delete the "id" employeeSkill.
     *
     * @param id the id of the entity.
     */
    void delete(EmployeeSkillId id);
}
