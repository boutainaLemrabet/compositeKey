package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.EmployeeSkill;
import com.mycompany.myapp.repository.EmployeeSkillRepository;
import com.mycompany.myapp.service.dto.EmployeeSkillCriteria;
import com.mycompany.myapp.service.dto.EmployeeSkillDTO;
import com.mycompany.myapp.service.mapper.EmployeeSkillMapper;
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
 * Service for executing complex queries for {@link EmployeeSkill} entities in the database.
 * The main input is a {@link EmployeeSkillCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmployeeSkillDTO} or a {@link Page} of {@link EmployeeSkillDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmployeeSkillQueryService extends QueryService<EmployeeSkill> {

    private final Logger log = LoggerFactory.getLogger(EmployeeSkillQueryService.class);

    private final EmployeeSkillRepository employeeSkillRepository;

    private final EmployeeSkillMapper employeeSkillMapper;

    private final EmployeeSkillCertificateQueryService employeeSkillCertificateQueryService;

    private final TaskQueryService taskQueryService;

    private final EmployeeQueryService employeeQueryService;

    public EmployeeSkillQueryService(
        EmployeeSkillRepository employeeSkillRepository,
        EmployeeSkillMapper employeeSkillMapper,
        @Lazy EmployeeSkillCertificateQueryService employeeSkillCertificateQueryService,
        @Lazy TaskQueryService taskQueryService,
        @Lazy EmployeeQueryService employeeQueryService
    ) {
        this.employeeSkillRepository = employeeSkillRepository;
        this.employeeSkillMapper = employeeSkillMapper;
        this.employeeSkillCertificateQueryService = employeeSkillCertificateQueryService;
        this.taskQueryService = taskQueryService;
        this.employeeQueryService = employeeQueryService;
    }

    /**
     * Return a {@link List} of {@link EmployeeSkillDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmployeeSkillDTO> findByCriteria(EmployeeSkillCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<EmployeeSkill> specification = createSpecification(criteria);
        return employeeSkillMapper.toDto(employeeSkillRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EmployeeSkillDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeSkillDTO> findByCriteria(EmployeeSkillCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<EmployeeSkill> specification = createSpecification(criteria);
        return employeeSkillRepository.findAll(specification, page).map(employeeSkillMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmployeeSkillCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<EmployeeSkill> specification = createSpecification(criteria);
        return employeeSkillRepository.count(specification);
    }

    /**
     * Function to convert {@link EmployeeSkillCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    public Specification<EmployeeSkill> createSpecification(EmployeeSkillCriteria criteria) {
        Specification<EmployeeSkill> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), EmployeeSkill_.name));
            }
            if (criteria.getLevel() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getLevel(), EmployeeSkill_.level));
            }

            if (criteria.getEmployeeSkillCertificate() != null) {
                Specification<EmployeeSkillCertificate> employeeSkillCertificateSpecification =
                    this.employeeSkillCertificateQueryService.createSpecification(criteria.getEmployeeSkillCertificate());
                specification =
                    specification.and(
                        (root, query, criteriaBuilder) -> {
                            Root<EmployeeSkillCertificate> employeeSkillCertificateRoot = query.from(EmployeeSkillCertificate.class);
                            return criteriaBuilder.and(
                                criteriaBuilder.isMember(employeeSkillCertificateRoot, root.get(EmployeeSkill_.employeeSkillCertificates)),
                                employeeSkillCertificateSpecification.toPredicate(employeeSkillCertificateRoot, query, criteriaBuilder)
                            );
                        }
                    );
            }
            if (criteria.getTask() != null) {
                Specification<Task> taskSpecification = this.taskQueryService.createSpecification(criteria.getTask());
                specification =
                    specification.and(
                        (root, query, criteriaBuilder) -> {
                            Root<Task> taskRoot = query.from(Task.class);
                            return criteriaBuilder.and(
                                criteriaBuilder.isMember(taskRoot, root.get(EmployeeSkill_.tasks)),
                                taskSpecification.toPredicate(taskRoot, query, criteriaBuilder)
                            );
                        }
                    );
            }
            if (criteria.getEmployee() != null) {
                Specification<Employee> employeeSpecification = this.employeeQueryService.createSpecification(criteria.getEmployee());
                specification =
                    specification.and(
                        (root, query, criteriaBuilder) -> {
                            Root<Employee> employeeRoot = query.from(Employee.class);
                            return criteriaBuilder.and(
                                criteriaBuilder.equal(employeeRoot, root.get(EmployeeSkill_.employee)),
                                employeeSpecification.toPredicate(employeeRoot, query, criteriaBuilder)
                            );
                        }
                    );
            }
            if (criteria.getTeacher() != null) {
                Specification<Employee> employeeSpecification = this.employeeQueryService.createSpecification(criteria.getTeacher());
                specification =
                    specification.and(
                        (root, query, criteriaBuilder) -> {
                            Root<Employee> employeeRoot = query.from(Employee.class);
                            return criteriaBuilder.and(
                                criteriaBuilder.equal(employeeRoot, root.get(EmployeeSkill_.teacher)),
                                employeeSpecification.toPredicate(employeeRoot, query, criteriaBuilder)
                            );
                        }
                    );
            }
        }
        return specification;
    }
}
