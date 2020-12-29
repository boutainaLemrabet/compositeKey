package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.EmployeeSkillCertificate;
import com.mycompany.myapp.repository.EmployeeSkillCertificateRepository;
import com.mycompany.myapp.service.dto.EmployeeSkillCertificateCriteria;
import com.mycompany.myapp.service.dto.EmployeeSkillCertificateDTO;
import com.mycompany.myapp.service.mapper.EmployeeSkillCertificateMapper;
import java.util.List;
import javax.persistence.criteria.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link EmployeeSkillCertificate} entities in the database.
 * The main input is a {@link EmployeeSkillCertificateCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmployeeSkillCertificateDTO} or a {@link Page} of {@link EmployeeSkillCertificateDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmployeeSkillCertificateQueryService extends QueryService<EmployeeSkillCertificate> {

    private final Logger log = LoggerFactory.getLogger(EmployeeSkillCertificateQueryService.class);

    private final EmployeeSkillCertificateRepository employeeSkillCertificateRepository;

    private final EmployeeSkillCertificateMapper employeeSkillCertificateMapper;

    private final CertificateTypeQueryService certificateTypeQueryService;

    private final EmployeeSkillQueryService employeeSkillQueryService;

    public EmployeeSkillCertificateQueryService(
        EmployeeSkillCertificateRepository employeeSkillCertificateRepository,
        EmployeeSkillCertificateMapper employeeSkillCertificateMapper,
        @Lazy CertificateTypeQueryService certificateTypeQueryService,
        @Lazy EmployeeSkillQueryService employeeSkillQueryService
    ) {
        this.employeeSkillCertificateRepository = employeeSkillCertificateRepository;
        this.employeeSkillCertificateMapper = employeeSkillCertificateMapper;
        this.certificateTypeQueryService = certificateTypeQueryService;
        this.employeeSkillQueryService = employeeSkillQueryService;
    }

    /**
     * Return a {@link List} of {@link EmployeeSkillCertificateDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmployeeSkillCertificateDTO> findByCriteria(EmployeeSkillCertificateCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EmployeeSkillCertificate> specification = createSpecification(criteria);
        return employeeSkillCertificateMapper.toDto(employeeSkillCertificateRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EmployeeSkillCertificateDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeSkillCertificateDTO> findByCriteria(EmployeeSkillCertificateCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EmployeeSkillCertificate> specification = createSpecification(criteria);
        return employeeSkillCertificateRepository.findAll(specification, page).map(employeeSkillCertificateMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmployeeSkillCertificateCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EmployeeSkillCertificate> specification = createSpecification(criteria);
        return employeeSkillCertificateRepository.count(specification);
    }

    /**
     * Function to convert {@link EmployeeSkillCertificateCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    public Specification<EmployeeSkillCertificate> createSpecification(EmployeeSkillCertificateCriteria criteria) {
        Specification<EmployeeSkillCertificate> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getGrade() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getGrade(), EmployeeSkillCertificate_.grade));
            }
            if (criteria.getDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDate(), EmployeeSkillCertificate_.date));
            }

            if (criteria.getType() != null) {
                Specification<CertificateType> certificateTypeSpecification =
                    this.certificateTypeQueryService.createSpecification(criteria.getType());
                specification =
                    specification.and(
                        (root, query, criteriaBuilder) -> {
                            Root<CertificateType> certificateTypeRoot = query.from(CertificateType.class);
                            return criteriaBuilder.and(
                                criteriaBuilder.equal(certificateTypeRoot, root.get(EmployeeSkillCertificate_.type)),
                                certificateTypeSpecification.toPredicate(certificateTypeRoot, query, criteriaBuilder)
                            );
                        }
                    );
            }
            if (criteria.getSkill() != null) {
                Specification<EmployeeSkill> employeeSkillSpecification =
                    this.employeeSkillQueryService.createSpecification(criteria.getSkill());
                specification =
                    specification.and(
                        (root, query, criteriaBuilder) -> {
                            Root<EmployeeSkill> employeeSkillRoot = query.from(EmployeeSkill.class);
                            return criteriaBuilder.and(
                                criteriaBuilder.equal(employeeSkillRoot, root.get(EmployeeSkillCertificate_.skill)),
                                employeeSkillSpecification.toPredicate(employeeSkillRoot, query, criteriaBuilder)
                            );
                        }
                    );
            }

            if (criteria.getGlobalFilter() != null) {
                Specification<EmployeeSkillCertificate> orSpecification = Specification.where(null);
                orSpecification =
                    orSpecification.or(
                        (
                            (root, criteriaQuery, criteriaBuilder) ->
                                criteriaBuilder.like(
                                    root.get(EmployeeSkillCertificate_.id).get(EmployeeSkillCertificateId_.skillName),
                                    "%" + criteria.getGlobalFilter() + "%"
                                )
                        )
                    );
                orSpecification =
                    orSpecification.or(
                        (
                            (root, criteriaQuery, criteriaBuilder) ->
                                criteriaBuilder.like(
                                    root.get(EmployeeSkillCertificate_.id).get(EmployeeSkillCertificateId_.skillEmployeeUsername),
                                    "%" + criteria.getGlobalFilter() + "%"
                                )
                        )
                    );
                orSpecification =
                    orSpecification.or(
                        (
                            (root, criteriaQuery, criteriaBuilder) ->
                                criteriaBuilder.like(
                                    root.get(EmployeeSkillCertificate_.skill).get(EmployeeSkill_.name),
                                    "%" + criteria.getGlobalFilter() + "%"
                                )
                        )
                    );
                specification = specification.and(orSpecification);
            }
        }
        return specification;
    }
}
