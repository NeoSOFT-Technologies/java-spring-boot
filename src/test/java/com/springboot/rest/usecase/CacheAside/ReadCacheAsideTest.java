package com.springboot.rest.usecase.CacheAside;

import com.springboot.rest.domain.dto.CacheAsideDTO;
import com.springboot.rest.domain.port.api.CacheAsideServicePort;
import com.springboot.rest.domain.port.spi.CacheAsidePersistencePort;
import com.springboot.rest.infrastructure.entity.CacheAsideEntity;
import com.springboot.rest.mapper.CacheAsideMapper;
import com.springboot.rest.usecase.CacheAside.ReadCacheAside;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNull;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class ReadCacheAsideTest {
	
	private static final String DEFAULT_LOGIN = "johndoe";
	
    private CacheAsideMapper sampleEntityMapper;
    private CacheAsideEntity sampleEntity;
    private CacheAsideDTO sampleEntityDto;
    
    @Autowired
    @MockBean
    private CacheAsideServicePort sampleEntityServicePort;
    
    @MockBean
    private CacheAsidePersistencePort sampleEntityPersistencePort;
    
    @InjectMocks
    private ReadCacheAside readSampleEntity;

	@BeforeEach
    public void init() {
		sampleEntity = new CacheAsideEntity();
		sampleEntity.setId(99l);
		sampleEntity.setAge(20);
		sampleEntity.setName("Test Sample");
		sampleEntity.setPhone(2848);
		sampleEntity.setPassword("Test@123");

        sampleEntityDto = new CacheAsideDTO(sampleEntity);
        readSampleEntity = new ReadCacheAside(sampleEntityServicePort);
    }
    
	@Test
	void contextLoads() {
		assertThat(sampleEntityServicePort).isNotNull();
	}
	
    @Test
    void findSampleEntitiesTest() {
		List<CacheAsideEntity> entities = new ArrayList<CacheAsideEntity>();
		
		Mockito.when(sampleEntityServicePort.findAll().size() > 0)
				.thenReturn(null);
		
		entities = readSampleEntity.findAll();
		// testing
		// System.out.println("Auths: "+authorities);
		
		assertNull(entities);
    }
    
    @Test
    void findSampleEntityByIdTest() {
    	Mockito.when(sampleEntityPersistencePort
    			.findById(sampleEntityDto.getId())
    			.isPresent())
    			.thenReturn(null);    	
    	Optional<CacheAsideEntity> fetchedSampleEntity = readSampleEntity.findById(sampleEntity.getId());
    	
    	//assertNull(fetchedSampleEntity);
    }
 
}
