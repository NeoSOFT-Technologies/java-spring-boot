package com.springboot.rest.usecase.user;

import com.springboot.rest.domain.dto.AdminUserDTO;
import com.springboot.rest.domain.dto.UserDTO;
import com.springboot.rest.domain.port.api.UserServicePort;
import com.springboot.rest.infrastructure.entity.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReadUser {

    private final UserServicePort userServicePort;

    public ReadUser(UserServicePort userServicePort) {
        this.userServicePort = userServicePort;
    }

    // actions
    public Page<AdminUserDTO> getAllManagedUsers(Pageable pageable) {
    	return userServicePort.getAllManagedUsers(pageable);
    }
    
    public Page<UserDTO> findAllByIdNotNullAndActivatedIsTrue(Pageable pageable) {
    	return userServicePort.findAllByIdNotNullAndActivatedIsTrue(pageable);
    }
    
    public Optional<User> getUserWithAuthoritiesByLogin(String login) {
    	return userServicePort.getUserWithAuthoritiesByLogin(login);
    }
    
    public Optional<User> getUserWithAuthorities() {
    	return userServicePort.getUserWithAuthorities();
    }
    
    public Page<UserDTO> getAllPublicUsers(Pageable pageable) {
    	return userServicePort.getAllPublicUsers(pageable);
    }
    
}
