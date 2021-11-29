package com.springboot.rest.domain.port.spi;

import com.springboot.rest.domain.dto.WriteThroughCacheEntityDTO;
import com.springboot.rest.infrastructure.entity.SampleEntity;

import java.util.List;
import java.util.Optional;


public interface WriteThroughCacheEntityPersistencePort {
    List<SampleEntity> findAll();

    Optional<SampleEntity> findById(Long id);

    SampleEntity save(WriteThroughCacheEntityDTO sampleEntityDTO);

    boolean existsById(Long id);

    boolean deleteById(Long id);
}