package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.WithIdStringDetails;
import com.mycompany.myapp.repository.WithIdStringDetailsRepository;
import com.mycompany.myapp.repository.WithIdStringRepository;
import com.mycompany.myapp.service.WithIdStringDetailsService;
import com.mycompany.myapp.service.dto.WithIdStringDetailsDTO;
import com.mycompany.myapp.service.mapper.WithIdStringDetailsMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link WithIdStringDetails}.
 */
@Service
@Transactional
public class WithIdStringDetailsServiceImpl implements WithIdStringDetailsService {

    private final Logger log = LoggerFactory.getLogger(WithIdStringDetailsServiceImpl.class);

    private final WithIdStringDetailsRepository withIdStringDetailsRepository;

    private final WithIdStringDetailsMapper withIdStringDetailsMapper;

    private final WithIdStringRepository withIdStringRepository;

    public WithIdStringDetailsServiceImpl(
        WithIdStringDetailsRepository withIdStringDetailsRepository,
        WithIdStringDetailsMapper withIdStringDetailsMapper,
        WithIdStringRepository withIdStringRepository
    ) {
        this.withIdStringDetailsRepository = withIdStringDetailsRepository;
        this.withIdStringDetailsMapper = withIdStringDetailsMapper;
        this.withIdStringRepository = withIdStringRepository;
    }

    @Override
    public WithIdStringDetailsDTO save(WithIdStringDetailsDTO withIdStringDetailsDTO) {
        log.debug("Request to save WithIdStringDetails : {}", withIdStringDetailsDTO);
        WithIdStringDetails withIdStringDetails = withIdStringDetailsMapper.toEntity(withIdStringDetailsDTO);
        String withIdStringId = withIdStringDetailsDTO.getWithIdString().getId();
        withIdStringRepository.findById(withIdStringId).ifPresent(withIdStringDetails::withIdString);
        withIdStringDetails = withIdStringDetailsRepository.save(withIdStringDetails);
        return withIdStringDetailsMapper.toDto(withIdStringDetails);
    }

    @Override
    public WithIdStringDetailsDTO partialUpdate(WithIdStringDetailsDTO withIdStringDetailsDTO) {
        log.debug("Request to partially update WithIdStringDetails : {}", withIdStringDetailsDTO);

        return withIdStringDetailsRepository
            .findById(withIdStringDetailsDTO.getWithIdStringId())
            .map(
                existingWithIdStringDetails -> {
                    if (withIdStringDetailsDTO.getName() != null) {
                        existingWithIdStringDetails.setName(withIdStringDetailsDTO.getName());
                    }

                    return existingWithIdStringDetails;
                }
            )
            .map(withIdStringDetailsRepository::save)
            .map(withIdStringDetailsMapper::toDto)
            .get();
    }

    @Override
    @Transactional(readOnly = true)
    public List<WithIdStringDetailsDTO> findAll() {
        log.debug("Request to get all WithIdStringDetails");
        return withIdStringDetailsRepository
            .findAll()
            .stream()
            .map(withIdStringDetailsMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WithIdStringDetailsDTO> findOne(String id) {
        log.debug("Request to get WithIdStringDetails : {}", id);
        return withIdStringDetailsRepository.findById(id).map(withIdStringDetailsMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete WithIdStringDetails : {}", id);
        withIdStringDetailsRepository.deleteById(id);
    }
}
