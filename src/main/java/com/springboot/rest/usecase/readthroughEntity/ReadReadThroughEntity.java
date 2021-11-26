package com.springboot.rest.usecase.readthroughEntity;

import com.springboot.rest.domain.port.api.SampleEntity2ServicePort;
import com.springboot.rest.infrastructure.entity.ReadThroughEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReadReadThroughEntity {

    private final SampleEntity2ServicePort sampleEntity2ServicePort;

    public ReadReadThroughEntity(SampleEntity2ServicePort sampleEntity2ServicePort) {
        this.sampleEntity2ServicePort = sampleEntity2ServicePort;
    }

    public List<ReadThroughEntity> findAll() {
        return sampleEntity2ServicePort.findAll();
    }

    public Optional<ReadThroughEntity> findById(Long id) {
        return sampleEntity2ServicePort.findById(id);
    }

}
