package com.springboot.rest.rest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.rest.domain.port.spi.SampleEntity2PersistencePort;
import com.springboot.rest.domain.service.SampleEntity2Service;
import com.springboot.rest.infrastructure.entity.SampleEntity2;
import com.springboot.rest.infrastructure.repository.SampleEntity2Repository;

/**
 * Integration tests for the {@link SampleEntity2Resource} REST controller.
 */
//@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SampleEntityResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    private static final Integer DEFAULT_AGE = 1;
    private static final Integer UPDATED_AGE = 2;

    private static final Integer DEFAULT_PHONE = 1;
    private static final Integer UPDATED_PHONE = 2;

    private static final String ENTITY_API_URL = "/api/sample-entity";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private SampleEntity2Repository sampleEntity2Repository;
    
    @Mock
    private SampleEntity2PersistencePort sampleEntity2PersistencePort;
    
    @Mock
    private SampleEntity2Service sampleEntity2Service;

   
    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAMockMvc;

    private SampleEntity2 sampleEntity2;

    /**
     * Create an entity for this test.
     *
     * This is sampleEntity2 static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SampleEntity2 createEntity(EntityManager em) {
        SampleEntity2 sampleEntity2 = new SampleEntity2().name(DEFAULT_NAME).password(DEFAULT_PASSWORD).age(DEFAULT_AGE).phone(DEFAULT_PHONE);
        return sampleEntity2;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is sampleEntity2 static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SampleEntity2 createUpdatedEntity(EntityManager em) {
        SampleEntity2 sampleEntity2 = new SampleEntity2().name(UPDATED_NAME).password(UPDATED_PASSWORD).age(UPDATED_AGE).phone(UPDATED_PHONE);
        return sampleEntity2;
    }

    @BeforeEach
    public void initTest() {
        sampleEntity2 = createEntity(em);
    }

    @Test
    @Transactional
    void createSampleEntity() throws Exception {
        int databaseSizeBeforeCreate = sampleEntity2Repository.findAll().size();
        // Create the SampleEntity2

        restAMockMvc
                .perform(MockMvcRequestBuilders.post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(sampleEntity2)))
                .andExpect(status().isCreated());

        // Validate the SampleEntity2 in the database
        List<SampleEntity2> sampleEntitiesList = sampleEntity2Repository.findAll();
        assertThat(sampleEntitiesList.size()).isEqualTo(databaseSizeBeforeCreate + 1);

        SampleEntity2 testSampleEntity = sampleEntitiesList.get(sampleEntitiesList.size() - 1);
        assertThat(testSampleEntity.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSampleEntity.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testSampleEntity.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testSampleEntity.getPhone()).isEqualTo(DEFAULT_PHONE);
    }

    @Test
    @Transactional
    void createSampleEntityWithExistingId() throws Exception {
        // Create the SampleEntity2 with an existing ID
        sampleEntity2.setId(1L);

        int databaseSizeBeforeCreate = sampleEntity2Repository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAMockMvc
                .perform(MockMvcRequestBuilders.post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(sampleEntity2)))
                .andExpect(status().isBadRequest());

        // Validate the SampleEntity2 in the database
        List<SampleEntity2> sampleEntityListList = sampleEntity2Repository.findAll();
        assertThat(sampleEntityListList.size()).isEqualTo(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSampleEntities() throws Exception {
        // Initialize the database
        sampleEntity2Repository.saveAndFlush(sampleEntity2);

        // Get all the sampleEntityList
        restAMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].id").value(hasItem(sampleEntity2.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*]['id']").value(hasItem(sampleEntity2.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].name").value(hasItem(DEFAULT_NAME)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].password").value(hasItem(DEFAULT_PASSWORD)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].age").value(hasItem(DEFAULT_AGE)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].phone").value(hasItem(DEFAULT_PHONE)));
    }

    @Test
    @Transactional
    void getSampleEntity() throws Exception {
        // Initialize the database
        sampleEntity2Repository.saveAndFlush(sampleEntity2);
     
        // Get the sampleEntity2
        restAMockMvc
            .perform(get(ENTITY_API_URL_ID, sampleEntity2.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(sampleEntity2.getId().intValue()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(MockMvcResultMatchers.jsonPath("$.password").value(DEFAULT_PASSWORD))
            .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value(DEFAULT_PHONE));
    }

    @Test
    @Transactional
    void getNonExistingSampleEntity() throws Exception {
        // Get the sampleEntity2
        restAMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    //
    @Test
    @Transactional
    void putNewSampleEntity() throws Exception {
        // Initialize the database
        sampleEntity2Repository.saveAndFlush(sampleEntity2);

        int databaseSizeBeforeUpdate = sampleEntity2Repository.findAll().size();

        // Update the sampleEntity2
        SampleEntity2 updatedSampleEntity = sampleEntity2Repository.findById(sampleEntity2.getId()).get();
        // Disconnect from session so that the updates on updatedA are not directly saved in db
        em.detach(updatedSampleEntity);
        updatedSampleEntity.name(UPDATED_NAME).password(UPDATED_PASSWORD).age(UPDATED_AGE).phone(UPDATED_PHONE);

        restAMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSampleEntity.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedSampleEntity))
            )
            .andExpect(status().isOk());

        // Validate the SampleEntity2 in the database
        List<SampleEntity2> sampleEntityList = sampleEntity2Repository.findAll();
        assertThat(sampleEntityList.size()).isEqualTo(databaseSizeBeforeUpdate);
        SampleEntity2 testSampleEntity = sampleEntityList.get(sampleEntityList.size() - 1);
        assertThat(testSampleEntity.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSampleEntity.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testSampleEntity.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testSampleEntity.getPhone()).isEqualTo(UPDATED_PHONE);
    }

    @Test
    @Transactional
    void putNonExistingSampleEntity() throws Exception {
        int databaseSizeBeforeUpdate = sampleEntity2Repository.findAll().size();
        sampleEntity2.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sampleEntity2.getId()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sampleEntity2))
            )
            .andExpect(status().isBadRequest());

        // Validate the SampleEntity2 in the database
        List<SampleEntity2> sampleEntityList = sampleEntity2Repository.findAll();
        assertThat(sampleEntityList.size()).isEqualTo(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSampleEntity() throws Exception {
        int databaseSizeBeforeUpdate = sampleEntity2Repository.findAll().size();
        sampleEntity2.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(sampleEntity2))
            )
            .andExpect(status().isBadRequest());

        // Validate the SampleEntity2 in the database
        List<SampleEntity2> sampleEntityList = sampleEntity2Repository.findAll();
        assertThat(sampleEntityList.size()).isEqualTo(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSampleEntity() throws Exception {
        int databaseSizeBeforeUpdate = sampleEntity2Repository.findAll().size();
        sampleEntity2.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(sampleEntity2)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SampleEntity2 in the database
        List<SampleEntity2> sampleEntityList = sampleEntity2Repository.findAll();
        assertThat(sampleEntityList.size()).isEqualTo(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSampleEntityWithPatch() throws Exception {
        // Initialize the database
        sampleEntity2Repository.saveAndFlush(sampleEntity2);

        int databaseSizeBeforeUpdate = sampleEntity2Repository.findAll().size();

        // Update the sampleEntity2 using partial update
        SampleEntity2 partialUpdatedSampleEntity = new SampleEntity2();
        partialUpdatedSampleEntity.setId(sampleEntity2.getId());

        partialUpdatedSampleEntity.name(UPDATED_NAME);

        restAMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSampleEntity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSampleEntity))
            )
            .andExpect(status().isOk());

        // Validate the SampleEntity2 in the database
        List<SampleEntity2> sampleEntityList = sampleEntity2Repository.findAll();
        assertThat(sampleEntityList.size()).isEqualTo(databaseSizeBeforeUpdate);
        SampleEntity2 testSampleEntity = sampleEntityList.get(sampleEntityList.size() - 1);
        assertThat(testSampleEntity.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSampleEntity.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testSampleEntity.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testSampleEntity.getPhone()).isEqualTo(DEFAULT_PHONE);
    }

    @Test
    @Transactional
    void fullUpdateSampleEntityWithPatch() throws Exception {
        // Initialize the database
        sampleEntity2Repository.saveAndFlush(sampleEntity2);

        int databaseSizeBeforeUpdate = sampleEntity2Repository.findAll().size();

        // Update the sampleEntity2 using partial update
        SampleEntity2 partialUpdatedSampleEntity = new SampleEntity2();
        partialUpdatedSampleEntity.setId(sampleEntity2.getId());

        partialUpdatedSampleEntity.name(UPDATED_NAME).password(UPDATED_PASSWORD).age(UPDATED_AGE).phone(UPDATED_PHONE);

        restAMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSampleEntity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSampleEntity))
            )
            .andExpect(status().isOk());

        // Validate the SampleEntity2 in the database
        List<SampleEntity2> sampleEntityList = sampleEntity2Repository.findAll();
        assertThat(sampleEntityList.size()).isEqualTo(databaseSizeBeforeUpdate);
        SampleEntity2 testSampleEntity = sampleEntityList.get(sampleEntityList.size() - 1);
        assertThat(testSampleEntity.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSampleEntity.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testSampleEntity.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testSampleEntity.getPhone()).isEqualTo(UPDATED_PHONE);
    }

    @Test
    @Transactional
    void patchNonExistingSampleEntity() throws Exception {
        int databaseSizeBeforeUpdate = sampleEntity2Repository.findAll().size();
        sampleEntity2.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sampleEntity2.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sampleEntity2))
            )
            .andExpect(status().isBadRequest());

        // Validate the SampleEntity2 in the database
        List<SampleEntity2> sampleEntityList = sampleEntity2Repository.findAll();
        assertThat(sampleEntityList.size()).isEqualTo(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSampleEntity() throws Exception {
        int databaseSizeBeforeUpdate = sampleEntity2Repository.findAll().size();
        sampleEntity2.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(sampleEntity2))
            )
            .andExpect(status().isBadRequest());

        // Validate the SampleEntity2 in the database
        List<SampleEntity2> sampleEntityList = sampleEntity2Repository.findAll();
        assertThat(sampleEntityList.size()).isEqualTo(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSampleEntity() throws Exception {
        int databaseSizeBeforeUpdate = sampleEntity2Repository.findAll().size();
        sampleEntity2.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(sampleEntity2)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SampleEntity2 in the database
        List<SampleEntity2> sampleEntityList = sampleEntity2Repository.findAll();
        assertThat(sampleEntityList.size()).isEqualTo(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSampleEntity() throws Exception {
        // Initialize the database
        sampleEntity2Repository.saveAndFlush(sampleEntity2);

        int databaseSizeBeforeDelete = sampleEntity2Repository.findAll().size();

        // Delete the sampleEntity2
        restAMockMvc.perform(delete(ENTITY_API_URL_ID, sampleEntity2.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SampleEntity2> sampleEntityList = sampleEntity2Repository.findAll();
        assertThat(sampleEntityList.size()).isEqualTo(databaseSizeBeforeDelete - 1);
    }
}
