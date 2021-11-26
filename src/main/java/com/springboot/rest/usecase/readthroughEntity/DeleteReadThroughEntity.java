package com.springboot.rest.usecase.sampleentity2;

import com.springboot.rest.domain.port.api.SampleEntity2ServicePort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DeleteSampleEntity2 {

    private final SampleEntity2ServicePort sampleEntity2ServicePort;

    public DeleteSampleEntity2(SampleEntity2ServicePort sampleEntity2ServicePort) {
        this.sampleEntity2ServicePort = sampleEntity2ServicePort;
    }

    public void deleteById(Long id) {
        sampleEntity2ServicePort.deleteById(id);
    }
}
