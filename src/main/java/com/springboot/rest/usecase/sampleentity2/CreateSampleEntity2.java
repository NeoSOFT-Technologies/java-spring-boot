package com.springboot.rest.usecase.sampleentity2;

import com.springboot.rest.domain.dto.SampleEntity2DTO;
import com.springboot.rest.domain.port.api.SampleEntity2ServicePort;
import com.springboot.rest.infrastructure.entity.SampleEntity2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CreateSampleEntity2 {

    private final SampleEntity2ServicePort sampleEntity2ServicePort;

    public CreateSampleEntity2(SampleEntity2ServicePort sampleEntity2ServicePort) {
        this.sampleEntity2ServicePort = sampleEntity2ServicePort;
    }

    public SampleEntity2 save(SampleEntity2DTO sampleEntity2DTO) {
        return sampleEntity2ServicePort.save(sampleEntity2DTO);
    }

}
