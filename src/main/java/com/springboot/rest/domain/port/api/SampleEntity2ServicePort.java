package com.springboot.rest.domain.port.api;

import java.util.List;
import java.util.Optional;

import com.springboot.rest.domain.dto.ReadThroughEntityDTO;
import com.springboot.rest.infrastructure.entity.ReadThroughEntity;

public interface SampleEntity2ServicePort {

    ReadThroughEntity save(ReadThroughEntityDTO readThroughEntityDTO);

    ReadThroughEntity update(Long id, ReadThroughEntityDTO readThroughEntityDTO);

    boolean existsById(Long id);

    List<ReadThroughEntity> findAll();

    Optional<ReadThroughEntity> findById(Long id);

    boolean deleteById(Long id);

    Optional<ReadThroughEntity> patch(Long id, ReadThroughEntityDTO readThroughEntityDTO);



}
