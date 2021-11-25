package com.springboot.rest.usecase.sampleentity2;

import com.springboot.rest.domain.dto.SampleEntity2DTO;
import com.springboot.rest.domain.port.api.SampleEntity2ServicePort;
import com.springboot.rest.infrastructure.entity.SampleEntity2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UpdateSampleEntity2 {

    private final SampleEntity2ServicePort sampleEntity2ServicePort;

    public UpdateSampleEntity2(SampleEntity2ServicePort sampleEntity2ServicePort) {
        this.sampleEntity2ServicePort = sampleEntity2ServicePort;
    }

    public SampleEntity2 update(Long id, SampleEntity2DTO sampleEntity2DTO) {
        return sampleEntity2ServicePort.update(id, sampleEntity2DTO);
    }

    public Optional<SampleEntity2> patch(Long id, SampleEntity2DTO sampleEntity2DTO) {
        return sampleEntity2ServicePort.patch(id, sampleEntity2DTO);
    }


}
