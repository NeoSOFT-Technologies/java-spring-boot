package com.springboot.rest.usecase.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.rest.domain.dto.AdminUserDTO;
import com.springboot.rest.domain.port.api.UserServicePort;
import com.springboot.rest.infrastructure.entity.User;

@Service
@Transactional
public class CreateUser {

    private final UserServicePort userServicePort;

    public CreateUser(UserServicePort userServicePort) {
        this.userServicePort = userServicePort;
    }

    // actions
    public User createUser(AdminUserDTO userDTO) {
    	
    	// testing
//    	System.out.println("adminuserDto: "+userDTO);
    	
        return userServicePort.createUser(userDTO);
    }
    
    public void saveAccount(AdminUserDTO userDTO, String userLogin) {
    	userServicePort.saveAccount(userDTO, userLogin);
    }

}
