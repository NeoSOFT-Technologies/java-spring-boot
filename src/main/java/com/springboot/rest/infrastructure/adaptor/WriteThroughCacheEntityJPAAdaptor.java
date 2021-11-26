package com.springboot.rest.infrastructure.adaptor;

import com.springboot.rest.domain.dto.WriteThroughCacheEntityDTO;
import com.springboot.rest.domain.port.spi.WriteThroughCacheEntityPersistencePort;
import com.springboot.rest.infrastructure.entity.WriteThroughCacheEntity;
import com.springboot.rest.infrastructure.repository.WriteThroughCacheEntityRepository;
import com.springboot.rest.mapper.WriteThroughCacheEntityMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class WriteThroughCacheEntityJPAAdaptor implements WriteThroughCacheEntityPersistencePort {

    @Autowired
    private final WriteThroughCacheEntityRepository sampleEntityRepository;

    private final WriteThroughCacheEntityMapper sampleEntityMapper;
    
    public WriteThroughCacheEntityJPAAdaptor(WriteThroughCacheEntityRepository sampleEntityRepository, WriteThroughCacheEntityMapper sampleEntityMapper) {
        this.sampleEntityRepository = sampleEntityRepository;
        this.sampleEntityMapper = sampleEntityMapper;
    }

    public List<WriteThroughCacheEntity> findAll() {
        return sampleEntityRepository.findAll();
    }

    @Override
    public Optional<WriteThroughCacheEntity> findById(Long id) {
    	
        return sampleEntityRepository.findById(id);
    }

    public WriteThroughCacheEntity save(WriteThroughCacheEntityDTO sampleEntityDTO) {

        // SampleEntityDTO to SampleEntity conversion
    	WriteThroughCacheEntity sampleEntity = sampleEntityMapper.dtoToEntity(sampleEntityDTO);
        return sampleEntityRepository.save(sampleEntity);
    }

    @Override
    public boolean existsById(Long id) {
        return sampleEntityRepository.existsById(id);
    }

    @Override
    public boolean deleteById(Long id) {
        sampleEntityRepository.deleteById(id);
        return true;
    }



}