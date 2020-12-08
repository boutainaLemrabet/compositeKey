package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.CompositekeyApp;
import com.mycompany.myapp.domain.CertificateType;
import com.mycompany.myapp.domain.EmployeeSkillCertificate;
import com.mycompany.myapp.repository.CertificateTypeRepository;
import com.mycompany.myapp.service.CertificateTypeQueryService;
import com.mycompany.myapp.service.dto.CertificateTypeCriteria;
import com.mycompany.myapp.service.dto.CertificateTypeDTO;
import com.mycompany.myapp.service.mapper.CertificateTypeMapper;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CertificateTypeResource} REST controller.
 */
@SpringBootTest(classes = CompositekeyApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class CertificateTypeResourceIT {

    public static final String DEFAULT_NAME = "AAAAAAAAAA";
    public static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private CertificateTypeRepository certificateTypeRepository;

    @Autowired
    private CertificateTypeMapper certificateTypeMapper;

    @Autowired
    private CertificateTypeQueryService certificateTypeQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCertificateTypeMockMvc;

    private CertificateType certificateType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CertificateType createEntity(EntityManager em) {
        CertificateType certificateType = new CertificateType().name(DEFAULT_NAME);
        return certificateType;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CertificateType createUpdatedEntity(EntityManager em) {
        CertificateType certificateType = new CertificateType().name(UPDATED_NAME);
        return certificateType;
    }

    @BeforeEach
    public void initTest() {
        certificateType = createEntity(em);
    }

    @Test
    @Transactional
    void createCertificateType() throws Exception {
        int databaseSizeBeforeCreate = certificateTypeRepository.findAll().size();
        // Create the CertificateType
        CertificateTypeDTO certificateTypeDTO = certificateTypeMapper.toDto(certificateType);
        restCertificateTypeMockMvc
            .perform(
                post("/api/certificate-types")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(certificateTypeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CertificateType in the database
        List<CertificateType> certificateTypeList = certificateTypeRepository.findAll();
        assertThat(certificateTypeList).hasSize(databaseSizeBeforeCreate + 1);
        CertificateType testCertificateType = certificateTypeList.get(certificateTypeList.size() - 1);
        assertThat(testCertificateType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createCertificateTypeWithExistingId() throws Exception {
        certificateTypeRepository.save(certificateType);
        int databaseSizeBeforeCreate = certificateTypeRepository.findAll().size();

        // Create the CertificateType with an existing ID
        CertificateTypeDTO certificateTypeDTO = certificateTypeMapper.toDto(certificateType);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCertificateTypeMockMvc
            .perform(
                post("/api/certificate-types")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(certificateTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CertificateType in the database
        List<CertificateType> certificateTypeList = certificateTypeRepository.findAll();
        assertThat(certificateTypeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = certificateTypeRepository.findAll().size();
        // set the field null
        certificateType.setName(null);

        // Create the CertificateType, which fails.
        CertificateTypeDTO certificateTypeDTO = certificateTypeMapper.toDto(certificateType);

        restCertificateTypeMockMvc
            .perform(
                post("/api/certificate-types")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(certificateTypeDTO))
            )
            .andExpect(status().isBadRequest());

        List<CertificateType> certificateTypeList = certificateTypeRepository.findAll();
        assertThat(certificateTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCertificateTypes() throws Exception {
        // Initialize the database
        certificateTypeRepository.saveAndFlush(certificateType);

        // Get all the certificateTypeList
        restCertificateTypeMockMvc
            .perform(get("/api/certificate-types"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(certificateType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getCertificateType() throws Exception {
        // Initialize the database
        certificateTypeRepository.saveAndFlush(certificateType);

        // Get the certificateType
        restCertificateTypeMockMvc
            .perform(get("/api/certificate-types/{id}", certificateType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(certificateType.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getCertificateTypesByIdFiltering() throws Exception {
        // Initialize the database
        certificateTypeRepository.saveAndFlush(certificateType);

        Long id = certificateType.getId();

        defaultCertificateTypeShouldBeFound("id.equals=" + id);
        defaultCertificateTypeShouldNotBeFound("id.notEquals=" + id);

        defaultCertificateTypeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultCertificateTypeShouldNotBeFound("id.greaterThan=" + id);

        defaultCertificateTypeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultCertificateTypeShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllCertificateTypesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        certificateTypeRepository.saveAndFlush(certificateType);

        // Get all the certificateTypeList where name equals to DEFAULT_NAME
        defaultCertificateTypeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the certificateTypeList where name equals to UPDATED_NAME
        defaultCertificateTypeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCertificateTypesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        certificateTypeRepository.saveAndFlush(certificateType);

        // Get all the certificateTypeList where name not equals to DEFAULT_NAME
        defaultCertificateTypeShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the certificateTypeList where name not equals to UPDATED_NAME
        defaultCertificateTypeShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCertificateTypesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        certificateTypeRepository.saveAndFlush(certificateType);

        // Get all the certificateTypeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultCertificateTypeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the certificateTypeList where name equals to UPDATED_NAME
        defaultCertificateTypeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCertificateTypesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        certificateTypeRepository.saveAndFlush(certificateType);

        // Get all the certificateTypeList where name is not null
        defaultCertificateTypeShouldBeFound("name.specified=true");

        // Get all the certificateTypeList where name is null
        defaultCertificateTypeShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllCertificateTypesByNameContainsSomething() throws Exception {
        // Initialize the database
        certificateTypeRepository.saveAndFlush(certificateType);

        // Get all the certificateTypeList where name contains DEFAULT_NAME
        defaultCertificateTypeShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the certificateTypeList where name contains UPDATED_NAME
        defaultCertificateTypeShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCertificateTypesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        certificateTypeRepository.saveAndFlush(certificateType);

        // Get all the certificateTypeList where name does not contain DEFAULT_NAME
        defaultCertificateTypeShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the certificateTypeList where name does not contain UPDATED_NAME
        defaultCertificateTypeShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllCertificateTypesByEmployeeSkillCertificateIsEqualToSomething() throws Exception {
        // Initialize the database
        certificateTypeRepository.saveAndFlush(certificateType);
        EmployeeSkillCertificate employeeSkillCertificate = EmployeeSkillCertificateResourceIT.createEntity(em);
        em.persist(employeeSkillCertificate);
        em.flush();
        certificateType.addEmployeeSkillCertificate(employeeSkillCertificate);
        certificateTypeRepository.saveAndFlush(certificateType);

        // Get all the certificateTypeList where employeeSkillCertificate.grade equals to EmployeeSkillCertificateResourceIT.DEFAULT_GRADE
        defaultCertificateTypeShouldBeFound("employeeSkillCertificate.grade.equals=" + EmployeeSkillCertificateResourceIT.DEFAULT_GRADE);

        // Get all the certificateTypeList where employeeSkillCertificate.grade equals to EmployeeSkillCertificateResourceIT.UPDATED_GRADE
        defaultCertificateTypeShouldNotBeFound("employeeSkillCertificate.grade.equals=" + EmployeeSkillCertificateResourceIT.UPDATED_GRADE);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCertificateTypeShouldBeFound(String filter) throws Exception {
        restCertificateTypeMockMvc
            .perform(get("/api/certificate-types?" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(certificateType.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restCertificateTypeMockMvc
            .perform(get("/api/certificate-types/count?" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCertificateTypeShouldNotBeFound(String filter) throws Exception {
        restCertificateTypeMockMvc
            .perform(get("/api/certificate-types?" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCertificateTypeMockMvc
            .perform(get("/api/certificate-types/count?" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingCertificateType() throws Exception {
        // Get the certificateType
        restCertificateTypeMockMvc.perform(get("/api/certificate-types/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void updateCertificateType() throws Exception {
        // Initialize the database
        certificateTypeRepository.saveAndFlush(certificateType);

        int databaseSizeBeforeUpdate = certificateTypeRepository.findAll().size();

        // Update the certificateType
        CertificateType updatedCertificateType = certificateTypeRepository.findById(certificateType.getId()).get();
        // Disconnect from session so that the updates on updatedCertificateType are not directly saved in db
        em.detach(updatedCertificateType);
        updatedCertificateType.name(UPDATED_NAME);
        CertificateTypeDTO certificateTypeDTO = certificateTypeMapper.toDto(updatedCertificateType);

        restCertificateTypeMockMvc
            .perform(
                put("/api/certificate-types")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(certificateTypeDTO))
            )
            .andExpect(status().isOk());

        // Validate the CertificateType in the database
        List<CertificateType> certificateTypeList = certificateTypeRepository.findAll();
        assertThat(certificateTypeList).hasSize(databaseSizeBeforeUpdate);
        CertificateType testCertificateType = certificateTypeList.get(certificateTypeList.size() - 1);
        assertThat(testCertificateType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void updateNonExistingCertificateType() throws Exception {
        int databaseSizeBeforeUpdate = certificateTypeRepository.findAll().size();

        // Create the CertificateType
        CertificateTypeDTO certificateTypeDTO = certificateTypeMapper.toDto(certificateType);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCertificateTypeMockMvc
            .perform(
                put("/api/certificate-types")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(certificateTypeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CertificateType in the database
        List<CertificateType> certificateTypeList = certificateTypeRepository.findAll();
        assertThat(certificateTypeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCertificateTypeWithPatch() throws Exception {
        // Initialize the database
        certificateTypeRepository.saveAndFlush(certificateType);

        int databaseSizeBeforeUpdate = certificateTypeRepository.findAll().size();

        // Update the certificateType using partial update
        CertificateType partialUpdatedCertificateType = new CertificateType();
        partialUpdatedCertificateType.setId(certificateType.getId());

        restCertificateTypeMockMvc
            .perform(
                patch("/api/certificate-types")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCertificateType))
            )
            .andExpect(status().isOk());

        // Validate the CertificateType in the database
        List<CertificateType> certificateTypeList = certificateTypeRepository.findAll();
        assertThat(certificateTypeList).hasSize(databaseSizeBeforeUpdate);
        CertificateType testCertificateType = certificateTypeList.get(certificateTypeList.size() - 1);
        assertThat(testCertificateType.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void fullUpdateCertificateTypeWithPatch() throws Exception {
        // Initialize the database
        certificateTypeRepository.saveAndFlush(certificateType);

        int databaseSizeBeforeUpdate = certificateTypeRepository.findAll().size();

        // Update the certificateType using partial update
        CertificateType partialUpdatedCertificateType = new CertificateType();
        partialUpdatedCertificateType.setId(certificateType.getId());

        partialUpdatedCertificateType.name(UPDATED_NAME);

        restCertificateTypeMockMvc
            .perform(
                patch("/api/certificate-types")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCertificateType))
            )
            .andExpect(status().isOk());

        // Validate the CertificateType in the database
        List<CertificateType> certificateTypeList = certificateTypeRepository.findAll();
        assertThat(certificateTypeList).hasSize(databaseSizeBeforeUpdate);
        CertificateType testCertificateType = certificateTypeList.get(certificateTypeList.size() - 1);
        assertThat(testCertificateType.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void partialUpdateCertificateTypeShouldThrown() throws Exception {
        // Update the certificateType without id should throw
        CertificateType partialUpdatedCertificateType = new CertificateType();

        restCertificateTypeMockMvc
            .perform(
                patch("/api/certificate-types")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCertificateType))
            )
            .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    void deleteCertificateType() throws Exception {
        // Initialize the database
        certificateTypeRepository.saveAndFlush(certificateType);

        int databaseSizeBeforeDelete = certificateTypeRepository.findAll().size();

        // Delete the certificateType
        restCertificateTypeMockMvc
            .perform(delete("/api/certificate-types/{id}", certificateType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CertificateType> certificateTypeList = certificateTypeRepository.findAll();
        assertThat(certificateTypeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
