package com.mycompany.myapp.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.mycompany.myapp.CompositekeyApp;
import com.mycompany.myapp.domain.WithIdString;
import com.mycompany.myapp.domain.WithIdStringDetails;
import com.mycompany.myapp.repository.WithIdStringDetailsRepository;
import com.mycompany.myapp.service.WithIdStringDetailsQueryService;
import com.mycompany.myapp.service.dto.WithIdStringDetailsCriteria;
import com.mycompany.myapp.service.dto.WithIdStringDetailsDTO;
import com.mycompany.myapp.service.mapper.WithIdStringDetailsMapper;
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
 * Integration tests for the {@link WithIdStringDetailsResource} REST controller.
 */
@SpringBootTest(classes = CompositekeyApp.class)
@AutoConfigureMockMvc
@WithMockUser
public class WithIdStringDetailsResourceIT {

    public static final String DEFAULT_NAME = "AAAAAAAAAA";
    public static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private WithIdStringDetailsRepository withIdStringDetailsRepository;

    @Autowired
    private WithIdStringDetailsMapper withIdStringDetailsMapper;

    @Autowired
    private WithIdStringDetailsQueryService withIdStringDetailsQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWithIdStringDetailsMockMvc;

    private WithIdStringDetails withIdStringDetails;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WithIdStringDetails createEntity(EntityManager em) {
        WithIdStringDetails withIdStringDetails = new WithIdStringDetails().name(DEFAULT_NAME);
        // Add required entity
        WithIdString newWithIdString = WithIdStringResourceIT.createEntity(em);
        WithIdString withIdString = TestUtil
            .findAll(em, WithIdString.class)
            .stream()
            .filter(x -> x.getId().equals(newWithIdString.getId()))
            .findAny()
            .orElse(null);
        if (withIdString == null) {
            withIdString = newWithIdString;
            em.persist(withIdString);
            em.flush();
        }
        withIdStringDetails.setWithIdString(withIdString);
        return withIdStringDetails;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static WithIdStringDetails createUpdatedEntity(EntityManager em) {
        WithIdStringDetails withIdStringDetails = new WithIdStringDetails().name(UPDATED_NAME);
        // Add required entity
        WithIdString newWithIdString = WithIdStringResourceIT.createUpdatedEntity(em);
        WithIdString withIdString = TestUtil
            .findAll(em, WithIdString.class)
            .stream()
            .filter(x -> x.getId().equals(newWithIdString.getId()))
            .findAny()
            .orElse(null);
        if (withIdString == null) {
            withIdString = newWithIdString;
            em.persist(withIdString);
            em.flush();
        }
        withIdStringDetails.setWithIdString(withIdString);
        return withIdStringDetails;
    }

    @BeforeEach
    public void initTest() {
        withIdStringDetails = createEntity(em);
    }

    @Test
    @Transactional
    void createWithIdStringDetails() throws Exception {
        int databaseSizeBeforeCreate = withIdStringDetailsRepository.findAll().size();
        // Create the WithIdStringDetails
        WithIdStringDetailsDTO withIdStringDetailsDTO = withIdStringDetailsMapper.toDto(withIdStringDetails);
        restWithIdStringDetailsMockMvc
            .perform(
                post("/api/with-id-string-details")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(withIdStringDetailsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the WithIdStringDetails in the database
        List<WithIdStringDetails> withIdStringDetailsList = withIdStringDetailsRepository.findAll();
        assertThat(withIdStringDetailsList).hasSize(databaseSizeBeforeCreate + 1);
        WithIdStringDetails testWithIdStringDetails = withIdStringDetailsList.get(withIdStringDetailsList.size() - 1);
        assertThat(testWithIdStringDetails.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void createWithIdStringDetailsWithExistingId() throws Exception {
        withIdStringDetailsRepository.save(withIdStringDetails);
        int databaseSizeBeforeCreate = withIdStringDetailsRepository.findAll().size();

        // Create the WithIdStringDetails with an existing ID
        WithIdStringDetailsDTO withIdStringDetailsDTO = withIdStringDetailsMapper.toDto(withIdStringDetails);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWithIdStringDetailsMockMvc
            .perform(
                post("/api/with-id-string-details")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(withIdStringDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WithIdStringDetails in the database
        List<WithIdStringDetails> withIdStringDetailsList = withIdStringDetailsRepository.findAll();
        assertThat(withIdStringDetailsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void updateWithIdStringDetailsMapsIdAssociationWithNewId() throws Exception {
        // Initialize the database
        withIdStringDetailsRepository.saveAndFlush(withIdStringDetails);
        int databaseSizeBeforeCreate = withIdStringDetailsRepository.findAll().size();

        // Add a new parent entity
        WithIdString withIdString = WithIdStringResourceIT.createUpdatedEntity(em);
        em.persist(withIdString);
        em.flush();

        // Load the withIdStringDetails
        WithIdStringDetails updatedWithIdStringDetails = withIdStringDetailsRepository
            .findById(withIdStringDetails.getWithIdStringId())
            .get();
        assertThat(updatedWithIdStringDetails).isNotNull();
        // Disconnect from session so that the updates on updatedWithIdStringDetails are not directly saved in db
        em.detach(updatedWithIdStringDetails);

        // Update the WithIdString with new association value
        updatedWithIdStringDetails.setWithIdString(withIdString);
        WithIdStringDetailsDTO updatedWithIdStringDetailsDTO = withIdStringDetailsMapper.toDto(updatedWithIdStringDetails);
        assertThat(updatedWithIdStringDetailsDTO).isNotNull();

        // Update the entity
        restWithIdStringDetailsMockMvc
            .perform(
                put("/api/with-id-string-details")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedWithIdStringDetailsDTO))
            )
            .andExpect(status().isOk());

        // Validate the WithIdStringDetails in the database
        List<WithIdStringDetails> withIdStringDetailsList = withIdStringDetailsRepository.findAll();
        assertThat(withIdStringDetailsList).hasSize(databaseSizeBeforeCreate);
        WithIdStringDetails testWithIdStringDetails = withIdStringDetailsList.get(withIdStringDetailsList.size() - 1);
        // Validate the id for MapsId, the ids must be same
        // Uncomment the following line for assertion. However, please note that there is a known issue and uncommenting will fail the test.
        // Please look at https://github.com/jhipster/generator-jhipster/issues/9100. You can modify this test as necessary.
        // assertThat(testWithIdStringDetails.getWithIdStringId()).isEqualTo(testWithIdStringDetails.get().getWithIdStringId());
    }

    @Test
    @Transactional
    void getAllWithIdStringDetails() throws Exception {
        // Initialize the database
        withIdStringDetailsRepository.saveAndFlush(withIdStringDetails);

        // Get all the withIdStringDetailsList
        restWithIdStringDetailsMockMvc
            .perform(get("/api/with-id-string-details"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].withIdStringId").value(hasItem(withIdStringDetails.getWithIdStringId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }

    @Test
    @Transactional
    void getWithIdStringDetails() throws Exception {
        // Initialize the database
        withIdStringDetailsRepository.saveAndFlush(withIdStringDetails);

        // Get the withIdStringDetails
        restWithIdStringDetailsMockMvc
            .perform(get("/api/with-id-string-details/{withIdStringId}", withIdStringDetails.getWithIdStringId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.withIdStringId").value(withIdStringDetails.getWithIdStringId()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    void getWithIdStringDetailsByIdFiltering() throws Exception {
        // Initialize the database
        withIdStringDetailsRepository.saveAndFlush(withIdStringDetails);

        String withIdStringId = withIdStringDetails.getWithIdStringId();

        defaultWithIdStringDetailsShouldBeFound("withIdStringId.equals=" + withIdStringId);
        defaultWithIdStringDetailsShouldNotBeFound("withIdStringId.notEquals=" + withIdStringId);
    }

    @Test
    @Transactional
    void getAllWithIdStringDetailsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        withIdStringDetailsRepository.saveAndFlush(withIdStringDetails);

        // Get all the withIdStringDetailsList where name equals to DEFAULT_NAME
        defaultWithIdStringDetailsShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the withIdStringDetailsList where name equals to UPDATED_NAME
        defaultWithIdStringDetailsShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllWithIdStringDetailsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        withIdStringDetailsRepository.saveAndFlush(withIdStringDetails);

        // Get all the withIdStringDetailsList where name not equals to DEFAULT_NAME
        defaultWithIdStringDetailsShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the withIdStringDetailsList where name not equals to UPDATED_NAME
        defaultWithIdStringDetailsShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllWithIdStringDetailsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        withIdStringDetailsRepository.saveAndFlush(withIdStringDetails);

        // Get all the withIdStringDetailsList where name in DEFAULT_NAME or UPDATED_NAME
        defaultWithIdStringDetailsShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the withIdStringDetailsList where name equals to UPDATED_NAME
        defaultWithIdStringDetailsShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllWithIdStringDetailsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        withIdStringDetailsRepository.saveAndFlush(withIdStringDetails);

        // Get all the withIdStringDetailsList where name is not null
        defaultWithIdStringDetailsShouldBeFound("name.specified=true");

        // Get all the withIdStringDetailsList where name is null
        defaultWithIdStringDetailsShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllWithIdStringDetailsByNameContainsSomething() throws Exception {
        // Initialize the database
        withIdStringDetailsRepository.saveAndFlush(withIdStringDetails);

        // Get all the withIdStringDetailsList where name contains DEFAULT_NAME
        defaultWithIdStringDetailsShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the withIdStringDetailsList where name contains UPDATED_NAME
        defaultWithIdStringDetailsShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllWithIdStringDetailsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        withIdStringDetailsRepository.saveAndFlush(withIdStringDetails);

        // Get all the withIdStringDetailsList where name does not contain DEFAULT_NAME
        defaultWithIdStringDetailsShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the withIdStringDetailsList where name does not contain UPDATED_NAME
        defaultWithIdStringDetailsShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllWithIdStringDetailsByWithIdStringIsEqualToSomething() throws Exception {
        // Get already existing entity
        WithIdString withIdString = withIdStringDetails.getWithIdString();
        withIdStringDetailsRepository.saveAndFlush(withIdStringDetails);

        // Get all the withIdStringDetailsList where withIdString.id equals to WithIdStringResourceIT.DEFAULT_ID
        defaultWithIdStringDetailsShouldBeFound("withIdString.id.equals=" + WithIdStringResourceIT.DEFAULT_ID);

        // Get all the withIdStringDetailsList where withIdString.id equals to WithIdStringResourceIT.UPDATED_ID
        defaultWithIdStringDetailsShouldNotBeFound("withIdString.id.equals=" + WithIdStringResourceIT.UPDATED_ID);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultWithIdStringDetailsShouldBeFound(String filter) throws Exception {
        restWithIdStringDetailsMockMvc
            .perform(get("/api/with-id-string-details?" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].withIdStringId").value(hasItem(withIdStringDetails.getWithIdStringId())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));

        // Check, that the count call also returns 1
        restWithIdStringDetailsMockMvc
            .perform(get("/api/with-id-string-details/count?" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultWithIdStringDetailsShouldNotBeFound(String filter) throws Exception {
        restWithIdStringDetailsMockMvc
            .perform(get("/api/with-id-string-details?" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restWithIdStringDetailsMockMvc
            .perform(get("/api/with-id-string-details/count?" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingWithIdStringDetails() throws Exception {
        // Get the withIdStringDetails
        restWithIdStringDetailsMockMvc.perform(get("/api/with-id-string-details/{id}", Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void updateWithIdStringDetails() throws Exception {
        // Initialize the database
        withIdStringDetailsRepository.saveAndFlush(withIdStringDetails);

        int databaseSizeBeforeUpdate = withIdStringDetailsRepository.findAll().size();

        // Update the withIdStringDetails
        WithIdStringDetails updatedWithIdStringDetails = withIdStringDetailsRepository
            .findById(withIdStringDetails.getWithIdStringId())
            .get();
        // Disconnect from session so that the updates on updatedWithIdStringDetails are not directly saved in db
        em.detach(updatedWithIdStringDetails);
        updatedWithIdStringDetails.name(UPDATED_NAME);
        WithIdStringDetailsDTO withIdStringDetailsDTO = withIdStringDetailsMapper.toDto(updatedWithIdStringDetails);

        restWithIdStringDetailsMockMvc
            .perform(
                put("/api/with-id-string-details")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(withIdStringDetailsDTO))
            )
            .andExpect(status().isOk());

        // Validate the WithIdStringDetails in the database
        List<WithIdStringDetails> withIdStringDetailsList = withIdStringDetailsRepository.findAll();
        assertThat(withIdStringDetailsList).hasSize(databaseSizeBeforeUpdate);
        WithIdStringDetails testWithIdStringDetails = withIdStringDetailsList.get(withIdStringDetailsList.size() - 1);
        assertThat(testWithIdStringDetails.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void updateNonExistingWithIdStringDetails() throws Exception {
        int databaseSizeBeforeUpdate = withIdStringDetailsRepository.findAll().size();

        // Create the WithIdStringDetails
        WithIdStringDetailsDTO withIdStringDetailsDTO = withIdStringDetailsMapper.toDto(withIdStringDetails);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWithIdStringDetailsMockMvc
            .perform(
                put("/api/with-id-string-details")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(withIdStringDetailsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the WithIdStringDetails in the database
        List<WithIdStringDetails> withIdStringDetailsList = withIdStringDetailsRepository.findAll();
        assertThat(withIdStringDetailsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWithIdStringDetailsWithPatch() throws Exception {
        // Initialize the database
        withIdStringDetailsRepository.saveAndFlush(withIdStringDetails);

        int databaseSizeBeforeUpdate = withIdStringDetailsRepository.findAll().size();

        // Update the withIdStringDetails using partial update
        WithIdStringDetails partialUpdatedWithIdStringDetails = new WithIdStringDetails();
        partialUpdatedWithIdStringDetails.setWithIdStringId(withIdStringDetails.getWithIdStringId());
        partialUpdatedWithIdStringDetails.setWithIdString(withIdStringDetails.getWithIdString());

        restWithIdStringDetailsMockMvc
            .perform(
                patch("/api/with-id-string-details")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWithIdStringDetails))
            )
            .andExpect(status().isOk());

        // Validate the WithIdStringDetails in the database
        List<WithIdStringDetails> withIdStringDetailsList = withIdStringDetailsRepository.findAll();
        assertThat(withIdStringDetailsList).hasSize(databaseSizeBeforeUpdate);
        WithIdStringDetails testWithIdStringDetails = withIdStringDetailsList.get(withIdStringDetailsList.size() - 1);
        assertThat(testWithIdStringDetails.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    void fullUpdateWithIdStringDetailsWithPatch() throws Exception {
        // Initialize the database
        withIdStringDetailsRepository.saveAndFlush(withIdStringDetails);

        int databaseSizeBeforeUpdate = withIdStringDetailsRepository.findAll().size();

        // Update the withIdStringDetails using partial update
        WithIdStringDetails partialUpdatedWithIdStringDetails = new WithIdStringDetails();
        partialUpdatedWithIdStringDetails.setWithIdStringId(withIdStringDetails.getWithIdStringId());
        partialUpdatedWithIdStringDetails.setWithIdString(withIdStringDetails.getWithIdString());

        partialUpdatedWithIdStringDetails.name(UPDATED_NAME);

        restWithIdStringDetailsMockMvc
            .perform(
                patch("/api/with-id-string-details")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWithIdStringDetails))
            )
            .andExpect(status().isOk());

        // Validate the WithIdStringDetails in the database
        List<WithIdStringDetails> withIdStringDetailsList = withIdStringDetailsRepository.findAll();
        assertThat(withIdStringDetailsList).hasSize(databaseSizeBeforeUpdate);
        WithIdStringDetails testWithIdStringDetails = withIdStringDetailsList.get(withIdStringDetailsList.size() - 1);
        assertThat(testWithIdStringDetails.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    void partialUpdateWithIdStringDetailsShouldThrown() throws Exception {
        // Update the withIdStringDetails without id should throw
        WithIdStringDetails partialUpdatedWithIdStringDetails = new WithIdStringDetails();

        restWithIdStringDetailsMockMvc
            .perform(
                patch("/api/with-id-string-details")
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedWithIdStringDetails))
            )
            .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    void deleteWithIdStringDetails() throws Exception {
        // Initialize the database
        withIdStringDetailsRepository.saveAndFlush(withIdStringDetails);

        int databaseSizeBeforeDelete = withIdStringDetailsRepository.findAll().size();

        // Delete the withIdStringDetails
        restWithIdStringDetailsMockMvc
            .perform(
                delete("/api/with-id-string-details/{withIdStringId}", withIdStringDetails.getWithIdStringId())
                    .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<WithIdStringDetails> withIdStringDetailsList = withIdStringDetailsRepository.findAll();
        assertThat(withIdStringDetailsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
