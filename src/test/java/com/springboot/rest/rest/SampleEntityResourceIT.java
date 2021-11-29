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

import com.springboot.rest.domain.port.spi.ReadThroughEntityPersistencePort;
import com.springboot.rest.domain.service.ReadThroughEntityService;
import com.springboot.rest.infrastructure.entity.ReadThroughEntity;
import com.springboot.rest.infrastructure.repository.ReadThroughEntityRepository;

/**
 * Integration tests for the {@link ReadThroughEntityResource} REST controller.
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
    private ReadThroughEntityRepository readThroughEntityRepository;
    
    @Mock
    private ReadThroughEntityPersistencePort readThroughEntityPersistencePort;
    
    @Mock
    private ReadThroughEntityService readThroughEntityService;

   
    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAMockMvc;

    private ReadThroughEntity readThroughEntity;

    /**
     * Create an entity for this test.
     *
     * This is readThroughEntity static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReadThroughEntity createEntity(EntityManager em) {
        ReadThroughEntity readThroughEntity = new ReadThroughEntity().name(DEFAULT_NAME).password(DEFAULT_PASSWORD).age(DEFAULT_AGE).phone(DEFAULT_PHONE);
        return readThroughEntity;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is readThroughEntity static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ReadThroughEntity createUpdatedEntity(EntityManager em) {
        ReadThroughEntity readThroughEntity = new ReadThroughEntity().name(UPDATED_NAME).password(UPDATED_PASSWORD).age(UPDATED_AGE).phone(UPDATED_PHONE);
        return readThroughEntity;
    }

    @BeforeEach
    public void initTest() {
        readThroughEntity = createEntity(em);
    }

    @Test
    @Transactional
    void createSampleEntity() throws Exception {
        int databaseSizeBeforeCreate = readThroughEntityRepository.findAll().size();
        // Create the ReadThroughEntity

        restAMockMvc
                .perform(MockMvcRequestBuilders.post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(readThroughEntity)))
                .andExpect(status().isCreated());

        // Validate the ReadThroughEntity in the database
        List<ReadThroughEntity> sampleEntitiesList = readThroughEntityRepository.findAll();
        assertThat(sampleEntitiesList.size()).isEqualTo(databaseSizeBeforeCreate + 1);

        ReadThroughEntity testSampleEntity = sampleEntitiesList.get(sampleEntitiesList.size() - 1);
        assertThat(testSampleEntity.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSampleEntity.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testSampleEntity.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testSampleEntity.getPhone()).isEqualTo(DEFAULT_PHONE);
    }

    @Test
    @Transactional
    void createSampleEntityWithExistingId() throws Exception {
        // Create the ReadThroughEntity with an existing ID
        readThroughEntity.setId(1L);

        int databaseSizeBeforeCreate = readThroughEntityRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAMockMvc
                .perform(MockMvcRequestBuilders.post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(readThroughEntity)))
                .andExpect(status().isBadRequest());

        // Validate the ReadThroughEntity in the database
        List<ReadThroughEntity> sampleEntityListList = readThroughEntityRepository.findAll();
        assertThat(sampleEntityListList.size()).isEqualTo(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSampleEntities() throws Exception {
        // Initialize the database
        readThroughEntityRepository.saveAndFlush(readThroughEntity);

        // Get all the sampleEntityList
        restAMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].id").value(hasItem(readThroughEntity.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*]['id']").value(hasItem(readThroughEntity.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].name").value(hasItem(DEFAULT_NAME)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].password").value(hasItem(DEFAULT_PASSWORD)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].age").value(hasItem(DEFAULT_AGE)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[*].phone").value(hasItem(DEFAULT_PHONE)));
    }

    @Test
    @Transactional
    void getSampleEntity() throws Exception {
        // Initialize the database
        readThroughEntityRepository.saveAndFlush(readThroughEntity);
     
        // Get the readThroughEntity
        restAMockMvc
            .perform(get(ENTITY_API_URL_ID, readThroughEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(readThroughEntity.getId().intValue()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(MockMvcResultMatchers.jsonPath("$.password").value(DEFAULT_PASSWORD))
            .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value(DEFAULT_PHONE));
    }

    @Test
    @Transactional
    void getNonExistingSampleEntity() throws Exception {
        // Get the readThroughEntity
        restAMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    //
    @Test
    @Transactional
    void putNewSampleEntity() throws Exception {
        // Initialize the database
        readThroughEntityRepository.saveAndFlush(readThroughEntity);

        int databaseSizeBeforeUpdate = readThroughEntityRepository.findAll().size();

        // Update the readThroughEntity
        ReadThroughEntity updatedSampleEntity = readThroughEntityRepository.findById(readThroughEntity.getId()).get();
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

        // Validate the ReadThroughEntity in the database
        List<ReadThroughEntity> sampleEntityList = readThroughEntityRepository.findAll();
        assertThat(sampleEntityList.size()).isEqualTo(databaseSizeBeforeUpdate);
        ReadThroughEntity testSampleEntity = sampleEntityList.get(sampleEntityList.size() - 1);
        assertThat(testSampleEntity.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSampleEntity.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testSampleEntity.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testSampleEntity.getPhone()).isEqualTo(UPDATED_PHONE);
    }

    @Test
    @Transactional
    void putNonExistingSampleEntity() throws Exception {
        int databaseSizeBeforeUpdate = readThroughEntityRepository.findAll().size();
        readThroughEntity.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAMockMvc
            .perform(
                put(ENTITY_API_URL_ID, readThroughEntity.getId()).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(readThroughEntity))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReadThroughEntity in the database
        List<ReadThroughEntity> sampleEntityList = readThroughEntityRepository.findAll();
        assertThat(sampleEntityList.size()).isEqualTo(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSampleEntity() throws Exception {
        int databaseSizeBeforeUpdate = readThroughEntityRepository.findAll().size();
        readThroughEntity.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(readThroughEntity))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReadThroughEntity in the database
        List<ReadThroughEntity> sampleEntityList = readThroughEntityRepository.findAll();
        assertThat(sampleEntityList.size()).isEqualTo(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSampleEntity() throws Exception {
        int databaseSizeBeforeUpdate = readThroughEntityRepository.findAll().size();
        readThroughEntity.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(readThroughEntity)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ReadThroughEntity in the database
        List<ReadThroughEntity> sampleEntityList = readThroughEntityRepository.findAll();
        assertThat(sampleEntityList.size()).isEqualTo(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSampleEntityWithPatch() throws Exception {
        // Initialize the database
        readThroughEntityRepository.saveAndFlush(readThroughEntity);

        int databaseSizeBeforeUpdate = readThroughEntityRepository.findAll().size();

        // Update the readThroughEntity using partial update
        ReadThroughEntity partialUpdatedSampleEntity = new ReadThroughEntity();
        partialUpdatedSampleEntity.setId(readThroughEntity.getId());

        partialUpdatedSampleEntity.name(UPDATED_NAME);

        restAMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSampleEntity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSampleEntity))
            )
            .andExpect(status().isOk());

        // Validate the ReadThroughEntity in the database
        List<ReadThroughEntity> sampleEntityList = readThroughEntityRepository.findAll();
        assertThat(sampleEntityList.size()).isEqualTo(databaseSizeBeforeUpdate);
        ReadThroughEntity testSampleEntity = sampleEntityList.get(sampleEntityList.size() - 1);
        assertThat(testSampleEntity.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSampleEntity.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testSampleEntity.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testSampleEntity.getPhone()).isEqualTo(DEFAULT_PHONE);
    }

    @Test
    @Transactional
    void fullUpdateSampleEntityWithPatch() throws Exception {
        // Initialize the database
        readThroughEntityRepository.saveAndFlush(readThroughEntity);

        int databaseSizeBeforeUpdate = readThroughEntityRepository.findAll().size();

        // Update the readThroughEntity using partial update
        ReadThroughEntity partialUpdatedSampleEntity = new ReadThroughEntity();
        partialUpdatedSampleEntity.setId(readThroughEntity.getId());

        partialUpdatedSampleEntity.name(UPDATED_NAME).password(UPDATED_PASSWORD).age(UPDATED_AGE).phone(UPDATED_PHONE);

        restAMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSampleEntity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedSampleEntity))
            )
            .andExpect(status().isOk());

        // Validate the ReadThroughEntity in the database
        List<ReadThroughEntity> sampleEntityList = readThroughEntityRepository.findAll();
        assertThat(sampleEntityList.size()).isEqualTo(databaseSizeBeforeUpdate);
        ReadThroughEntity testSampleEntity = sampleEntityList.get(sampleEntityList.size() - 1);
        assertThat(testSampleEntity.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testSampleEntity.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testSampleEntity.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testSampleEntity.getPhone()).isEqualTo(UPDATED_PHONE);
    }

    @Test
    @Transactional
    void patchNonExistingSampleEntity() throws Exception {
        int databaseSizeBeforeUpdate = readThroughEntityRepository.findAll().size();
        readThroughEntity.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, readThroughEntity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(readThroughEntity))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReadThroughEntity in the database
        List<ReadThroughEntity> sampleEntityList = readThroughEntityRepository.findAll();
        assertThat(sampleEntityList.size()).isEqualTo(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSampleEntity() throws Exception {
        int databaseSizeBeforeUpdate = readThroughEntityRepository.findAll().size();
        readThroughEntity.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(readThroughEntity))
            )
            .andExpect(status().isBadRequest());

        // Validate the ReadThroughEntity in the database
        List<ReadThroughEntity> sampleEntityList = readThroughEntityRepository.findAll();
        assertThat(sampleEntityList.size()).isEqualTo(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSampleEntity() throws Exception {
        int databaseSizeBeforeUpdate = readThroughEntityRepository.findAll().size();
        readThroughEntity.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(readThroughEntity)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ReadThroughEntity in the database
        List<ReadThroughEntity> sampleEntityList = readThroughEntityRepository.findAll();
        assertThat(sampleEntityList.size()).isEqualTo(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSampleEntity() throws Exception {
        // Initialize the database
        readThroughEntityRepository.saveAndFlush(readThroughEntity);

        int databaseSizeBeforeDelete = readThroughEntityRepository.findAll().size();

        // Delete the readThroughEntity
        restAMockMvc.perform(delete(ENTITY_API_URL_ID, readThroughEntity.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ReadThroughEntity> sampleEntityList = readThroughEntityRepository.findAll();
        assertThat(sampleEntityList.size()).isEqualTo(databaseSizeBeforeDelete - 1);
    }
}
