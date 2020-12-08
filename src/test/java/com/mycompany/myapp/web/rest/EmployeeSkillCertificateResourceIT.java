package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.CompositekeyApp;
import com.mycompany.myapp.domain.CertificateType;
import com.mycompany.myapp.domain.EmployeeSkill;
import com.mycompany.myapp.domain.EmployeeSkillCertificate;
import com.mycompany.myapp.domain.EmployeeSkillCertificateId;
import com.mycompany.myapp.repository.EmployeeSkillCertificateRepository;
import com.mycompany.myapp.service.EmployeeSkillCertificateQueryService;
import com.mycompany.myapp.service.dto.EmployeeSkillCertificateCriteria;
import com.mycompany.myapp.service.dto.EmployeeSkillCertificateDTO;
import com.mycompany.myapp.service.mapper.EmployeeSkillCertificateMapper;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link EmployeeSkillCertificateResource} REST controller.
 */
@SpringBootTest(classes = CompositekeyApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class EmployeeSkillCertificateResourceIT {

    public static final Integer DEFAULT_GRADE = 1;
    public static final Integer UPDATED_GRADE = 2;
    public static final Integer SMALLER_GRADE = 1 - 1;

    public static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    public static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());
    public static final LocalDate SMALLER_DATE = LocalDate.ofEpochDay(-1L);

    @Autowired
    private EmployeeSkillCertificateRepository employeeSkillCertificateRepository;

    @Autowired
    private EmployeeSkillCertificateMapper employeeSkillCertificateMapper;

    @Autowired
    private EmployeeSkillCertificateQueryService employeeSkillCertificateQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEmployeeSkillCertificateMockMvc;

    private EmployeeSkillCertificate employeeSkillCertificate;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeSkillCertificate createEntity(EntityManager em) {
        EmployeeSkillCertificate employeeSkillCertificate = new EmployeeSkillCertificate().grade(DEFAULT_GRADE).date(DEFAULT_DATE);
        // Add required entity
        CertificateType certificateType = TestUtil.findAll(em, CertificateType.class).stream().findFirst().orElse(null);
        if (certificateType == null) {
            certificateType = CertificateTypeResourceIT.createEntity(em);
            em.persist(certificateType);
            em.flush();
        }
        employeeSkillCertificate.setType(certificateType);
        // Add required entity
        EmployeeSkill newEmployeeSkill = EmployeeSkillResourceIT.createEntity(em);
        EmployeeSkill employeeSkill = TestUtil
            .findAll(em, EmployeeSkill.class)
            .stream()
            .filter(x -> x.getId().equals(newEmployeeSkill.getId()))
            .findAny()
            .orElse(null);
        if (employeeSkill == null) {
            employeeSkill = newEmployeeSkill;
            em.persist(employeeSkill);
            em.flush();
        }
        employeeSkillCertificate.setSkill(employeeSkill);
        employeeSkillCertificate.setId(
            new EmployeeSkillCertificateId(certificateType.getId(), employeeSkill.getName(), employeeSkill.getEmployee().getUsername())
        );
        return employeeSkillCertificate;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeSkillCertificate createUpdatedEntity(EntityManager em) {
        EmployeeSkillCertificate employeeSkillCertificate = new EmployeeSkillCertificate().grade(UPDATED_GRADE).date(UPDATED_DATE);
        // Add required entity
        CertificateType certificateType = TestUtil.findAll(em, CertificateType.class).stream().skip(1).findFirst().orElse(null);
        if (certificateType == null) {
            certificateType = CertificateTypeResourceIT.createUpdatedEntity(em);
            em.persist(certificateType);
            em.flush();
        }
        employeeSkillCertificate.setType(certificateType);
        // Add required entity
        EmployeeSkill newEmployeeSkill = EmployeeSkillResourceIT.createUpdatedEntity(em);
        EmployeeSkill employeeSkill = TestUtil
            .findAll(em, EmployeeSkill.class)
            .stream()
            .filter(x -> x.getId().equals(newEmployeeSkill.getId()))
            .findAny()
            .orElse(null);
        if (employeeSkill == null) {
            employeeSkill = newEmployeeSkill;
            em.persist(employeeSkill);
            em.flush();
        }
        employeeSkillCertificate.setSkill(employeeSkill);
        employeeSkillCertificate.setId(
            new EmployeeSkillCertificateId(certificateType.getId(), employeeSkill.getName(), employeeSkill.getEmployee().getUsername())
        );
        return employeeSkillCertificate;
    }

    @BeforeEach
    public void initTest() {
        employeeSkillCertificate = createEntity(em);
    }

    @Test
    @Transactional
    void createEmployeeSkillCertificate() throws Exception {
        int databaseSizeBeforeCreate = employeeSkillCertificateRepository.findAll().size();
        // Create the EmployeeSkillCertificate
        EmployeeSkillCertificateDTO employeeSkillCertificateDTO = employeeSkillCertificateMapper.toDto(employeeSkillCertificate);
        restEmployeeSkillCertificateMockMvc
            .perform(
                post("/api/employee-skill-certificates")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeSkillCertificateDTO))
            )
            .andExpect(status().isCreated());

        // Validate the EmployeeSkillCertificate in the database
        List<EmployeeSkillCertificate> employeeSkillCertificateList = employeeSkillCertificateRepository.findAll();
        assertThat(employeeSkillCertificateList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeSkillCertificate testEmployeeSkillCertificate = employeeSkillCertificateList.get(employeeSkillCertificateList.size() - 1);
        assertThat(testEmployeeSkillCertificate.getGrade()).isEqualTo(DEFAULT_GRADE);
        assertThat(testEmployeeSkillCertificate.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    void createEmployeeSkillCertificateWithExistingId() throws Exception {
        employeeSkillCertificateRepository.save(employeeSkillCertificate);
        int databaseSizeBeforeCreate = employeeSkillCertificateRepository.findAll().size();

        // Create the EmployeeSkillCertificate with an existing ID
        EmployeeSkillCertificateDTO employeeSkillCertificateDTO = employeeSkillCertificateMapper.toDto(employeeSkillCertificate);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeSkillCertificateMockMvc
            .perform(
                post("/api/employee-skill-certificates")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeSkillCertificateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeSkillCertificate in the database
        List<EmployeeSkillCertificate> employeeSkillCertificateList = employeeSkillCertificateRepository.findAll();
        assertThat(employeeSkillCertificateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkGradeIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeSkillCertificateRepository.findAll().size();
        // set the field null
        employeeSkillCertificate.setGrade(null);

        // Create the EmployeeSkillCertificate, which fails.
        EmployeeSkillCertificateDTO employeeSkillCertificateDTO = employeeSkillCertificateMapper.toDto(employeeSkillCertificate);

        restEmployeeSkillCertificateMockMvc
            .perform(
                post("/api/employee-skill-certificates")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeSkillCertificateDTO))
            )
            .andExpect(status().isBadRequest());

        List<EmployeeSkillCertificate> employeeSkillCertificateList = employeeSkillCertificateRepository.findAll();
        assertThat(employeeSkillCertificateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeSkillCertificateRepository.findAll().size();
        // set the field null
        employeeSkillCertificate.setDate(null);

        // Create the EmployeeSkillCertificate, which fails.
        EmployeeSkillCertificateDTO employeeSkillCertificateDTO = employeeSkillCertificateMapper.toDto(employeeSkillCertificate);

        restEmployeeSkillCertificateMockMvc
            .perform(
                post("/api/employee-skill-certificates")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeSkillCertificateDTO))
            )
            .andExpect(status().isBadRequest());

        List<EmployeeSkillCertificate> employeeSkillCertificateList = employeeSkillCertificateRepository.findAll();
        assertThat(employeeSkillCertificateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllEmployeeSkillCertificates() throws Exception {
        // Initialize the database
        employeeSkillCertificateRepository.saveAndFlush(employeeSkillCertificate);

        // Get all the employeeSkillCertificateList
        restEmployeeSkillCertificateMockMvc
            .perform(get("/api/employee-skill-certificates"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].grade").value(hasItem(DEFAULT_GRADE)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }

    @Test
    @Transactional
    void getEmployeeSkillCertificate() throws Exception {
        // Initialize the database
        employeeSkillCertificateRepository.saveAndFlush(employeeSkillCertificate);

        // Get the employeeSkillCertificate
        restEmployeeSkillCertificateMockMvc
            .perform(
                get(
                    "/api/employee-skill-certificates/{id}",
                    "typeId=" +
                    employeeSkillCertificate.getId().getTypeId() +
                    ";" +
                    "skillName=" +
                    employeeSkillCertificate.getId().getSkillName() +
                    ";" +
                    "skillEmployeeUsername=" +
                    employeeSkillCertificate.getId().getSkillEmployeeUsername()
                )
            )
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.grade").value(DEFAULT_GRADE))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    void getAllEmployeeSkillCertificatesByGradeIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeSkillCertificateRepository.saveAndFlush(employeeSkillCertificate);

        // Get all the employeeSkillCertificateList where grade equals to DEFAULT_GRADE
        defaultEmployeeSkillCertificateShouldBeFound("grade.equals=" + DEFAULT_GRADE);

        // Get all the employeeSkillCertificateList where grade equals to UPDATED_GRADE
        defaultEmployeeSkillCertificateShouldNotBeFound("grade.equals=" + UPDATED_GRADE);
    }

    @Test
    @Transactional
    void getAllEmployeeSkillCertificatesByGradeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeSkillCertificateRepository.saveAndFlush(employeeSkillCertificate);

        // Get all the employeeSkillCertificateList where grade not equals to DEFAULT_GRADE
        defaultEmployeeSkillCertificateShouldNotBeFound("grade.notEquals=" + DEFAULT_GRADE);

        // Get all the employeeSkillCertificateList where grade not equals to UPDATED_GRADE
        defaultEmployeeSkillCertificateShouldBeFound("grade.notEquals=" + UPDATED_GRADE);
    }

    @Test
    @Transactional
    void getAllEmployeeSkillCertificatesByGradeIsInShouldWork() throws Exception {
        // Initialize the database
        employeeSkillCertificateRepository.saveAndFlush(employeeSkillCertificate);

        // Get all the employeeSkillCertificateList where grade in DEFAULT_GRADE or UPDATED_GRADE
        defaultEmployeeSkillCertificateShouldBeFound("grade.in=" + DEFAULT_GRADE + "," + UPDATED_GRADE);

        // Get all the employeeSkillCertificateList where grade equals to UPDATED_GRADE
        defaultEmployeeSkillCertificateShouldNotBeFound("grade.in=" + UPDATED_GRADE);
    }

    @Test
    @Transactional
    void getAllEmployeeSkillCertificatesByGradeIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeSkillCertificateRepository.saveAndFlush(employeeSkillCertificate);

        // Get all the employeeSkillCertificateList where grade is not null
        defaultEmployeeSkillCertificateShouldBeFound("grade.specified=true");

        // Get all the employeeSkillCertificateList where grade is null
        defaultEmployeeSkillCertificateShouldNotBeFound("grade.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeSkillCertificatesByGradeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeSkillCertificateRepository.saveAndFlush(employeeSkillCertificate);

        // Get all the employeeSkillCertificateList where grade is greater than or equal to DEFAULT_GRADE
        defaultEmployeeSkillCertificateShouldBeFound("grade.greaterThanOrEqual=" + DEFAULT_GRADE);

        // Get all the employeeSkillCertificateList where grade is greater than or equal to UPDATED_GRADE
        defaultEmployeeSkillCertificateShouldNotBeFound("grade.greaterThanOrEqual=" + UPDATED_GRADE);
    }

    @Test
    @Transactional
    void getAllEmployeeSkillCertificatesByGradeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeSkillCertificateRepository.saveAndFlush(employeeSkillCertificate);

        // Get all the employeeSkillCertificateList where grade is less than or equal to DEFAULT_GRADE
        defaultEmployeeSkillCertificateShouldBeFound("grade.lessThanOrEqual=" + DEFAULT_GRADE);

        // Get all the employeeSkillCertificateList where grade is less than or equal to SMALLER_GRADE
        defaultEmployeeSkillCertificateShouldNotBeFound("grade.lessThanOrEqual=" + SMALLER_GRADE);
    }

    @Test
    @Transactional
    void getAllEmployeeSkillCertificatesByGradeIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeSkillCertificateRepository.saveAndFlush(employeeSkillCertificate);

        // Get all the employeeSkillCertificateList where grade is less than DEFAULT_GRADE
        defaultEmployeeSkillCertificateShouldNotBeFound("grade.lessThan=" + DEFAULT_GRADE);

        // Get all the employeeSkillCertificateList where grade is less than UPDATED_GRADE
        defaultEmployeeSkillCertificateShouldBeFound("grade.lessThan=" + UPDATED_GRADE);
    }

    @Test
    @Transactional
    void getAllEmployeeSkillCertificatesByGradeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeSkillCertificateRepository.saveAndFlush(employeeSkillCertificate);

        // Get all the employeeSkillCertificateList where grade is greater than DEFAULT_GRADE
        defaultEmployeeSkillCertificateShouldNotBeFound("grade.greaterThan=" + DEFAULT_GRADE);

        // Get all the employeeSkillCertificateList where grade is greater than SMALLER_GRADE
        defaultEmployeeSkillCertificateShouldBeFound("grade.greaterThan=" + SMALLER_GRADE);
    }

    @Test
    @Transactional
    void getAllEmployeeSkillCertificatesByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeSkillCertificateRepository.saveAndFlush(employeeSkillCertificate);

        // Get all the employeeSkillCertificateList where date equals to DEFAULT_DATE
        defaultEmployeeSkillCertificateShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the employeeSkillCertificateList where date equals to UPDATED_DATE
        defaultEmployeeSkillCertificateShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllEmployeeSkillCertificatesByDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        employeeSkillCertificateRepository.saveAndFlush(employeeSkillCertificate);

        // Get all the employeeSkillCertificateList where date not equals to DEFAULT_DATE
        defaultEmployeeSkillCertificateShouldNotBeFound("date.notEquals=" + DEFAULT_DATE);

        // Get all the employeeSkillCertificateList where date not equals to UPDATED_DATE
        defaultEmployeeSkillCertificateShouldBeFound("date.notEquals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllEmployeeSkillCertificatesByDateIsInShouldWork() throws Exception {
        // Initialize the database
        employeeSkillCertificateRepository.saveAndFlush(employeeSkillCertificate);

        // Get all the employeeSkillCertificateList where date in DEFAULT_DATE or UPDATED_DATE
        defaultEmployeeSkillCertificateShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the employeeSkillCertificateList where date equals to UPDATED_DATE
        defaultEmployeeSkillCertificateShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllEmployeeSkillCertificatesByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeSkillCertificateRepository.saveAndFlush(employeeSkillCertificate);

        // Get all the employeeSkillCertificateList where date is not null
        defaultEmployeeSkillCertificateShouldBeFound("date.specified=true");

        // Get all the employeeSkillCertificateList where date is null
        defaultEmployeeSkillCertificateShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    void getAllEmployeeSkillCertificatesByDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeSkillCertificateRepository.saveAndFlush(employeeSkillCertificate);

        // Get all the employeeSkillCertificateList where date is greater than or equal to DEFAULT_DATE
        defaultEmployeeSkillCertificateShouldBeFound("date.greaterThanOrEqual=" + DEFAULT_DATE);

        // Get all the employeeSkillCertificateList where date is greater than or equal to UPDATED_DATE
        defaultEmployeeSkillCertificateShouldNotBeFound("date.greaterThanOrEqual=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllEmployeeSkillCertificatesByDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        employeeSkillCertificateRepository.saveAndFlush(employeeSkillCertificate);

        // Get all the employeeSkillCertificateList where date is less than or equal to DEFAULT_DATE
        defaultEmployeeSkillCertificateShouldBeFound("date.lessThanOrEqual=" + DEFAULT_DATE);

        // Get all the employeeSkillCertificateList where date is less than or equal to SMALLER_DATE
        defaultEmployeeSkillCertificateShouldNotBeFound("date.lessThanOrEqual=" + SMALLER_DATE);
    }

    @Test
    @Transactional
    void getAllEmployeeSkillCertificatesByDateIsLessThanSomething() throws Exception {
        // Initialize the database
        employeeSkillCertificateRepository.saveAndFlush(employeeSkillCertificate);

        // Get all the employeeSkillCertificateList where date is less than DEFAULT_DATE
        defaultEmployeeSkillCertificateShouldNotBeFound("date.lessThan=" + DEFAULT_DATE);

        // Get all the employeeSkillCertificateList where date is less than UPDATED_DATE
        defaultEmployeeSkillCertificateShouldBeFound("date.lessThan=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    void getAllEmployeeSkillCertificatesByDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        employeeSkillCertificateRepository.saveAndFlush(employeeSkillCertificate);

        // Get all the employeeSkillCertificateList where date is greater than DEFAULT_DATE
        defaultEmployeeSkillCertificateShouldNotBeFound("date.greaterThan=" + DEFAULT_DATE);

        // Get all the employeeSkillCertificateList where date is greater than SMALLER_DATE
        defaultEmployeeSkillCertificateShouldBeFound("date.greaterThan=" + SMALLER_DATE);
    }

    @Test
    @Transactional
    void getAllEmployeeSkillCertificatesByTypeIsEqualToSomething() throws Exception {
        // Get already existing entity
        CertificateType type = employeeSkillCertificate.getType();
        employeeSkillCertificateRepository.saveAndFlush(employeeSkillCertificate);

        // Get all the employeeSkillCertificateList where type.id equals to type.getId()
        defaultEmployeeSkillCertificateShouldBeFound("type.id.equals=" + type.getId());

        // Get all the employeeSkillCertificateList where type.id equals to (type.getId() + 1)
        defaultEmployeeSkillCertificateShouldNotBeFound("type.id.equals=" + (type.getId() + 1));
    }

    @Test
    @Transactional
    void getAllEmployeeSkillCertificatesBySkillIsEqualToSomething() throws Exception {
        // Get already existing entity
        EmployeeSkill skill = employeeSkillCertificate.getSkill();
        employeeSkillCertificateRepository.saveAndFlush(employeeSkillCertificate);

        // Get all the employeeSkillCertificateList where skill.name equals to EmployeeSkillResourceIT.DEFAULT_NAME
        defaultEmployeeSkillCertificateShouldBeFound("skill.name.equals=" + EmployeeSkillResourceIT.DEFAULT_NAME);

        // Get all the employeeSkillCertificateList where skill.name equals to EmployeeSkillResourceIT.UPDATED_NAME
        defaultEmployeeSkillCertificateShouldNotBeFound("skill.name.equals=" + EmployeeSkillResourceIT.UPDATED_NAME);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmployeeSkillCertificateShouldBeFound(String filter) throws Exception {
        restEmployeeSkillCertificateMockMvc
            .perform(get("/api/employee-skill-certificates?" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].grade").value(hasItem(DEFAULT_GRADE)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));

        // Check, that the count call also returns 1
        restEmployeeSkillCertificateMockMvc
            .perform(get("/api/employee-skill-certificates/count?" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmployeeSkillCertificateShouldNotBeFound(String filter) throws Exception {
        restEmployeeSkillCertificateMockMvc
            .perform(get("/api/employee-skill-certificates?" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmployeeSkillCertificateMockMvc
            .perform(get("/api/employee-skill-certificates/count?" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingEmployeeSkillCertificate() throws Exception {
        // Get the employeeSkillCertificate
        restEmployeeSkillCertificateMockMvc
            .perform(
                get(
                    "/api/employee-skill-certificates/{id}",
                    "typeId=" +
                    employeeSkillCertificate.getId().getTypeId() +
                    ";" +
                    "skillName=" +
                    employeeSkillCertificate.getId().getSkillName() +
                    ";" +
                    "skillEmployeeUsername=" +
                    employeeSkillCertificate.getId().getSkillEmployeeUsername()
                )
            )
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void updateEmployeeSkillCertificate() throws Exception {
        // Initialize the database
        employeeSkillCertificateRepository.saveAndFlush(employeeSkillCertificate);

        int databaseSizeBeforeUpdate = employeeSkillCertificateRepository.findAll().size();

        // Update the employeeSkillCertificate
        EmployeeSkillCertificate updatedEmployeeSkillCertificate = employeeSkillCertificateRepository
            .findById(employeeSkillCertificate.getId())
            .get();
        // Disconnect from session so that the updates on updatedEmployeeSkillCertificate are not directly saved in db
        em.detach(updatedEmployeeSkillCertificate);
        updatedEmployeeSkillCertificate.grade(UPDATED_GRADE).date(UPDATED_DATE);
        EmployeeSkillCertificateDTO employeeSkillCertificateDTO = employeeSkillCertificateMapper.toDto(updatedEmployeeSkillCertificate);

        restEmployeeSkillCertificateMockMvc
            .perform(
                put("/api/employee-skill-certificates")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeSkillCertificateDTO))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeSkillCertificate in the database
        List<EmployeeSkillCertificate> employeeSkillCertificateList = employeeSkillCertificateRepository.findAll();
        assertThat(employeeSkillCertificateList).hasSize(databaseSizeBeforeUpdate);
        EmployeeSkillCertificate testEmployeeSkillCertificate = employeeSkillCertificateList.get(employeeSkillCertificateList.size() - 1);
        assertThat(testEmployeeSkillCertificate.getGrade()).isEqualTo(UPDATED_GRADE);
        assertThat(testEmployeeSkillCertificate.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    void updateNonExistingEmployeeSkillCertificate() throws Exception {
        int databaseSizeBeforeUpdate = employeeSkillCertificateRepository.findAll().size();

        // Create the EmployeeSkillCertificate
        EmployeeSkillCertificateDTO employeeSkillCertificateDTO = employeeSkillCertificateMapper.toDto(employeeSkillCertificate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeSkillCertificateMockMvc
            .perform(
                put("/api/employee-skill-certificates")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(employeeSkillCertificateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the EmployeeSkillCertificate in the database
        List<EmployeeSkillCertificate> employeeSkillCertificateList = employeeSkillCertificateRepository.findAll();
        assertThat(employeeSkillCertificateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEmployeeSkillCertificateWithPatch() throws Exception {
        // Initialize the database
        employeeSkillCertificateRepository.saveAndFlush(employeeSkillCertificate);

        int databaseSizeBeforeUpdate = employeeSkillCertificateRepository.findAll().size();

        // Update the employeeSkillCertificate using partial update
        EmployeeSkillCertificate partialUpdatedEmployeeSkillCertificate = new EmployeeSkillCertificate();
        partialUpdatedEmployeeSkillCertificate.setType(employeeSkillCertificate.getType());
        partialUpdatedEmployeeSkillCertificate.setSkill(employeeSkillCertificate.getSkill());

        partialUpdatedEmployeeSkillCertificate.grade(UPDATED_GRADE);

        restEmployeeSkillCertificateMockMvc
            .perform(
                patch("/api/employee-skill-certificates")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployeeSkillCertificate))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeSkillCertificate in the database
        List<EmployeeSkillCertificate> employeeSkillCertificateList = employeeSkillCertificateRepository.findAll();
        assertThat(employeeSkillCertificateList).hasSize(databaseSizeBeforeUpdate);
        EmployeeSkillCertificate testEmployeeSkillCertificate = employeeSkillCertificateList.get(employeeSkillCertificateList.size() - 1);
        assertThat(testEmployeeSkillCertificate.getGrade()).isEqualTo(UPDATED_GRADE);
        assertThat(testEmployeeSkillCertificate.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    void fullUpdateEmployeeSkillCertificateWithPatch() throws Exception {
        // Initialize the database
        employeeSkillCertificateRepository.saveAndFlush(employeeSkillCertificate);

        int databaseSizeBeforeUpdate = employeeSkillCertificateRepository.findAll().size();

        // Update the employeeSkillCertificate using partial update
        EmployeeSkillCertificate partialUpdatedEmployeeSkillCertificate = new EmployeeSkillCertificate();
        partialUpdatedEmployeeSkillCertificate.setType(employeeSkillCertificate.getType());
        partialUpdatedEmployeeSkillCertificate.setSkill(employeeSkillCertificate.getSkill());

        partialUpdatedEmployeeSkillCertificate.grade(UPDATED_GRADE).date(UPDATED_DATE);

        restEmployeeSkillCertificateMockMvc
            .perform(
                patch("/api/employee-skill-certificates")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployeeSkillCertificate))
            )
            .andExpect(status().isOk());

        // Validate the EmployeeSkillCertificate in the database
        List<EmployeeSkillCertificate> employeeSkillCertificateList = employeeSkillCertificateRepository.findAll();
        assertThat(employeeSkillCertificateList).hasSize(databaseSizeBeforeUpdate);
        EmployeeSkillCertificate testEmployeeSkillCertificate = employeeSkillCertificateList.get(employeeSkillCertificateList.size() - 1);
        assertThat(testEmployeeSkillCertificate.getGrade()).isEqualTo(UPDATED_GRADE);
        assertThat(testEmployeeSkillCertificate.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    void partialUpdateEmployeeSkillCertificateShouldThrown() throws Exception {
        // Update the employeeSkillCertificate without id should throw
        EmployeeSkillCertificate partialUpdatedEmployeeSkillCertificate = new EmployeeSkillCertificate();

        restEmployeeSkillCertificateMockMvc
            .perform(
                patch("/api/employee-skill-certificates")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEmployeeSkillCertificate))
            )
            .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    void deleteEmployeeSkillCertificate() throws Exception {
        // Initialize the database
        employeeSkillCertificateRepository.saveAndFlush(employeeSkillCertificate);

        int databaseSizeBeforeDelete = employeeSkillCertificateRepository.findAll().size();

        // Delete the employeeSkillCertificate
        restEmployeeSkillCertificateMockMvc
            .perform(
                delete(
                    "/api/employee-skill-certificates/{id}",
                    "typeId=" +
                    employeeSkillCertificate.getId().getTypeId() +
                    ";" +
                    "skillName=" +
                    employeeSkillCertificate.getId().getSkillName() +
                    ";" +
                    "skillEmployeeUsername=" +
                    employeeSkillCertificate.getId().getSkillEmployeeUsername()
                )
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmployeeSkillCertificate> employeeSkillCertificateList = employeeSkillCertificateRepository.findAll();
        assertThat(employeeSkillCertificateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
