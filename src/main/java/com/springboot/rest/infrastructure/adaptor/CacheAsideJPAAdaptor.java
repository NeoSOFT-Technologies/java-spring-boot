package com.springboot.rest.infrastructure.adaptor;

import com.springboot.rest.domain.dto.CacheAsideDTO;
import com.springboot.rest.domain.port.spi.CacheAsidePersistencePort;
import com.springboot.rest.infrastructure.entity.CacheAsideEntity;
import com.springboot.rest.infrastructure.repository.CacheAsideRepository;
import com.springboot.rest.mapper.CacheAsideMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CacheAsideJPAAdaptor implements CacheAsidePersistencePort {

    @Autowired
    private final CacheAsideRepository sampleEntityRepository;

    private final CacheAsideMapper sampleEntityMapper;
    
    public CacheAsideJPAAdaptor(CacheAsideRepository sampleEntityRepository, CacheAsideMapper sampleEntityMapper) {
        this.sampleEntityRepository = sampleEntityRepository;
        this.sampleEntityMapper = sampleEntityMapper;
    }

    public List<CacheAsideEntity> findAll() {
        return sampleEntityRepository.findAll();
    }

    @Override
    @Cacheable(value = "sampleEntity",key = "#id")
    public Optional<CacheAsideEntity> findById(Long id) {
        return sampleEntityRepository.findById(id);
    }

    //@CachePut(value = "sampleEntity",key = "#sampleEntity.id")
    public CacheAsideEntity save(CacheAsideDTO sampleEntityDTO) {

        // SampleEntityDTO to SampleEntity conversion
    	CacheAsideEntity sampleEntity = sampleEntityMapper.dtoToEntity(sampleEntityDTO);
        return sampleEntityRepository.save(sampleEntity);
    }

    @Override
    public boolean existsById(Long id) {
        return sampleEntityRepository.existsById(id);
    }

    @Override
    @CacheEvict(value = "sampleEntity",allEntries = false,key = "#id")
    public boolean deleteById(Long id) {
        sampleEntityRepository.deleteById(id);
		return true;
    }

}