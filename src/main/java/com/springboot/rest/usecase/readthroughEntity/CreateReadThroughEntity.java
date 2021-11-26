package com.springboot.rest.usecase.readthroughEntity;

import com.springboot.rest.domain.dto.ReadThroughEntityDTO;
import com.springboot.rest.domain.port.api.SampleEntity2ServicePort;
import com.springboot.rest.infrastructure.entity.ReadThroughEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CreateReadThroughEntity {

    private final SampleEntity2ServicePort sampleEntity2ServicePort;

    public CreateReadThroughEntity(SampleEntity2ServicePort sampleEntity2ServicePort) {
        this.sampleEntity2ServicePort = sampleEntity2ServicePort;
    }

    public ReadThroughEntity save(ReadThroughEntityDTO readThroughEntityDTO) {
        return sampleEntity2ServicePort.save(readThroughEntityDTO);
    }

}
