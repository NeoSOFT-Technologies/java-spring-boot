package com.springboot.rest.domain.port.api;

import java.util.List;
import java.util.Optional;

import com.springboot.rest.domain.dto.WriteThroughCacheEntityDTO;
import com.springboot.rest.infrastructure.entity.WriteThroughCacheEntity;

public interface WriteThroughCacheEntityServicePort {

    WriteThroughCacheEntity save(WriteThroughCacheEntityDTO sampleEntityDTO);

    WriteThroughCacheEntity update(Long id, WriteThroughCacheEntityDTO sampleEntityDTO);

    boolean existsById(Long id);

    List<WriteThroughCacheEntity> findAll();

    Optional<WriteThroughCacheEntity> findById(Long id);

    boolean deleteById(Long id);

    Optional<WriteThroughCacheEntity> patch(Long id, WriteThroughCacheEntityDTO sampleEntityDTO);

	



}
