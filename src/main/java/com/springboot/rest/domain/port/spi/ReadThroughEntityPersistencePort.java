package com.springboot.rest.domain.port.spi;

import java.util.List;
import java.util.Optional;

import com.springboot.rest.domain.dto.ReadThroughEntityDTO;
import com.springboot.rest.infrastructure.entity.ReadThroughEntity;


public interface ReadThroughEntityPersistencePort {
    List<ReadThroughEntity> findAll();

    Optional<ReadThroughEntity> findById(Long id);

    ReadThroughEntity save(ReadThroughEntityDTO readThroughEntityDTO);

    boolean existsById(Long id);

    boolean deleteById(Long id);
}