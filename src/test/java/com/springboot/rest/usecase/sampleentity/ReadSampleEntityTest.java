package com.springboot.rest.usecase.sampleentity;

import com.springboot.rest.domain.dto.ReadThroughEntityDTO;
import com.springboot.rest.domain.port.api.SampleEntity2ServicePort;
import com.springboot.rest.domain.port.spi.ReadThroughEntityPersistencePort;
import com.springboot.rest.infrastructure.entity.ReadThroughEntity;
import com.springboot.rest.mapper.ReadThroughEntityMapper;
import com.springboot.rest.usecase.readthroughEntity.ReadReadThroughEntity;

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
	
    private ReadThroughEntityMapper readThroughEntityMapper;
    private ReadThroughEntity readThroughEntity;
    private ReadThroughEntityDTO sampleEntityDto;
    
    @Autowired
    @MockBean
    private SampleEntity2ServicePort sampleEntity2ServicePort;
    
    @MockBean
    private ReadThroughEntityPersistencePort readThroughEntityPersistencePort;
    
    @InjectMocks
    private ReadReadThroughEntity readReadThroughEntity;

	@BeforeEach
    public void init() {
		readThroughEntity = new ReadThroughEntity();
		readThroughEntity.setId(99l);
		readThroughEntity.setAge(20);
		readThroughEntity.setName("Test Sample");
		readThroughEntity.setPhone(2848);
		readThroughEntity.setPassword("Test@123");

        sampleEntityDto = new ReadThroughEntityDTO(readThroughEntity);
        readReadThroughEntity = new ReadReadThroughEntity(sampleEntity2ServicePort);
    }
    
	@Test
	void contextLoads() {
		assertThat(sampleEntity2ServicePort).isNotNull();
	}
	
    @Test
    void findSampleEntitiesTest() {
		List<ReadThroughEntity> entities = new ArrayList<ReadThroughEntity>();
		
		Mockito.when(sampleEntity2ServicePort.findAll().size() > 0)
				.thenReturn(null);
		
		entities = readReadThroughEntity.findAll();
		// testing
		// System.out.println("Auths: "+authorities);
		
		assertNull(entities);
    }
    
    @Test
    void findSampleEntityByIdTest() {
    	Mockito.when(readThroughEntityPersistencePort
    			.findById(sampleEntityDto.getId())
    			.isPresent())
    			.thenReturn(null);    	
    	Optional<ReadThroughEntity> fetchedSampleEntity = readReadThroughEntity.findById(readThroughEntity.getId());
    	
    	//assertNull(fetchedSampleEntity);
    }
 
}
