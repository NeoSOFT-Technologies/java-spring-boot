package com.springboot.rest.usecase.sampleentity;

import com.springboot.rest.domain.dto.WriteBackCacheEntityDTO;
import com.springboot.rest.domain.port.api.WriteBackCacheEntityServicePort;
import com.springboot.rest.infrastructure.entity.SampleEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UpdateWriteThroughCacheEntity {

    private final WriteBackCacheEntityServicePort sampleEntityServicePort;

    public UpdateWriteThroughCacheEntity(WriteBackCacheEntityServicePort sampleEntityServicePort) {
        this.sampleEntityServicePort = sampleEntityServicePort;
    }

    public SampleEntity update(Long id, WriteBackCacheEntityDTO sampleEntityDTO) {
        return sampleEntityServicePort.update(id, sampleEntityDTO);
    }

    public Optional<SampleEntity> patch(Long id, WriteBackCacheEntityDTO sampleEntityDTO) {
        return sampleEntityServicePort.patch(id, sampleEntityDTO);
    }


}
