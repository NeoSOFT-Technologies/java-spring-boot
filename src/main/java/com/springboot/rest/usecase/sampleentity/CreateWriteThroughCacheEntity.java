package com.springboot.rest.usecase.sampleentity;

import com.springboot.rest.domain.dto.WriteThroughCacheEntityDTO;
import com.springboot.rest.domain.port.api.WriteThroughCacheEntityServicePort;
import com.springboot.rest.infrastructure.entity.SampleEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CreateWriteThroughCacheEntity {

    private final WriteThroughCacheEntityServicePort sampleEntityServicePort;

    public CreateWriteThroughCacheEntity(WriteThroughCacheEntityServicePort sampleEntityServicePort) {
        this.sampleEntityServicePort = sampleEntityServicePort;
    }

    public SampleEntity save(WriteThroughCacheEntityDTO sampleEntityDTO) {
        return sampleEntityServicePort.save(sampleEntityDTO);
    }

}
