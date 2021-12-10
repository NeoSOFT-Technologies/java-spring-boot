package com.springboot.rest.usecase.sampleentity;

import com.springboot.rest.domain.port.api.WriteBackCacheEntityServicePort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DeleteWriteThroughCacheEntity {

    private final WriteBackCacheEntityServicePort sampleEntityServicePort;

    public DeleteWriteThroughCacheEntity(WriteBackCacheEntityServicePort sampleEntityServicePort) {
        this.sampleEntityServicePort = sampleEntityServicePort;
    }

    public void deleteById(Long id) {
        sampleEntityServicePort.deleteById(id);
    }
}
