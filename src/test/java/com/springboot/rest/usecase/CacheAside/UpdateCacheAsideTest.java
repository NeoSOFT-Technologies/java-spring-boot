package com.springboot.rest.usecase.CacheAside;

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
import com.springboot.rest.domain.dto.CacheAsideDTO;
import com.springboot.rest.domain.port.api.CacheAsideServicePort;
import com.springboot.rest.domain.port.api.UserServicePort;
import com.springboot.rest.domain.port.spi.CacheAsidePersistencePort;
import com.springboot.rest.domain.port.spi.UserPersistencPort;
import com.springboot.rest.domain.service.UserService;
import com.springboot.rest.infrastructure.entity.CacheAsideEntity;
import com.springboot.rest.infrastructure.entity.User;
import com.springboot.rest.mapper.CacheAsideMapper;
import com.springboot.rest.mapper.UserMapper;
import com.springboot.rest.security.AuthoritiesConstants;
import com.springboot.rest.usecase.CacheAside.UpdateCacheAside;


@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class UpdateCacheAsideTest {
	
	private static final String DEFAULT_LOGIN = "johndoe";
	private static final Long DEFAULT_ID = 999l;
	
    private CacheAsideMapper sampleEntityMapper;
    private CacheAsideEntity sampleEntity;
    private CacheAsideDTO sampleEntityDto;
    
    @Autowired
    @MockBean
    private CacheAsideServicePort sampleEntityServicePort;
    
    @MockBean
    private CacheAsidePersistencePort sampleEntityPersistencePort;
    
    @InjectMocks
    private UpdateCacheAside updateSampleEntity;

	@BeforeEach
    public void init() {
		sampleEntity = new CacheAsideEntity();
		sampleEntity.setId(99l);
		sampleEntity.setAge(20);
		sampleEntity.setName("Test Sample");
		sampleEntity.setPhone(2848);
		sampleEntity.setPassword("Test@123");

        sampleEntityDto = new CacheAsideDTO(sampleEntity);
        updateSampleEntity = new UpdateCacheAside(sampleEntityServicePort);
    }
    
	@Test
	void contextLoads() {
		assertThat(sampleEntityServicePort).isNotNull();
	}
	
    @Test
    void updateSampleEntityTest() {
    	Mockito.when(sampleEntityServicePort
    			.update(DEFAULT_ID, sampleEntityDto))
    			.thenReturn(null);    	
    	CacheAsideEntity updatedSampleEntity = updateSampleEntity.update(DEFAULT_ID, sampleEntityDto);
    	
    	assertNull(updatedSampleEntity);
    }
    
    @Test
    void patchSampleEntityTest() {
    	Mockito.when(sampleEntityServicePort
    			.patch(DEFAULT_ID, sampleEntityDto))
    			.thenReturn(null);    	
    	Optional<CacheAsideEntity> patchedSampleEntity = updateSampleEntity.patch(DEFAULT_ID, sampleEntityDto);
    	
    	assertNull(patchedSampleEntity);
    }
 
}
