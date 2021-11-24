package com.springboot.rest.domain.port.api;

import java.util.List;
import java.util.Optional;

import com.springboot.rest.domain.dto.SampleEntityDTO;
import com.springboot.rest.infrastructure.entity.SampleEntity;

public interface SampleEntityServicePort {

    SampleEntity save(SampleEntityDTO sampleEntityDTO);

    SampleEntity update(Long id, SampleEntityDTO sampleEntityDTO);

    boolean existsById(Long id);

    List<SampleEntity> findAll();

    Optional<SampleEntity> findById(Long id);

    boolean deleteById(Long id);

    Optional<SampleEntity> patch(Long id, SampleEntityDTO sampleEntityDTO);

	



}
