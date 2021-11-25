package com.springboot.rest.domain.port.api;

import java.util.List;
import java.util.Optional;

import com.springboot.rest.domain.dto.SampleEntity2DTO;
import com.springboot.rest.infrastructure.entity.SampleEntity2;

public interface SampleEntity2ServicePort {

    SampleEntity2 save(SampleEntity2DTO sampleEntity2DTO);

    SampleEntity2 update(Long id, SampleEntity2DTO sampleEntity2DTO);

    boolean existsById(Long id);

    List<SampleEntity2> findAll();

    Optional<SampleEntity2> findById(Long id);

    boolean deleteById(Long id);

    Optional<SampleEntity2> patch(Long id, SampleEntity2DTO sampleEntity2DTO);



}
