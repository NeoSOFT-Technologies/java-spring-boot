package com.springboot.rest.usecase.sampleentity;

import com.springboot.rest.domain.dto.WriteThroughCacheEntityDTO;
import com.springboot.rest.domain.port.api.WriteThroughCacheEntityServicePort;
import com.springboot.rest.infrastructure.entity.WriteThroughCacheEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UpdateSampleEntity {

    private final WriteThroughCacheEntityServicePort sampleEntityServicePort;

    public UpdateSampleEntity(WriteThroughCacheEntityServicePort sampleEntityServicePort) {
        this.sampleEntityServicePort = sampleEntityServicePort;
    }

    public WriteThroughCacheEntity update(Long id, WriteThroughCacheEntityDTO sampleEntityDTO) {
        return sampleEntityServicePort.update(id, sampleEntityDTO);
    }

    public Optional<WriteThroughCacheEntity> patch(Long id, WriteThroughCacheEntityDTO sampleEntityDTO) {
        return sampleEntityServicePort.patch(id, sampleEntityDTO);
    }


}
