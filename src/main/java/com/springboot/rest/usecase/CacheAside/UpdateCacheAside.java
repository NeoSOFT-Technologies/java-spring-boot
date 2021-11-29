package com.springboot.rest.usecase.CacheAside;

import com.springboot.rest.domain.dto.CacheAsideDTO;
import com.springboot.rest.domain.port.api.CacheAsideServicePort;
import com.springboot.rest.infrastructure.entity.CacheAsideEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UpdateCacheAside {

    private final CacheAsideServicePort sampleEntityServicePort;

    public UpdateCacheAside(CacheAsideServicePort sampleEntityServicePort) {
        this.sampleEntityServicePort = sampleEntityServicePort;
    }

    public CacheAsideEntity update(Long id, CacheAsideDTO sampleEntityDTO) {
        return sampleEntityServicePort.update(id, sampleEntityDTO);
    }

    public Optional<CacheAsideEntity> patch(Long id, CacheAsideDTO sampleEntityDTO) {
        return sampleEntityServicePort.patch(id, sampleEntityDTO);
    }


}
