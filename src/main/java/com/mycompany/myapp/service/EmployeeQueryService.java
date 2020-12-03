package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.Employee;
import com.mycompany.myapp.repository.EmployeeRepository;
import com.mycompany.myapp.service.dto.EmployeeCriteria;
import com.mycompany.myapp.service.dto.EmployeeDTO;
import com.mycompany.myapp.service.mapper.EmployeeMapper;
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
 * Service for executing complex queries for {@link Employee} entities in the database.
 * The main input is a {@link EmployeeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link EmployeeDTO} or a {@link Page} of {@link EmployeeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EmployeeQueryService extends QueryService<Employee> {

    private final Logger log = LoggerFactory.getLogger(EmployeeQueryService.class);

    private final EmployeeRepository employeeRepository;

    private final EmployeeMapper employeeMapper;

    private final EmployeeQueryService employeeQueryService;

    private final EmployeeSkillQueryService employeeSkillQueryService;

    public EmployeeQueryService(
        EmployeeRepository employeeRepository,
        EmployeeMapper employeeMapper,
        @Lazy EmployeeQueryService employeeQueryService,
        @Lazy EmployeeSkillQueryService employeeSkillQueryService
    ) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
        this.employeeQueryService = employeeQueryService;
        this.employeeSkillQueryService = employeeSkillQueryService;
    }

    /**
     * Return a {@link List} of {@link EmployeeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<EmployeeDTO> findByCriteria(EmployeeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Employee> specification = createSpecification(criteria);
        return employeeMapper.toDto(employeeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link EmployeeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<EmployeeDTO> findByCriteria(EmployeeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Employee> specification = createSpecification(criteria);
        return employeeRepository.findAll(specification, page).map(employeeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(EmployeeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Employee> specification = createSpecification(criteria);
        return employeeRepository.count(specification);
    }

    /**
     * Function to convert {@link EmployeeCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    public Specification<Employee> createSpecification(EmployeeCriteria criteria) {
        Specification<Employee> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getUsername() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUsername(), Employee_.username));
            }
            if (criteria.getFullname() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFullname(), Employee_.fullname));
            }

            if (criteria.getTeamMember() != null) {
                Specification<Employee> employeeSpecification = this.employeeQueryService.createSpecification(criteria.getTeamMember());
                specification =
                    specification.and(
                        (root, query, criteriaBuilder) -> {
                            Root<Employee> employeeRoot = query.from(Employee.class);
                            return criteriaBuilder.and(
                                criteriaBuilder.isMember(employeeRoot, root.get(Employee_.teamMembers)),
                                employeeSpecification.toPredicate(employeeRoot, query, criteriaBuilder)
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
                                criteriaBuilder.isMember(employeeSkillRoot, root.get(Employee_.skills)),
                                employeeSkillSpecification.toPredicate(employeeSkillRoot, query, criteriaBuilder)
                            );
                        }
                    );
            }
            if (criteria.getTaughtSkill() != null) {
                Specification<EmployeeSkill> employeeSkillSpecification =
                    this.employeeSkillQueryService.createSpecification(criteria.getTaughtSkill());
                specification =
                    specification.and(
                        (root, query, criteriaBuilder) -> {
                            Root<EmployeeSkill> employeeSkillRoot = query.from(EmployeeSkill.class);
                            return criteriaBuilder.and(
                                criteriaBuilder.isMember(employeeSkillRoot, root.get(Employee_.taughtSkills)),
                                employeeSkillSpecification.toPredicate(employeeSkillRoot, query, criteriaBuilder)
                            );
                        }
                    );
            }
            if (criteria.getManager() != null) {
                Specification<Employee> employeeSpecification = this.employeeQueryService.createSpecification(criteria.getManager());
                specification =
                    specification.and(
                        (root, query, criteriaBuilder) -> {
                            Root<Employee> employeeRoot = query.from(Employee.class);
                            return criteriaBuilder.and(
                                criteriaBuilder.equal(employeeRoot, root.get(Employee_.manager)),
                                employeeSpecification.toPredicate(employeeRoot, query, criteriaBuilder)
                            );
                        }
                    );
            }
        }
        return specification;
    }
}
