package com.springboot.rest.rest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

import com.springboot.rest.IntegrationTest;
import com.springboot.rest.domain.dto.CacheAsideDTO;
import com.springboot.rest.domain.port.spi.CacheAsidePersistencePort;
import com.springboot.rest.infrastructure.adaptor.CacheAsideJPAAdaptor;
import com.springboot.rest.infrastructure.entity.CacheAsideEntity;
import com.springboot.rest.infrastructure.repository.CacheAsideRepository;
import com.springboot.rest.mapper.CacheAsideMapper;

@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CacheAsideAdaptor {

	private static final String DEFAULT_NAME = "Manish";
    private static final String UPDATED_NAME = "manish";

    private static final String DEFAULT_PASSWORD = "Manish@123";
    private static final String UPDATED_PASSWORD = "Manish@1";

    private static final Integer DEFAULT_AGE = 10;
    private static final Integer UPDATED_AGE = 20;

    private static final Integer DEFAULT_PHONE = 10;
    private static final Integer UPDATED_PHONE = 20;

    private static final String ENTITY_API_URL = "/api/cacheAside-entity";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";
    
    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    
    @Mock
    private CacheAsideJPAAdaptor cacheAsidePAAdaptor;
    
    @Autowired
	private CacheAsidePersistencePort cacheAsidePersistencePort;
    
    @Autowired
    private CacheAsideRepository cacheAsideRepository;
    
    @Autowired
    private EntityManager em;
    
    @Autowired
    private MockMvc restAMockMvc;
    
    private  CacheAsideMapper cacheAsideMapper;
    
    private CacheAsideDTO cacheAsideDTO;
    
    private CacheAsideEntity cacheAsideEntity;
    
    public static CacheAsideEntity createEntity(EntityManager em) {
        CacheAsideEntity cacheAsideEntity = new CacheAsideEntity().name(DEFAULT_NAME).password(DEFAULT_PASSWORD).age(DEFAULT_AGE).phone(DEFAULT_PHONE);
        return cacheAsideEntity;
    }
    
    @BeforeEach
    public void initTest() {
    	cacheAsideEntity = createEntity(em);
    }
    
    @Test
    @Transactional
    void createCacheAsideEntity() throws Exception {
        int databaseSizeBeforeCreate = cacheAsideRepository.findAll().size();
        // Create the SampleEntity

        restAMockMvc
                .perform(MockMvcRequestBuilders.post(ENTITY_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(TestUtil.convertObjectToJsonBytes(cacheAsideEntity)))
                .andExpect(status().isCreated());

        // Validate the SampleEntity in the database
        List<CacheAsideEntity> sampleEntitiesList = cacheAsideRepository.findAll();
        assertThat(sampleEntitiesList.size()).isEqualTo(databaseSizeBeforeCreate + 1);

        CacheAsideEntity testSampleEntity = sampleEntitiesList.get(sampleEntitiesList.size() - 1);
        assertThat(testSampleEntity.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testSampleEntity.getPassword()).isEqualTo(DEFAULT_PASSWORD);
        assertThat(testSampleEntity.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testSampleEntity.getPhone()).isEqualTo(DEFAULT_PHONE);
    }
    
    @Test
    @Transactional
    void getCacheAsideEntity() throws Exception {
        // Initialize the database
    	cacheAsideRepository.saveAndFlush(cacheAsideEntity);

        // Get the sampleEntity
        restAMockMvc
            .perform(get(ENTITY_API_URL_ID, cacheAsideEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(cacheAsideEntity.getId().intValue()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(MockMvcResultMatchers.jsonPath("$.password").value(DEFAULT_PASSWORD))
            .andExpect(MockMvcResultMatchers.jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value(DEFAULT_PHONE));
    }
    
    @Test
    @Transactional
    void putCacheAsideEntity() throws Exception {
        // Initialize the database
    	cacheAsideRepository.saveAndFlush(cacheAsideEntity);

        int databaseSizeBeforeUpdate = cacheAsideRepository.findAll().size();

        
        CacheAsideEntity updatedCacheAsideEntity = cacheAsideRepository.findById(cacheAsideEntity.getId()).get();
        em.detach(updatedCacheAsideEntity);
        updatedCacheAsideEntity.name(UPDATED_NAME).password(UPDATED_PASSWORD).age(UPDATED_AGE).phone(UPDATED_PHONE);

        restAMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCacheAsideEntity.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCacheAsideEntity))
            )
            .andExpect(status().isOk());

        List<CacheAsideEntity> cacheAsideList = cacheAsideRepository.findAll();
        assertThat(cacheAsideList.size()).isEqualTo(databaseSizeBeforeUpdate);
        CacheAsideEntity testcacheAsideEntity = cacheAsideList.get(cacheAsideList.size() - 1);
        assertThat(testcacheAsideEntity.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testcacheAsideEntity.getPassword()).isEqualTo(UPDATED_PASSWORD);
        assertThat(testcacheAsideEntity.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testcacheAsideEntity.getPhone()).isEqualTo(UPDATED_PHONE);
    }
    
    @Test
    @Transactional
    void deleteSampleEntity() throws Exception {
        // Initialize the database
        cacheAsideRepository.saveAndFlush(cacheAsideEntity);

        int databaseSizeBeforeDelete = cacheAsideRepository.findAll().size();

        restAMockMvc.perform(delete(ENTITY_API_URL_ID, cacheAsideEntity.getId()).accept(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());
        List<CacheAsideEntity> sampleEntityList = cacheAsideRepository.findAll();
        assertThat(sampleEntityList.size()).isEqualTo(databaseSizeBeforeDelete - 1);
    }
    
}