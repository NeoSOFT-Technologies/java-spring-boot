package com.springboot.rest.usecase.sampleentity;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.rest.domain.port.api.WriteThroughCacheEntityServicePort;
import com.springboot.rest.infrastructure.entity.WriteThroughCacheEntity;

@Service
@Transactional
public class ReadSampleEntity {

    private final WriteThroughCacheEntityServicePort sampleEntityServicePort;

    public ReadSampleEntity(WriteThroughCacheEntityServicePort sampleEntityServicePort) {
        this.sampleEntityServicePort = sampleEntityServicePort;
    }

    public List<WriteThroughCacheEntity> findAll() {
        return sampleEntityServicePort.findAll();
    }

    public Optional<WriteThroughCacheEntity> findById(Long id) {
        return sampleEntityServicePort.findById(id);
    }

}
