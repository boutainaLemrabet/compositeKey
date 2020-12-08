package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.WithIdString;
import com.mycompany.myapp.repository.WithIdStringRepository;
import com.mycompany.myapp.service.WithIdStringService;
import com.mycompany.myapp.service.dto.WithIdStringDTO;
import com.mycompany.myapp.service.mapper.WithIdStringMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link WithIdString}.
 */
@Service
@Transactional
public class WithIdStringServiceImpl implements WithIdStringService {

    private final Logger log = LoggerFactory.getLogger(WithIdStringServiceImpl.class);

    private final WithIdStringRepository withIdStringRepository;

    private final WithIdStringMapper withIdStringMapper;

    public WithIdStringServiceImpl(WithIdStringRepository withIdStringRepository, WithIdStringMapper withIdStringMapper) {
        this.withIdStringRepository = withIdStringRepository;
        this.withIdStringMapper = withIdStringMapper;
    }

    @Override
    public WithIdStringDTO save(WithIdStringDTO withIdStringDTO) {
        log.debug("Request to save WithIdString : {}", withIdStringDTO);
        WithIdString withIdString = withIdStringMapper.toEntity(withIdStringDTO);
        withIdString = withIdStringRepository.save(withIdString);
        return withIdStringMapper.toDto(withIdString);
    }

    @Override
    public WithIdStringDTO partialUpdate(WithIdStringDTO withIdStringDTO) {
        log.debug("Request to partially update WithIdString : {}", withIdStringDTO);

        return withIdStringRepository
            .findById(withIdStringDTO.getId())
            .map(
                existingWithIdString -> {
                    return existingWithIdString;
                }
            )
            .map(withIdStringRepository::save)
            .map(withIdStringMapper::toDto)
            .get();
    }

    @Override
    @Transactional(readOnly = true)
    public List<WithIdStringDTO> findAll() {
        log.debug("Request to get all WithIdStrings");
        return withIdStringRepository.findAll().stream().map(withIdStringMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the withIdStrings where WithIdStringDetails is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<WithIdStringDTO> findAllWhereWithIdStringDetailsIsNull() {
        log.debug("Request to get all withIdStrings where WithIdStringDetails is null");
        return StreamSupport
            .stream(withIdStringRepository.findAll().spliterator(), false)
            .filter(withIdString -> withIdString.getWithIdStringDetails() == null)
            .map(withIdStringMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<WithIdStringDTO> findOne(String id) {
        log.debug("Request to get WithIdString : {}", id);
        return withIdStringRepository.findById(id).map(withIdStringMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete WithIdString : {}", id);
        withIdStringRepository.deleteById(id);
    }
}
