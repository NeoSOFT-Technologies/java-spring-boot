package com.springboot.rest.usecase.sampleentity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.springboot.rest.domain.dto.WriteThroughCacheEntityDTO;
import com.springboot.rest.domain.port.api.WriteThroughCacheEntityServicePort;
import com.springboot.rest.domain.port.spi.WriteThroughCacheEntityPersistencePort;
import com.springboot.rest.infrastructure.entity.WriteThroughCacheEntity;
import com.springboot.rest.mapper.WriteThroughCacheEntityMapper;


@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class DeleteSampleEntityTest {
	
	private static final String DEFAULT_LOGIN = "johndoe";
	
    private WriteThroughCacheEntityMapper sampleEntityMapper;
    private WriteThroughCacheEntity sampleEntity;
    private WriteThroughCacheEntityDTO sampleEntityDto;
    
    @Autowired
    @MockBean
    private WriteThroughCacheEntityServicePort sampleEntityServicePort;
    
    @MockBean
    private WriteThroughCacheEntityPersistencePort sampleEntityPersistencePort;
    
    @InjectMocks
    private DeleteSampleEntity deleteSampleEntity;

	@BeforeEach
    public void init() {
		sampleEntity = new WriteThroughCacheEntity();
		sampleEntity.setId(99l);
		sampleEntity.setAge(20);
		sampleEntity.setName("Test Sample");
		sampleEntity.setPhone(2848);
		sampleEntity.setPassword("Test@123");

        sampleEntityDto = new WriteThroughCacheEntityDTO(sampleEntity);
        deleteSampleEntity = new DeleteSampleEntity(sampleEntityServicePort);
    }
    
	@Test
	void contextLoads() {
		assertThat(sampleEntityServicePort).isNotNull();
	}
	
    @Test
    Boolean deleteSampleEntity() {
    	Mockito.doNothing().when(sampleEntityServicePort)
		.deleteById(sampleEntity.getId());
    	deleteSampleEntity.deleteById(sampleEntity.getId());
    	return true;
    }
 
}
