package com.springboot.rest.usecase.sampleentity;

import com.springboot.rest.domain.port.api.WriteThroughCacheEntityServicePort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DeleteSampleEntity {

    private final WriteThroughCacheEntityServicePort sampleEntityServicePort;

    public DeleteSampleEntity(WriteThroughCacheEntityServicePort sampleEntityServicePort) {
        this.sampleEntityServicePort = sampleEntityServicePort;
    }

    public void deleteById(Long id) {
        sampleEntityServicePort.deleteById(id);
    }
}
