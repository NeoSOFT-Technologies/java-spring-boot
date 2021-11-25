package com.springboot.rest.usecase.sampleentity;

import com.springboot.rest.domain.dto.SampleEntity2DTO;
import com.springboot.rest.domain.port.api.SampleEntity2ServicePort;
import com.springboot.rest.domain.port.spi.SampleEntity2PersistencePort;
import com.springboot.rest.infrastructure.entity.SampleEntity2;
import com.springboot.rest.mapper.SampleEntity2Mapper;
import com.springboot.rest.usecase.sampleentity2.ReadSampleEntity2;

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
	
    private SampleEntity2Mapper sampleEntity2Mapper;
    private SampleEntity2 sampleEntity2;
    private SampleEntity2DTO sampleEntityDto;
    
    @Autowired
    @MockBean
    private SampleEntity2ServicePort sampleEntity2ServicePort;
    
    @MockBean
    private SampleEntity2PersistencePort sampleEntity2PersistencePort;
    
    @InjectMocks
    private ReadSampleEntity2 readSampleEntity2;

	@BeforeEach
    public void init() {
		sampleEntity2 = new SampleEntity2();
		sampleEntity2.setId(99l);
		sampleEntity2.setAge(20);
		sampleEntity2.setName("Test Sample");
		sampleEntity2.setPhone(2848);
		sampleEntity2.setPassword("Test@123");

        sampleEntityDto = new SampleEntity2DTO(sampleEntity2);
        readSampleEntity2 = new ReadSampleEntity2(sampleEntity2ServicePort);
    }
    
	@Test
	void contextLoads() {
		assertThat(sampleEntity2ServicePort).isNotNull();
	}
	
    @Test
    void findSampleEntitiesTest() {
		List<SampleEntity2> entities = new ArrayList<SampleEntity2>();
		
		Mockito.when(sampleEntity2ServicePort.findAll().size() > 0)
				.thenReturn(null);
		
		entities = readSampleEntity2.findAll();
		// testing
		// System.out.println("Auths: "+authorities);
		
		assertNull(entities);
    }
    
    @Test
    void findSampleEntityByIdTest() {
    	Mockito.when(sampleEntity2PersistencePort
    			.findById(sampleEntityDto.getId())
    			.isPresent())
    			.thenReturn(null);    	
    	Optional<SampleEntity2> fetchedSampleEntity = readSampleEntity2.findById(sampleEntity2.getId());
    	
    	//assertNull(fetchedSampleEntity);
    }
 
}
