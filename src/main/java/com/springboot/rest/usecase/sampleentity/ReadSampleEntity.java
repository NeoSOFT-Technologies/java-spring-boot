package com.springboot.rest.usecase.sampleentity;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.rest.domain.port.api.SampleEntityServicePort;
import com.springboot.rest.infrastructure.entity.SampleEntity;

@Service
@Transactional
public class ReadSampleEntity {

    private final SampleEntityServicePort sampleEntityServicePort;

    public ReadSampleEntity(SampleEntityServicePort sampleEntityServicePort) {
        this.sampleEntityServicePort = sampleEntityServicePort;
    }

    public List<SampleEntity> findAll() {
        return sampleEntityServicePort.findAll();
    }

    public Optional<SampleEntity> findById(Long id) {
        return sampleEntityServicePort.findById(id);
    }

}
