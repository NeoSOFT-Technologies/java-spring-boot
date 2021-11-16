package com.springboot.rest.usecase.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.util.Optional;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.springboot.rest.domain.dto.AdminUserDTO;
import com.springboot.rest.domain.dto.UserDTO;
import com.springboot.rest.domain.port.api.UserServicePort;
import com.springboot.rest.domain.port.spi.UserPersistencPort;
import com.springboot.rest.infrastructure.entity.User;
import com.springboot.rest.mapper.UserMapper;
import com.springboot.rest.security.SecurityUtils;


@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
class ReadUserTest {
	
	private static final String DEFAULT_LOGIN = "johndoe";
	
    private UserMapper userMapper;
    private User user;
    private AdminUserDTO userDto;
    
    @Autowired
    @MockBean
    private UserServicePort userServicePort;
    
    @MockBean
    private UserPersistencPort userPersistencePort;
    
//    @MockBean
//    private Pageable pageable;
    
    @InjectMocks
    private ReadUser readUser;

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
        readUser = new ReadUser(userServicePort);
    }
    
	@Test
	void contextLoads() {
		assertThat(userServicePort).isNotNull();
	}
	
    @Test
    void getAllManagedUsersTest() {
    	Page<AdminUserDTO> adminUserPage = Mockito.mock(Page.class);
    	Mockito.when(userPersistencePort.findAll(Mockito.any()))
    			.thenReturn(adminUserPage);
    }
    
    @Test
    void getAllByIdNotNullAndActivatedIsTrueTest() {
    	Page<UserDTO> userPage = Mockito.mock(Page.class);
    	Mockito.when(userServicePort.findAllByIdNotNullAndActivatedIsTrue(Mockito.any()))
    			.thenReturn(userPage);
    }
    
	@Test
	void getUserWithAuthoritiesByLoginTest() {
		Optional<User> opUser = Optional.ofNullable(user);
		
		Mockito.when(userServicePort.getUserWithAuthoritiesByLogin(DEFAULT_LOGIN))
				.thenReturn(opUser);
		Optional<User> opUserResult = readUser.getUserWithAuthoritiesByLogin(DEFAULT_LOGIN);
		
		// testing
		// System.out.println("opU: "+opUserResult.get());
		
		assertNotNull(opUserResult.get());
	}
	
	@Test
	void getUserWithAuthoritiesTest() {
		Optional<User> opUser = Optional.ofNullable(user);
		
		Mockito.when(userServicePort.getUserWithAuthorities())
				.thenReturn(opUser);
		
		Optional<User> opUserResult = readUser.getUserWithAuthorities();
		
		// testing
		// System.out.println("opU: "+opUserResult.get());
		
		assertNotNull(opUserResult.get());
	}
	
    @Test
    void getAllPublicUsersTest() {
    	Page<UserDTO> userPage = Mockito.mock(Page.class);
    	Mockito.when(userServicePort.getAllPublicUsers(Mockito.any()))
    			.thenReturn(userPage);
    }

}
