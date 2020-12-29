package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.domain.WithIdString;
import com.mycompany.myapp.repository.WithIdStringRepository;
import com.mycompany.myapp.service.dto.WithIdStringCriteria;
import com.mycompany.myapp.service.dto.WithIdStringDTO;
import com.mycompany.myapp.service.mapper.WithIdStringMapper;
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
 * Service for executing complex queries for {@link WithIdString} entities in the database.
 * The main input is a {@link WithIdStringCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link WithIdStringDTO} or a {@link Page} of {@link WithIdStringDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class WithIdStringQueryService extends QueryService<WithIdString> {

    private final Logger log = LoggerFactory.getLogger(WithIdStringQueryService.class);

    private final WithIdStringRepository withIdStringRepository;

    private final WithIdStringMapper withIdStringMapper;

    private final WithIdStringDetailsQueryService withIdStringDetailsQueryService;

    public WithIdStringQueryService(
        WithIdStringRepository withIdStringRepository,
        WithIdStringMapper withIdStringMapper,
        @Lazy WithIdStringDetailsQueryService withIdStringDetailsQueryService
    ) {
        this.withIdStringRepository = withIdStringRepository;
        this.withIdStringMapper = withIdStringMapper;
        this.withIdStringDetailsQueryService = withIdStringDetailsQueryService;
    }

    /**
     * Return a {@link List} of {@link WithIdStringDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<WithIdStringDTO> findByCriteria(WithIdStringCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<WithIdString> specification = createSpecification(criteria);
        return withIdStringMapper.toDto(withIdStringRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link WithIdStringDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<WithIdStringDTO> findByCriteria(WithIdStringCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<WithIdString> specification = createSpecification(criteria);
        return withIdStringRepository.findAll(specification, page).map(withIdStringMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(WithIdStringCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<WithIdString> specification = createSpecification(criteria);
        return withIdStringRepository.count(specification);
    }

    /**
     * Function to convert {@link WithIdStringCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    public Specification<WithIdString> createSpecification(WithIdStringCriteria criteria) {
        Specification<WithIdString> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildStringSpecification(criteria.getId(), WithIdString_.id));
            }

            if (criteria.getWithIdStringDetails() != null) {
                Specification<WithIdStringDetails> withIdStringDetailsSpecification =
                    this.withIdStringDetailsQueryService.createSpecification(criteria.getWithIdStringDetails());
                specification =
                    specification.and(
                        (root, query, criteriaBuilder) -> {
                            Root<WithIdStringDetails> withIdStringDetailsRoot = query.from(WithIdStringDetails.class);
                            return criteriaBuilder.and(
                                criteriaBuilder.equal(
                                    withIdStringDetailsRoot.get(WithIdStringDetails_.withIdStringId),
                                    root.get(WithIdString_.withIdStringDetails).get(WithIdStringDetails_.withIdStringId)
                                ),
                                withIdStringDetailsSpecification.toPredicate(withIdStringDetailsRoot, query, criteriaBuilder)
                            );
                        }
                    );
            }

            if (criteria.getGlobalFilter() != null) {
                Specification<WithIdString> orSpecification = Specification.where(null);
                orSpecification =
                    orSpecification.or(
                        (
                            (root, criteriaQuery, criteriaBuilder) ->
                                criteriaBuilder.like(root.get(WithIdString_.id), "%" + criteria.getGlobalFilter() + "%")
                        )
                    );
                specification = specification.and(orSpecification);
            }
        }
        return specification;
    }
}
