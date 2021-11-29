package com.springboot.rest.domain.port.api;

import com.springboot.rest.domain.dto.CacheAsideDTO;
import com.springboot.rest.infrastructure.entity.CacheAsideEntity;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

public interface CacheAsideServicePort {

    CacheAsideEntity save(CacheAsideDTO sampleEntityDTO);

    CacheAsideEntity update(Long id, CacheAsideDTO sampleEntityDTO);

    boolean existsById(Long id);

    List<CacheAsideEntity> findAll();

    Optional<CacheAsideEntity> findById(Long id);

    boolean deleteById(Long id);

    Optional<CacheAsideEntity> patch(Long id, CacheAsideDTO sampleEntityDTO);



}
