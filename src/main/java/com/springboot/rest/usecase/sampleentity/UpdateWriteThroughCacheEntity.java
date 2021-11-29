package com.springboot.rest.usecase.sampleentity;

import com.springboot.rest.domain.dto.WriteThroughCacheEntityDTO;
import com.springboot.rest.domain.port.api.WriteThroughCacheEntityServicePort;
import com.springboot.rest.infrastructure.entity.SampleEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UpdateWriteThroughCacheEntity {

    private final WriteThroughCacheEntityServicePort sampleEntityServicePort;

    public UpdateWriteThroughCacheEntity(WriteThroughCacheEntityServicePort sampleEntityServicePort) {
        this.sampleEntityServicePort = sampleEntityServicePort;
    }

    public SampleEntity update(Long id, WriteThroughCacheEntityDTO sampleEntityDTO) {
        return sampleEntityServicePort.update(id, sampleEntityDTO);
    }

    public Optional<SampleEntity> patch(Long id, WriteThroughCacheEntityDTO sampleEntityDTO) {
        return sampleEntityServicePort.patch(id, sampleEntityDTO);
    }


}
