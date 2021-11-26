package com.springboot.rest.usecase.sampleentity2;

import com.springboot.rest.domain.port.api.SampleEntity2ServicePort;
import com.springboot.rest.infrastructure.entity.SampleEntity2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReadSampleEntity2 {

    private final SampleEntity2ServicePort sampleEntity2ServicePort;

    public ReadSampleEntity2(SampleEntity2ServicePort sampleEntity2ServicePort) {
        this.sampleEntity2ServicePort = sampleEntity2ServicePort;
    }

    public List<SampleEntity2> findAll() {
        return sampleEntity2ServicePort.findAll();
    }

    public Optional<SampleEntity2> findById(Long id) {
        return sampleEntity2ServicePort.findById(id);
    }

}
