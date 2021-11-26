package com.springboot.rest.usecase.sampleentity;

import com.springboot.rest.domain.dto.WriteThroughCacheEntityDTO;
import com.springboot.rest.domain.port.api.WriteThroughCacheEntityServicePort;
import com.springboot.rest.infrastructure.entity.WriteThroughCacheEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CreateSampleEntity {

    private final WriteThroughCacheEntityServicePort sampleEntityServicePort;

    public CreateSampleEntity(WriteThroughCacheEntityServicePort sampleEntityServicePort) {
        this.sampleEntityServicePort = sampleEntityServicePort;
    }

    public WriteThroughCacheEntity save(WriteThroughCacheEntityDTO sampleEntityDTO) {
        return sampleEntityServicePort.save(sampleEntityDTO);
    }

}
