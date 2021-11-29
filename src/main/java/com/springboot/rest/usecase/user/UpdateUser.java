package com.springboot.rest.usecase.user;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.rest.domain.dto.AdminUserDTO;
import com.springboot.rest.domain.port.api.UserServicePort;
import com.springboot.rest.infrastructure.entity.User;

@Service
@Transactional
public class UpdateUser {

    private final UserServicePort userServicePort;

    public UpdateUser(UserServicePort userServicePort) {
        this.userServicePort = userServicePort;
    }
    
    // actions
    public void updateUser(String firstName, String lastName, String email, String langKey, String imageUrl) {
        userServicePort.updateUser(firstName, lastName, email, langKey, imageUrl);
    }
    
    public Optional<AdminUserDTO> updateAdminUser(AdminUserDTO adminUserDTO) {
        return userServicePort.updateUser(adminUserDTO);
    }

    public void changePassword(String currentClearTextPassword, String newPassword) {
    	userServicePort.changePassword(currentClearTextPassword, newPassword);
    }
    
    public Optional<User> requestPasswordReset(String mail) {
    	return userServicePort.requestPasswordReset(mail);
    }
    
    public Optional<User> completePasswordReset(String newPassword, String key) {
    	return userServicePort.completePasswordReset(newPassword, key);
    }

}
