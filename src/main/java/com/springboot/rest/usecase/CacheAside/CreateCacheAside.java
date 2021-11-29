package com.springboot.rest.usecase.CacheAside;

import com.springboot.rest.domain.dto.CacheAsideDTO;
import com.springboot.rest.domain.port.api.CacheAsideServicePort;
import com.springboot.rest.infrastructure.entity.CacheAsideEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CreateCacheAside {

    private final CacheAsideServicePort sampleEntityServicePort;

    public CreateCacheAside(CacheAsideServicePort sampleEntityServicePort) {
        this.sampleEntityServicePort = sampleEntityServicePort;
    }

    public CacheAsideEntity save(CacheAsideDTO sampleEntityDTO) {
        return sampleEntityServicePort.save(sampleEntityDTO);
    }

}
