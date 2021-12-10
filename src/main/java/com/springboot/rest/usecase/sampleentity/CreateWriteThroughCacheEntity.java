package com.springboot.rest.usecase.sampleentity;

import com.springboot.rest.domain.dto.WriteBackCacheEntityDTO;
import com.springboot.rest.domain.port.api.WriteBackCacheEntityServicePort;
import com.springboot.rest.infrastructure.entity.SampleEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CreateWriteThroughCacheEntity {

    private final WriteBackCacheEntityServicePort sampleEntityServicePort;

    public CreateWriteThroughCacheEntity(WriteBackCacheEntityServicePort sampleEntityServicePort) {
        this.sampleEntityServicePort = sampleEntityServicePort;
    }

    public SampleEntity save(WriteBackCacheEntityDTO sampleEntityDTO) {
        return sampleEntityServicePort.save(sampleEntityDTO);
    }

}
