package com.springboot.rest.usecase.user;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.rest.domain.dto.AdminUserDTO;
import com.springboot.rest.domain.port.api.UserServicePort;
import com.springboot.rest.infrastructure.entity.User;

@Service
@Transactional
public class RegisterUser {

    private final UserServicePort userServicePort;

    public RegisterUser(UserServicePort userServicePort) {
        this.userServicePort = userServicePort;
    }

    // actions
    public User registerUser(AdminUserDTO userDTO, String password) {
    	return userServicePort.registerUser(userDTO, password);
    }
    
    public Optional<User> activateRegistration(String key) {
    	return userServicePort.activateRegistration(key);
    }

}
