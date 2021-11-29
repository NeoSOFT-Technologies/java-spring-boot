package com.springboot.rest.usecase.CacheAside;

import com.springboot.rest.domain.port.api.CacheAsideServicePort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DeleteCacheAside {

    private final CacheAsideServicePort sampleEntityServicePort;

    public DeleteCacheAside(CacheAsideServicePort sampleEntityServicePort) {
        this.sampleEntityServicePort = sampleEntityServicePort;
    }

    public void deleteById(Long id) {
        sampleEntityServicePort.deleteById(id);
    }
}
