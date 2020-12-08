package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.EmployeeSkillCertificateId;
import com.mycompany.myapp.service.dto.EmployeeSkillCertificateDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.mycompany.myapp.domain.EmployeeSkillCertificate}.
 */
public interface EmployeeSkillCertificateService {
    /**
     * Save a employeeSkillCertificate.
     *
     * @param employeeSkillCertificateDTO the entity to save.
     * @return the persisted entity.
     */
    EmployeeSkillCertificateDTO save(EmployeeSkillCertificateDTO employeeSkillCertificateDTO);

    /**
     * Partially updates a employeeSkillCertificate.
     *
     * @param employeeSkillCertificateDTO the entity to update partially.
     * @return the persisted entity.
     */
    EmployeeSkillCertificateDTO partialUpdate(EmployeeSkillCertificateDTO employeeSkillCertificateDTO);

    /**
     * Get all the employeeSkillCertificates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<EmployeeSkillCertificateDTO> findAll(Pageable pageable);

    /**
     * Get the "id" employeeSkillCertificate.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<EmployeeSkillCertificateDTO> findOne(EmployeeSkillCertificateId id);

    /**
     * Delete the "id" employeeSkillCertificate.
     *
     * @param id the id of the entity.
     */
    void delete(EmployeeSkillCertificateId id);
}
