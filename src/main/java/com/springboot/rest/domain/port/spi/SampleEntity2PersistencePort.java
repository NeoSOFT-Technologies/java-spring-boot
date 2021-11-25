package com.springboot.rest.domain.port.spi;

import java.util.List;
import java.util.Optional;

import com.springboot.rest.domain.dto.SampleEntity2DTO;
import com.springboot.rest.infrastructure.entity.SampleEntity2;


public interface SampleEntity2PersistencePort {
    List<SampleEntity2> findAll();

    Optional<SampleEntity2> findById(Long id);

    SampleEntity2 save(SampleEntity2DTO sampleEntity2DTO);

    boolean existsById(Long id);

    boolean deleteById(Long id);
}