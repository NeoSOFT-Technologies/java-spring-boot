package com.springboot.rest.domain.port.spi;

import com.springboot.rest.domain.dto.CacheAsideDTO;
import com.springboot.rest.infrastructure.entity.CacheAsideEntity;

import java.util.List;
import java.util.Optional;


public interface CacheAsidePersistencePort {
    List<CacheAsideEntity> findAll();

    Optional<CacheAsideEntity> findById(Long id);

    CacheAsideEntity save(CacheAsideDTO sampleEntityDTO);

    boolean existsById(Long id);

    boolean deleteById(Long id);
}