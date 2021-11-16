package com.springboot.rest.usecase.mail;

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
import com.springboot.rest.domain.port.api.MailServicePort;
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
class SendMailTest {
	
	private static final String DEFAULT_LOGIN = "johndoe";
	
    private UserMapper userMapper;
    private User user;
//    private Mail mail;
    private AdminUserDTO userDto;
    
    @Autowired
    @MockBean
    private UserServicePort userServicePort;
    
    @Autowired
    @MockBean
    private MailServicePort mailServicePort;
    
    @MockBean
    private UserPersistencPort userPersistencePort;
    
    @InjectMocks
    private SendMail sendMail;

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
        sendMail = new SendMail(mailServicePort);
    }
    
	@Test
	void contextLoads() {
		assertThat(mailServicePort).isNotNull();
	}
	
  @Test
  void sendActivationMailTest() {
  	Mockito.doNothing().when(mailServicePort)
  			.sendActivationEmail(user);
  	sendMail.sendActivationEmail(user);
  }
  
  @Test
  void sendCreationMailTest() {
  	Mockito.doNothing().when(mailServicePort)
  			.sendCreationEmail(user);
  	sendMail.sendCreationEmail(user);
  }
  
  @Test
  void sendPasswordResetMailTest() {
  	Mockito.doNothing().when(mailServicePort)
  			.sendPasswordResetMail(user);
  	sendMail.sendPasswordResetMail(user);
  }
	
//    @Test
//    void sendMailTest() {
//    	Mockito.doNothing().when(mailServicePort)
//    			.sendMail();
//    	sendEmail.sendEmail(mail.getTo(), mail.getSubject(), mail.getContent(), 
//    			mail.isMultipart(), mail.isHtml());
//    }
    
}
