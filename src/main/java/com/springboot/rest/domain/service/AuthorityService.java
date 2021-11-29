package com.springboot.rest.domain.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.rest.domain.port.api.AuthorityServicePort;
import com.springboot.rest.domain.port.spi.AuthorityPersistencePort;
import com.springboot.rest.infrastructure.entity.Authority;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class AuthorityService implements AuthorityServicePort {

    private final AuthorityPersistencePort authorityPersistencePort;

    public AuthorityService(AuthorityPersistencePort authorityPersistencePort) {
        this.authorityPersistencePort = authorityPersistencePort;

    }

    /**
     * Gets a list of all the authorities.
     * 
     * @return a list of all the authorities.
     */
    @Transactional(readOnly = true)
    @Override
    public List<String> getAuthorities() {
        return authorityPersistencePort.findAll().stream().map(Authority::getName).collect(Collectors.toList());
    }


}
