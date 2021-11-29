package com.springboot.rest.domain.port.api;

import java.util.List;
import java.util.Optional;

import com.springboot.rest.domain.dto.WriteThroughCacheEntityDTO;
import com.springboot.rest.infrastructure.entity.SampleEntity;

public interface WriteThroughCacheEntityServicePort {

    SampleEntity save(WriteThroughCacheEntityDTO sampleEntityDTO);

    SampleEntity update(Long id, WriteThroughCacheEntityDTO sampleEntityDTO);

    boolean existsById(Long id);

    List<SampleEntity> findAll();

    Optional<SampleEntity> findById(Long id);

    boolean deleteById(Long id);

    Optional<SampleEntity> patch(Long id, WriteThroughCacheEntityDTO sampleEntityDTO);

	



}
