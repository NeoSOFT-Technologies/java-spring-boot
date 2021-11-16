package com.springboot.rest.usecase.user;

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
import com.springboot.rest.domain.port.api.UserServicePort;
import com.springboot.rest.domain.port.spi.UserPersistencPort;
import com.springboot.rest.domain.service.UserService;
import com.springboot.rest.infrastructure.entity.User;
import com.springboot.rest.mapper.UserMapper;
import com.springboot.rest.security.AuthoritiesConstants;

//@WebMvcTest
//@AutoConfigureMockMvc
//@WithMockUser(authorities = AuthoritiesConstants.ADMIN)
//@SpringBootTest

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class CreateUserTest {
	
	private static final String DEFAULT_LOGIN = "johndoe";
	
    private UserMapper userMapper;
    private User user;
    private AdminUserDTO userDto;
    
    @Autowired
    @MockBean
    private UserServicePort userServicePort;
    
    @MockBean
    private UserPersistencPort userPersistencePort;
    
    @InjectMocks
    private CreateUser createUser;

	@BeforeEach
    public void init() {
        userMapper = new UserMapper();
        user = new User();
        user.setLogin(DEFAULT_LOGIN);
        user.setPassword(RandomStringUtils.random(60));
        user.setActivated(true);
        user.setEmail("johndoe@localhost");
        user.setFirstName("john");
        user.setLastName("doe");
        user.setImageUrl("image_url");
        user.setLangKey("en");

        userDto = new AdminUserDTO(user);
        createUser = new CreateUser(userServicePort);
    }
    
	@Test
	void contextLoads() {
		assertThat(userServicePort).isNotNull();
	}
	
    @Test
    void saveAdminUserDTOasUserAndTestUniqueLoginAndEmail() {
    	Mockito.when(userPersistencePort.findOneByLogin(userDto.getLogin().toLowerCase()).isPresent())
    			.thenReturn(null);
    	Mockito.when(userPersistencePort.findOneByEmailIgnoreCase(userDto.getEmail()).isPresent())
    			.thenReturn(null);
    	
    	User createdUser = createUser.createUser(userDto);
    	
    	// testing
//    	System.out.println("created: "+createdUser);
    	
    	assertNull(createdUser);
    }
    
    @Test
    void saveUserAccountWithGivenLoginID() {
    	
//    	Mockito.when(userPersistencePort.findOneByEmailIgnoreCase(userDto.getEmail()).isPresent())
//		.thenReturn(null);
    	Mockito.when(!userPersistencePort.findOneByLogin(DEFAULT_LOGIN.toLowerCase()).isPresent())
		.thenReturn(null);
    	
    	createUser.saveAccount(userDto, DEFAULT_LOGIN);
    	
    	Optional<User> existingUser = userPersistencePort.findOneByLogin(DEFAULT_LOGIN);
    	
    	// testing
//    	System.out.println("created: "+existingUser);
    	
    	assertNull(existingUser);
    }
    
}
