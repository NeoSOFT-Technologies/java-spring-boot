package com.springboot.rest.usecase.authority;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.rest.domain.port.api.AuthorityServicePort;

@Service
@Transactional
public class ReadAuthority {

    private final AuthorityServicePort authorityServicePort;

    public ReadAuthority(AuthorityServicePort authorityServicePort) {
        this.authorityServicePort = authorityServicePort;
    }

    public List<String> getAuthorities() {
    	return authorityServicePort.getAuthorities();
    }

}
