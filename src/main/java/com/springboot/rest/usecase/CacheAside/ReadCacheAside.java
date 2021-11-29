package com.springboot.rest.usecase.CacheAside;

import com.springboot.rest.domain.port.api.CacheAsideServicePort;
import com.springboot.rest.infrastructure.entity.CacheAsideEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReadCacheAside {

    private final CacheAsideServicePort sampleEntityServicePort;

    public ReadCacheAside(CacheAsideServicePort sampleEntityServicePort) {
        this.sampleEntityServicePort = sampleEntityServicePort;
    }

    public List<CacheAsideEntity> findAll() {
        return sampleEntityServicePort.findAll();
    }

    public Optional<CacheAsideEntity> findById(Long id) {
        return sampleEntityServicePort.findById(id);
    }

}
