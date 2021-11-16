package com.springboot.rest.usecase.sampleentity;

import com.springboot.rest.domain.dto.SampleEntityDTO;
import com.springboot.rest.domain.port.api.SampleEntityServicePort;
import com.springboot.rest.domain.port.spi.SampleEntityPersistencePort;
import com.springboot.rest.infrastructure.entity.SampleEntity;
import com.springboot.rest.mapper.SampleEntityMapper;
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
class ReadSampleEntityTest {
	
	private static final String DEFAULT_LOGIN = "johndoe";
	
    private SampleEntityMapper sampleEntityMapper;
    private SampleEntity sampleEntity;
    private SampleEntityDTO sampleEntityDto;
    
    @Autowired
    @MockBean
    private SampleEntityServicePort sampleEntityServicePort;
    
    @MockBean
    private SampleEntityPersistencePort sampleEntityPersistencePort;
    
    @InjectMocks
    private ReadSampleEntity readSampleEntity;

	@BeforeEach
    public void init() {
		sampleEntity = new SampleEntity();
		sampleEntity.setId(99l);
		sampleEntity.setAge(20);
		sampleEntity.setName("Test Sample");
		sampleEntity.setPhone(2848);
		sampleEntity.setPassword("Test@123");

        sampleEntityDto = new SampleEntityDTO(sampleEntity);
        readSampleEntity = new ReadSampleEntity(sampleEntityServicePort);
    }
    
	@Test
	void contextLoads() {
		assertThat(sampleEntityServicePort).isNotNull();
	}
	
    @Test
    void findSampleEntitiesTest() {
		List<SampleEntity> entities = new ArrayList<SampleEntity>();
		
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
    	Optional<SampleEntity> fetchedSampleEntity = readSampleEntity.findById(sampleEntity.getId());
    	
    	//assertNull(fetchedSampleEntity);
    }
 
}
