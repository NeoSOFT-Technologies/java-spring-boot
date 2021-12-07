package com.springboot.rest.domain.port.spi;

import com.springboot.rest.domain.dto.WriteBackCacheEntityDTO;
import com.springboot.rest.infrastructure.entity.SampleEntity;

import java.util.List;
import java.util.Optional;


public interface WriteBackCacheEntityPersistencePort {
    List<SampleEntity> findAll();

    Optional<SampleEntity> findById(Long id);

    SampleEntity save(WriteBackCacheEntityDTO sampleEntityDTO);

    boolean existsById(Long id);

    boolean deleteById(Long id);
}