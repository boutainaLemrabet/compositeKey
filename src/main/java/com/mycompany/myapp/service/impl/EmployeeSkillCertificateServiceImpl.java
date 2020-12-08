package com.mycompany.myapp.service.impl;

import com.mycompany.myapp.domain.EmployeeSkillCertificate;
import com.mycompany.myapp.domain.EmployeeSkillCertificateId;
import com.mycompany.myapp.repository.EmployeeSkillCertificateRepository;
import com.mycompany.myapp.service.EmployeeSkillCertificateService;
import com.mycompany.myapp.service.dto.EmployeeSkillCertificateDTO;
import com.mycompany.myapp.service.mapper.EmployeeSkillCertificateMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link EmployeeSkillCertificate}.
 */
@Service
@Transactional
public class EmployeeSkillCertificateServiceImpl implements EmployeeSkillCertificateService {

    private final Logger log = LoggerFactory.getLogger(EmployeeSkillCertificateServiceImpl.class);

    private final EmployeeSkillCertificateRepository employeeSkillCertificateRepository;

    private final EmployeeSkillCertificateMapper employeeSkillCertificateMapper;

    public EmployeeSkillCertificateServiceImpl(
        EmployeeSkillCertificateRepository employeeSkillCertificateRepository,
        EmployeeSkillCertificateMapper employeeSkillCertificateMapper
    ) {
        this.employeeSkillCertificateRepository = employeeSkillCertificateRepository;
        this.employeeSkillCertificateMapper = employeeSkillCertificateMapper;
    }

    @Override
    public EmployeeSkillCertificateDTO save(EmployeeSkillCertificateDTO employeeSkillCertificateDTO) {
        log.debug("Request to save EmployeeSkillCertificate : {}", employeeSkillCertificateDTO);
        EmployeeSkillCertificate employeeSkillCertificate = employeeSkillCertificateMapper.toEntity(employeeSkillCertificateDTO);
        employeeSkillCertificate = employeeSkillCertificateRepository.save(employeeSkillCertificate);
        return employeeSkillCertificateMapper.toDto(employeeSkillCertificate);
    }

    @Override
    public EmployeeSkillCertificateDTO partialUpdate(EmployeeSkillCertificateDTO employeeSkillCertificateDTO) {
        log.debug("Request to partially update EmployeeSkillCertificate : {}", employeeSkillCertificateDTO);

        return employeeSkillCertificateRepository
            .findById(employeeSkillCertificateMapper.toEntity(employeeSkillCertificateDTO).getId())
            .map(
                existingEmployeeSkillCertificate -> {
                    if (employeeSkillCertificateDTO.getGrade() != null) {
                        existingEmployeeSkillCertificate.setGrade(employeeSkillCertificateDTO.getGrade());
                    }

                    if (employeeSkillCertificateDTO.getDate() != null) {
                        existingEmployeeSkillCertificate.setDate(employeeSkillCertificateDTO.getDate());
                    }

                    return existingEmployeeSkillCertificate;
                }
            )
            .map(employeeSkillCertificateRepository::save)
            .map(employeeSkillCertificateMapper::toDto)
            .get();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<EmployeeSkillCertificateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all EmployeeSkillCertificates");
        return employeeSkillCertificateRepository.findAll(pageable).map(employeeSkillCertificateMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<EmployeeSkillCertificateDTO> findOne(EmployeeSkillCertificateId id) {
        log.debug("Request to get EmployeeSkillCertificate : {}", id);
        return employeeSkillCertificateRepository.findById(id).map(employeeSkillCertificateMapper::toDto);
    }

    @Override
    public void delete(EmployeeSkillCertificateId id) {
        log.debug("Request to delete EmployeeSkillCertificate : {}", id);
        employeeSkillCertificateRepository.deleteById(id);
    }
}
