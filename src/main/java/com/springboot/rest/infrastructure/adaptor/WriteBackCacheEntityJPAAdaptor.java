package com.springboot.rest.infrastructure.adaptor;

import com.springboot.rest.domain.dto.WriteBackCacheEntityDTO;
import com.springboot.rest.domain.port.spi.WriteBackCacheEntityPersistencePort;
import com.springboot.rest.infrastructure.entity.SampleEntity;
import com.springboot.rest.infrastructure.repository.WriteBackCacheEntityRepository;
import com.springboot.rest.mapper.WriteBackCacheEntityMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class WriteBackCacheEntityJPAAdaptor implements WriteBackCacheEntityPersistencePort {

    @Autowired
    private final WriteBackCacheEntityRepository sampleEntityRepository;

    private final WriteBackCacheEntityMapper sampleEntityMapper;
    
    public WriteBackCacheEntityJPAAdaptor(WriteBackCacheEntityRepository sampleEntityRepository, WriteBackCacheEntityMapper sampleEntityMapper) {
        this.sampleEntityRepository = sampleEntityRepository;
        this.sampleEntityMapper = sampleEntityMapper;
    }

    public List<SampleEntity> findAll() {
        return sampleEntityRepository.findAll();
    }

    @Override
    public Optional<SampleEntity> findById(Long id) {
    	
        return sampleEntityRepository.findById(id);
    }

    public SampleEntity save(WriteBackCacheEntityDTO sampleEntityDTO) {

        // SampleEntityDTO to SampleEntity conversion
    	SampleEntity sampleEntity = sampleEntityMapper.dtoToEntity(sampleEntityDTO);
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