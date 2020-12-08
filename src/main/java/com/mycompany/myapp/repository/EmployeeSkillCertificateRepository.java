package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.EmployeeSkillCertificate;
import com.mycompany.myapp.domain.EmployeeSkillCertificateId;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the EmployeeSkillCertificate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeeSkillCertificateRepository
    extends JpaRepository<EmployeeSkillCertificate, EmployeeSkillCertificateId>, JpaSpecificationExecutor<EmployeeSkillCertificate> {}
