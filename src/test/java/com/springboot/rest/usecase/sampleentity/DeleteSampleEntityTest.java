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
import com.springboot.rest.domain.dto.SampleEntity2DTO;
import com.springboot.rest.domain.port.api.SampleEntity2ServicePort;
import com.springboot.rest.domain.port.api.UserServicePort;
import com.springboot.rest.domain.port.spi.SampleEntity2PersistencePort;
import com.springboot.rest.domain.port.spi.UserPersistencPort;
import com.springboot.rest.domain.service.UserService;
import com.springboot.rest.infrastructure.entity.SampleEntity2;
import com.springboot.rest.infrastructure.entity.User;
import com.springboot.rest.mapper.SampleEntity2Mapper;
import com.springboot.rest.mapper.UserMapper;
import com.springboot.rest.security.AuthoritiesConstants;
import com.springboot.rest.usecase.sampleentity2.DeleteSampleEntity2;


@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class DeleteSampleEntityTest {
	
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
    private DeleteSampleEntity2 deleteSampleEntity2;

	@BeforeEach
    public void init() {
		sampleEntity2 = new SampleEntity2();
		sampleEntity2.setId(99l);
		sampleEntity2.setAge(20);
		sampleEntity2.setName("Test Sample");
		sampleEntity2.setPhone(2848);
		sampleEntity2.setPassword("Test@123");

        sampleEntityDto = new SampleEntity2DTO(sampleEntity2);
        deleteSampleEntity2 = new DeleteSampleEntity2(sampleEntity2ServicePort);
    }
    
	@Test
	void contextLoads() {
		assertThat(sampleEntity2ServicePort).isNotNull();
	}
	
    @Test
    void deleteSampleEntity() {
    	Mockito.doNothing().when(sampleEntity2ServicePort)
		.deleteById(sampleEntity2.getId());
    	deleteSampleEntity2.deleteById(sampleEntity2.getId());
    }
 
}
