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
import com.springboot.rest.domain.dto.ReadThroughEntityDTO;
import com.springboot.rest.domain.port.api.SampleEntity2ServicePort;
import com.springboot.rest.domain.port.api.UserServicePort;
import com.springboot.rest.domain.port.spi.ReadThroughEntityPersistencePort;
import com.springboot.rest.domain.port.spi.UserPersistencPort;
import com.springboot.rest.domain.service.UserService;
import com.springboot.rest.infrastructure.entity.ReadThroughEntity;
import com.springboot.rest.infrastructure.entity.User;
import com.springboot.rest.mapper.ReadThroughEntityMapper;
import com.springboot.rest.mapper.UserMapper;
import com.springboot.rest.security.AuthoritiesConstants;
import com.springboot.rest.usecase.readthroughEntity.CreateReadThroughEntity;

//@WebMvcTest
//@AutoConfigureMockMvc
//@WithMockUser(authorities = AuthoritiesConstants.ADMIN)
//@SpringBootTest

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class CreateSampleEntityTest {
	
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
    private CreateReadThroughEntity createReadThroughEntity;

	@BeforeEach
    public void init() {
		readThroughEntity = new ReadThroughEntity();
		readThroughEntity.setId(99l);
		readThroughEntity.setAge(20);
		readThroughEntity.setName("Test Sample");
		readThroughEntity.setPhone(2848);
		readThroughEntity.setPassword("Test@123");

        sampleEntityDto = new ReadThroughEntityDTO(readThroughEntity);
        createReadThroughEntity = new CreateReadThroughEntity(sampleEntity2ServicePort);
    }
    
	@Test
	void contextLoads() {
		assertThat(sampleEntity2ServicePort).isNotNull();
	}
	
    @Test
    void saveSampleEntity() {
    	Mockito.when(readThroughEntityPersistencePort
    			.findById(sampleEntityDto.getId())
    			.isPresent())
    			.thenReturn(null);    	
    	ReadThroughEntity createdSampleEntity = createReadThroughEntity.save(sampleEntityDto);
    	
    	assertNull(createdSampleEntity);
    }
 
}
