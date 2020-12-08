package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.CertificateType;
import com.mycompany.myapp.repository.CertificateTypeRepository;
import com.mycompany.myapp.service.CertificateTypeService;
import com.mycompany.myapp.service.dto.CertificateTypeDTO;
import com.mycompany.myapp.service.mapper.CertificateTypeMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CertificateType}.
 */
@Service
@Transactional
public class CertificateTypeServiceImpl implements CertificateTypeService {

    private final Logger log = LoggerFactory.getLogger(CertificateTypeServiceImpl.class);

    private final CertificateTypeRepository certificateTypeRepository;

    private final CertificateTypeMapper certificateTypeMapper;

    public CertificateTypeServiceImpl(CertificateTypeRepository certificateTypeRepository, CertificateTypeMapper certificateTypeMapper) {
        this.certificateTypeRepository = certificateTypeRepository;
        this.certificateTypeMapper = certificateTypeMapper;
    }

    @Override
    public CertificateTypeDTO save(CertificateTypeDTO certificateTypeDTO) {
        log.debug("Request to save CertificateType : {}", certificateTypeDTO);
        CertificateType certificateType = certificateTypeMapper.toEntity(certificateTypeDTO);
        certificateType = certificateTypeRepository.save(certificateType);
        return certificateTypeMapper.toDto(certificateType);
    }

    @Override
    public CertificateTypeDTO partialUpdate(CertificateTypeDTO certificateTypeDTO) {
        log.debug("Request to partially update CertificateType : {}", certificateTypeDTO);

        return certificateTypeRepository
            .findById(certificateTypeDTO.getId())
            .map(
                existingCertificateType -> {
                    if (certificateTypeDTO.getName() != null) {
                        existingCertificateType.setName(certificateTypeDTO.getName());
                    }

                    return existingCertificateType;
                }
            )
            .map(certificateTypeRepository::save)
            .map(certificateTypeMapper::toDto)
            .get();
    }

    @Override
    @Transactional(readOnly = true)
    public List<CertificateTypeDTO> findAll() {
        log.debug("Request to get all CertificateTypes");
        return certificateTypeRepository
            .findAll()
            .stream()
            .map(certificateTypeMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CertificateTypeDTO> findOne(Long id) {
        log.debug("Request to get CertificateType : {}", id);
        return certificateTypeRepository.findById(id).map(certificateTypeMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CertificateType : {}", id);
        certificateTypeRepository.deleteById(id);
    }
}
