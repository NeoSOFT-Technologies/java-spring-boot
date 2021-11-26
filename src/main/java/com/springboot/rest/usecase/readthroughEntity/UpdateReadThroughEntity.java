package com.springboot.rest.usecase.readthroughEntity;

import com.springboot.rest.domain.dto.ReadThroughEntityDTO;
import com.springboot.rest.domain.port.api.SampleEntity2ServicePort;
import com.springboot.rest.infrastructure.entity.ReadThroughEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UpdateReadThroughEntity {

    private final SampleEntity2ServicePort sampleEntity2ServicePort;

    public UpdateReadThroughEntity(SampleEntity2ServicePort sampleEntity2ServicePort) {
        this.sampleEntity2ServicePort = sampleEntity2ServicePort;
    }

    public ReadThroughEntity update(Long id, ReadThroughEntityDTO readThroughEntityDTO) {
        return sampleEntity2ServicePort.update(id, readThroughEntityDTO);
    }

    public Optional<ReadThroughEntity> patch(Long id, ReadThroughEntityDTO readThroughEntityDTO) {
        return sampleEntity2ServicePort.patch(id, readThroughEntityDTO);
    }


}
