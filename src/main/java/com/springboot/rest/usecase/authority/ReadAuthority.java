package com.springboot.rest.usecase.authority;

import com.springboot.rest.domain.port.api.AuthorityServicePort;
import com.springboot.rest.domain.port.api.SampleEntity2ServicePort;
import com.springboot.rest.infrastructure.entity.SampleEntity2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
