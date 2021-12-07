package com.springboot.rest.domain.port.api;

import java.util.List;
import java.util.Optional;

import com.springboot.rest.domain.dto.WriteBackCacheEntityDTO;
import com.springboot.rest.infrastructure.entity.SampleEntity;

public interface WriteBackCacheEntityServicePort {

    SampleEntity save(WriteBackCacheEntityDTO sampleEntityDTO);

    SampleEntity update(Long id, WriteBackCacheEntityDTO sampleEntityDTO);

    boolean existsById(Long id);

    List<SampleEntity> findAll();

    Optional<SampleEntity> findById(Long id);

    boolean deleteById(Long id);

    Optional<SampleEntity> patch(Long id, WriteBackCacheEntityDTO sampleEntityDTO);

	



}
