package com.springboot.rest.usecase.sampleentity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.hamcrest.core.IsInstanceOf;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.springboot.rest.domain.dto.AdminUserDTO;
import com.springboot.rest.domain.dto.WriteThroughCacheEntityDTO;
import com.springboot.rest.domain.port.api.WriteThroughCacheEntityServicePort;
import com.springboot.rest.domain.port.api.UserServicePort;
import com.springboot.rest.domain.port.spi.WriteThroughCacheEntityPersistencePort;
import com.springboot.rest.domain.port.spi.UserPersistencPort;
import com.springboot.rest.domain.service.UserService;
import com.springboot.rest.infrastructure.entity.WriteThroughCacheEntity;
import com.springboot.rest.infrastructure.entity.User;
import com.springboot.rest.mapper.WriteThroughCacheEntityMapper;
import com.springboot.rest.mapper.UserMapper;
import com.springboot.rest.security.AuthoritiesConstants;

//@WebMvcTest
//@AutoConfigureMockMvc
//@WithMockUser(authorities = AuthoritiesConstants.ADMIN)
//@SpringBootTest

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class CreateSampleEntityTest {
	
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
    private CreateSampleEntity createSampleEntity;

	@BeforeEach
    public void init() {
		sampleEntity = new WriteThroughCacheEntity();
		sampleEntity.setId(99l);
		sampleEntity.setAge(20);
		sampleEntity.setName("Test Sample");
		sampleEntity.setPhone(2848);
		sampleEntity.setPassword("Test@123");

        sampleEntityDto = new WriteThroughCacheEntityDTO(sampleEntity);
        createSampleEntity = new CreateSampleEntity(sampleEntityServicePort);
    }
    
	@Test
	void contextLoads() {
		assertThat(sampleEntityServicePort).isNotNull();
	}
	
    @Test
    void saveSampleEntity() {
    	Mockito.when(sampleEntityPersistencePort
    			.findById(sampleEntityDto.getId())
    			.isPresent())
    			.thenReturn(null);    	
    	WriteThroughCacheEntity createdSampleEntity = createSampleEntity.save(sampleEntityDto);
    	
    	assertNull(createdSampleEntity);
    }
 
}
