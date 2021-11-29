package com.springboot.rest.usecase.readthroughEntity;

import com.springboot.rest.domain.port.api.SampleEntity2ServicePort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DeleteReadThroughEntity {

    private final SampleEntity2ServicePort sampleEntity2ServicePort;

    public DeleteReadThroughEntity(SampleEntity2ServicePort sampleEntity2ServicePort) {
        this.sampleEntity2ServicePort = sampleEntity2ServicePort;
    }

    public void deleteById(Long id) {
        sampleEntity2ServicePort.deleteById(id);
    }
}
