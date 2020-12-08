package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.WithIdStringDetails;
import com.mycompany.myapp.repository.WithIdStringDetailsRepository;
import com.mycompany.myapp.service.dto.WithIdStringDetailsCriteria;
import com.mycompany.myapp.service.dto.WithIdStringDetailsDTO;
import com.mycompany.myapp.service.mapper.WithIdStringDetailsMapper;
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
 * Service for executing complex queries for {@link WithIdStringDetails} entities in the database.
 * The main input is a {@link WithIdStringDetailsCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link WithIdStringDetailsDTO} or a {@link Page} of {@link WithIdStringDetailsDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class WithIdStringDetailsQueryService extends QueryService<WithIdStringDetails> {

    private final Logger log = LoggerFactory.getLogger(WithIdStringDetailsQueryService.class);

    private final WithIdStringDetailsRepository withIdStringDetailsRepository;

    private final WithIdStringDetailsMapper withIdStringDetailsMapper;

    private final WithIdStringQueryService withIdStringQueryService;

    public WithIdStringDetailsQueryService(
        WithIdStringDetailsRepository withIdStringDetailsRepository,
        WithIdStringDetailsMapper withIdStringDetailsMapper,
        @Lazy WithIdStringQueryService withIdStringQueryService
    ) {
        this.withIdStringDetailsRepository = withIdStringDetailsRepository;
        this.withIdStringDetailsMapper = withIdStringDetailsMapper;
        this.withIdStringQueryService = withIdStringQueryService;
    }

    /**
     * Return a {@link List} of {@link WithIdStringDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<WithIdStringDetailsDTO> findByCriteria(WithIdStringDetailsCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<WithIdStringDetails> specification = createSpecification(criteria);
        return withIdStringDetailsMapper.toDto(withIdStringDetailsRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link WithIdStringDetailsDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<WithIdStringDetailsDTO> findByCriteria(WithIdStringDetailsCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<WithIdStringDetails> specification = createSpecification(criteria);
        return withIdStringDetailsRepository.findAll(specification, page).map(withIdStringDetailsMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(WithIdStringDetailsCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<WithIdStringDetails> specification = createSpecification(criteria);
        return withIdStringDetailsRepository.count(specification);
    }

    /**
     * Function to convert {@link WithIdStringDetailsCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    public Specification<WithIdStringDetails> createSpecification(WithIdStringDetailsCriteria criteria) {
        Specification<WithIdStringDetails> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getWithIdStringId() != null) {
                specification =
                    specification.and(buildStringSpecification(criteria.getWithIdStringId(), WithIdStringDetails_.withIdStringId));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), WithIdStringDetails_.name));
            }

            if (criteria.getWithIdString() != null) {
                Specification<WithIdString> withIdStringSpecification =
                    this.withIdStringQueryService.createSpecification(criteria.getWithIdString());
                specification =
                    specification.and(
                        (root, query, criteriaBuilder) -> {
                            Root<WithIdString> withIdStringRoot = query.from(WithIdString.class);
                            return criteriaBuilder.and(
                                criteriaBuilder.equal(
                                    withIdStringRoot.get(WithIdString_.id),
                                    root.get(WithIdStringDetails_.withIdString).get(WithIdString_.id)
                                ),
                                withIdStringSpecification.toPredicate(withIdStringRoot, query, criteriaBuilder)
                            );
                        }
                    );
            }
        }
        return specification;
    }
}
