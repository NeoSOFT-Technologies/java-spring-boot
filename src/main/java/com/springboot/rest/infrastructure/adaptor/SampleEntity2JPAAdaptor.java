package com.springboot.rest.infrastructure.adaptor;

import com.springboot.rest.domain.dto.SampleEntity2DTO;
import com.springboot.rest.domain.port.spi.SampleEntity2PersistencePort;
import com.springboot.rest.infrastructure.entity.SampleEntity2;
import com.springboot.rest.infrastructure.repository.SampleEntity2Repository;
import com.springboot.rest.mapper.SampleEntity2Mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SampleEntity2JPAAdaptor implements SampleEntity2PersistencePort {

    @Autowired
    private final SampleEntity2Repository sampleEntity2Repository;

    private final SampleEntity2Mapper sampleEntity2Mapper;
    
    public SampleEntity2JPAAdaptor(SampleEntity2Repository sampleEntity2Repository, SampleEntity2Mapper sampleEntity2Mapper) {
        this.sampleEntity2Repository = sampleEntity2Repository;
        this.sampleEntity2Mapper = sampleEntity2Mapper;
    }

    public List<SampleEntity2> findAll() {
        return sampleEntity2Repository.findAll();
    }

    @Override
    public Optional<SampleEntity2> findById(Long id) {
        return sampleEntity2Repository.findById(id);
    }

    public SampleEntity2 save(SampleEntity2DTO sampleEntity2DTO) {

        // SampleEntity2DTO to SampleEntity2 conversion
    	SampleEntity2 sampleEntity2 = sampleEntity2Mapper.dtoToEntity(sampleEntity2DTO);
        return sampleEntity2Repository.save(sampleEntity2);
    }

    @Override
    public boolean existsById(Long id) {
        return sampleEntity2Repository.existsById(id);
    }

    @Override
    public boolean deleteById(Long id) {
        sampleEntity2Repository.deleteById(id);
        return true;
    }

}