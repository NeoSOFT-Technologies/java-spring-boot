package com.springboot.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import com.springboot.rest.domain.dto.CacheAsideDTO;
import com.springboot.rest.domain.port.spi.CacheAsidePersistencePort;
import com.springboot.rest.infrastructure.adaptor.CacheAsideJPAAdaptor;
import com.springboot.rest.infrastructure.entity.CacheAsideEntity;
import com.springboot.rest.infrastructure.repository.CacheAsideRepository;
import com.springboot.rest.mapper.CacheAsideMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CacheAsideJPAAdaptorTest3 {
	
	@InjectMocks
	private CacheAsideJPAAdaptor sampleEntityJPAAdaptor3;
	
	@Autowired
	private CacheAsidePersistencePort sampleEntityPersistencePort3;
	
	@MockBean
	private CacheAsideRepository sampleEntityRepository3;
	
    private  CacheAsideMapper sampleEntityMapper3;
    private CacheAsideDTO sampleEntityDTO3;
    private CacheAsideEntity sampleEntity3;
    
    @BeforeEach
    public void init() {
        sampleEntityJPAAdaptor3 = new CacheAsideJPAAdaptor(sampleEntityRepository3, sampleEntityMapper3);
        sampleEntity3 = new CacheAsideEntity();
        sampleEntity3.setName("Mahesh Nehe");;
        sampleEntity3.setPassword(RandomStringUtils.random(60));
        sampleEntity3.setAge(20);;
        sampleEntity3.setPhone(10);

        sampleEntityDTO3 = new CacheAsideDTO(sampleEntity3);
    }

	
	@Test
	//@Ignore
	public void testSavesampleEntity(){
		
		CacheAsideEntity sampleEntity = sampleEntityMapper3.dtoToEntity(sampleEntityDTO3);
		

		CacheAsideEntity sampleEntity1 = new CacheAsideEntity();
		sampleEntity1.setId(1L);
		sampleEntity1.setName("Mahesh Nehe");
		sampleEntity1.setAge(2);
		sampleEntity1.setPhone(20);

		sampleEntity1.setPassword("mahesh");
		
	    Mockito.when(sampleEntityRepository3.save(sampleEntity1)).thenReturn(sampleEntity1);
	    
	    assertThat(sampleEntityJPAAdaptor3.save(sampleEntityDTO3)).isEqualTo(sampleEntity1);
	
	}
	
	
	@Test
	public void testGetsampleEntityById(){
		
		
		CacheAsideEntity sampleEntity = new CacheAsideEntity();
		sampleEntity.setId(1L);
		sampleEntity.setName("Mahesh Nehe");
		sampleEntity.setAge(2);
		sampleEntity.setPhone(20);
		
//		Mockito.when(sampleEntityRepository3.findById(1L)).thenReturn(sampleEntity);
//	    assertThat(sampleEntityJPAAdaptor3.findById(1L)).isEqualTo(sampleEntity);
	}
	
	@Test
	public void testGetAllsampleEntity(){

		CacheAsideEntity sampleEntity1 = new CacheAsideEntity();
		sampleEntity1.setId(1L);
		sampleEntity1.setName("Mahesh Nehe");
		sampleEntity1.setAge(2);
		sampleEntity1.setPhone(20);

		sampleEntity1.setPassword("mahesh");
		
		
		CacheAsideEntity sampleEntity2 = new CacheAsideEntity();
		sampleEntity2.setId(1L);
		sampleEntity2.setName("Mahesh Nehe");
		sampleEntity2.setAge(2);
		sampleEntity2.setPhone(20);
		
		List<CacheAsideEntity> sampleEntityList = new ArrayList();
		sampleEntityList.add(sampleEntity1);
		sampleEntityList.add(sampleEntity2);
		
		Mockito.when(sampleEntityRepository3.findAll()).thenReturn(sampleEntityList);
		
		assertThat(sampleEntityJPAAdaptor3.findAll()).isEqualTo(sampleEntityList);
	}
	
	
	@Test
	public Boolean testDeletesampleEntity(){
		CacheAsideEntity sampleEntity1 = new CacheAsideEntity();
		sampleEntity1.setId(1L);
		sampleEntity1.setName("Mahesh Nehe");
		sampleEntity1.setAge(2);
		sampleEntity1.setPhone(20);

		sampleEntity1.setPassword("mahesh");
		
	    Mockito.when(sampleEntityRepository3.findById(1L)).thenReturn(null);
	   assertFalse(sampleEntityJPAAdaptor3.deleteById(1L));
	   return true;
	}
	
	
}
