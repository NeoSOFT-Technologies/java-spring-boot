package com.springboot.rest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.springboot.rest.domain.dto.SampleEntityDTO;
import com.springboot.rest.domain.port.spi.SampleEntityPersistencePort;
import com.springboot.rest.domain.service.SampleEntityService;
import com.springboot.rest.infrastructure.entity.SampleEntity;
import com.springboot.rest.infrastructure.repository.SampleEntityRepository;
import com.springboot.rest.mapper.SampleEntityMapper;


public class SpringBootRedisCacheApplicationTests {
	@Autowired
	private SampleEntityService SampleEntityService;
	
	@MockBean
	private SampleEntityRepository SampleEntityRepository;
	
	private SampleEntityPersistencePort   SampleEntityPersistencePort;
	
	@MockBean
    private  SampleEntityMapper sampleEntityMapper;
	
	@MockBean
    private SampleEntityDTO sampleEntityDTO;
	
	@Test
	//@Ignore
	public void testSavesampleEntity(){
		
		SampleEntity sampleEntity=sampleEntityMapper.dtoToEntity(sampleEntityDTO);
		

	//	SampleEntity sampleEntity = new SampleEntity();
		//sampleEntity.setId(1L);
		sampleEntity.setName("Martin Bingel");
		sampleEntity.setAge(2);
		sampleEntity.setPhone(20);

		sampleEntity.setPassword("martin");
		
	        Mockito.when(SampleEntityRepository.save(sampleEntity)).thenReturn(sampleEntity);
			    assertThat(SampleEntityRepository.save(sampleEntity)).isEqualTo(sampleEntity);
	
	}
	
	
	@Test
	public void testGetsampleEntityById(){
		
		
		SampleEntity sampleEntity = new SampleEntity();
		sampleEntity.setId(1L);
		sampleEntity.setName("Martin Bingel");
		sampleEntity.setAge(2);
		sampleEntity.setPhone(20);
		

		
	    Mockito.when(SampleEntityRepository.findById(2L)).thenReturn(sampleEntity);
	    assertThat(SampleEntityPersistencePort.findById(2L)).isEqualTo(sampleEntity);
	}
	
	@Test
	public void testGetAllsampleEntity(){

		SampleEntity sampleEntity1 = new SampleEntity();
		sampleEntity1.setId(1L);
		sampleEntity1.setName("Martin Bingel");
		sampleEntity1.setAge(2);
		sampleEntity1.setPhone(20);

		sampleEntity1.setPassword("martin");
		
		
		SampleEntity sampleEntity2 = new SampleEntity();
		sampleEntity2.setId(1L);
		sampleEntity2.setName("Martin Bingel");
		sampleEntity2.setAge(2);
		sampleEntity2.setPhone(20);
		
		List<SampleEntity> sampleEntityList = new ArrayList<>();
		sampleEntityList.add(sampleEntity1);
		sampleEntityList.add(sampleEntity2);
		
		Mockito.when(SampleEntityRepository.findAll()).thenReturn(sampleEntityList);
		
		assertThat(SampleEntityService.findAll()).isEqualTo(sampleEntityList);
	}
	
	
	@Test
	public void testDeletesampleEntity(){
		SampleEntity sampleEntity1 = new SampleEntity();
		sampleEntity1.setId(1L);
		sampleEntity1.setName("Martin Bingel");
		sampleEntity1.setAge(2);
		sampleEntity1.setPhone(20);

		sampleEntity1.setPassword("martin");
		
	    Mockito.when(SampleEntityRepository.findById(1L)).thenReturn(sampleEntity1);

	   assertFalse(SampleEntityService.deleteById(1L));
	}
	
	
	@Test
	public void testUpdatesampleEntity(){
		SampleEntity sampleEntity2 = new SampleEntity();
		sampleEntity2.setId(1L);
		sampleEntity2.setName("Martin Bingel");
		sampleEntity2.setAge(2);
		sampleEntity2.setPhone(20);
		
		
		Mockito.when(SampleEntityRepository.findById(1L)).thenReturn(sampleEntity2);

		Mockito.when(SampleEntityRepository.save(sampleEntity2)).thenReturn(sampleEntity2);
		
		assertThat(SampleEntityService.update(1L, sampleEntityDTO)).isEqualTo(sampleEntity2);
		
	}
	
	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
//  private static final String USERNAME = "johndoe";
// 
//  private static final String PAASSWOD = "John";
//  private static final Integer AGE = 2;
//  private static final Integer PHONE = 2;
//
//  @Autowired
//  TestRestTemplate restTemplate;
//
////  @SpyBean
////  ExpensiveUserRepository remoteClientService;
//
//  @Autowired
//  SampleEntityRepository userRepository;
//
//
//
//  private SampleEntity  sampleEntity;
//
//  @Before
//  public void init() {
//	  sampleEntity = userRepository.save(new SampleEntity());
//    //clearAllCaches();
//  }
//
//  @Test
//  public void cacheTest() {
//
//    // Should check repo and insert on cache
//	  SampleEntity remoteUser = getUser(sampleEntity.getName());
//	  assertThat(remoteUser.getName()).isEqualTo(USERNAME);
//    assertThat(remoteUser.getPassword()).isEqualTo(PAASSWOD);
//    assertThat(remoteUser.getAge()).isEqualTo(AGE);
//    assertThat(remoteUser.getPhone()).isEqualTo(PHONE);
//   // verify(remoteClientService, atMost(1)).getUser(anyString());
//
//    // Should get from cache
//    remoteUser = getUser(remoteUser.getName());
//    assertThat(remoteUser.getName()).isEqualTo(USERNAME);
//    assertThat(remoteUser.getPassword()).isEqualTo(PAASSWOD);
//    assertThat(remoteUser.getAge()).isEqualTo(AGE);
//    assertThat(remoteUser.getPhone()).isEqualTo(PHONE);
//  //  verify(remoteClientService, atMost(1)).getUser(anyString());
//
//    // Should update in cache
//    remoteUser.setName("Doe Doe");
//    updateUser(remoteUser);
//
//    // Should get from cache with updated values
//    remoteUser = getUser(remoteUser.getName());
//    assertThat(remoteUser.getName()).isEqualTo(USERNAME);
//    assertThat(remoteUser.getPassword()).isEqualTo(PAASSWOD);
//    assertThat(remoteUser.getName()).isEqualTo("Doe Doe");
//  //  verify(remoteClientService, atMost(1)).getUser(anyString());
//  }
//
//  private SampleEntity getUser(String username) {
//    return restTemplate.getForEntity("/users/{username}", SampleEntity.class, username).getBody();
//  }
//
//  private void updateUser(SampleEntity user) {
//    restTemplate.put("/users", user);
//  }
//
////  private void clearAllCaches() {
////    Optional.of(cm.getCache("single-user")).ifPresent(Cache::clear);
////  }

}