package com.springboot.rest.domain.port.spi;

import com.springboot.rest.domain.dto.WriteThroughCacheEntityDTO;
import com.springboot.rest.infrastructure.entity.WriteThroughCacheEntity;

import java.util.List;
import java.util.Optional;


public interface WriteThroughCacheEntityPersistencePort {
    List<WriteThroughCacheEntity> findAll();

    Optional<WriteThroughCacheEntity> findById(Long id);

    WriteThroughCacheEntity save(WriteThroughCacheEntityDTO sampleEntityDTO);

    boolean existsById(Long id);

    boolean deleteById(Long id);
}