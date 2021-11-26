package com.springboot.rest.infrastructure.adaptor;

import com.springboot.rest.domain.dto.ReadThroughEntityDTO;
import com.springboot.rest.domain.port.spi.ReadThroughEntityPersistencePort;
import com.springboot.rest.infrastructure.entity.ReadThroughEntity;
import com.springboot.rest.infrastructure.repository.ReadThroughEntityRepository;
import com.springboot.rest.mapper.ReadThroughEntityMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReadThroughEntityJPAAdaptor implements ReadThroughEntityPersistencePort {

    @Autowired
    private final ReadThroughEntityRepository readThroughEntityRepository;

    private final ReadThroughEntityMapper readThroughEntityMapper;
    
    public ReadThroughEntityJPAAdaptor(ReadThroughEntityRepository readThroughEntityRepository, ReadThroughEntityMapper readThroughEntityMapper) {
        this.readThroughEntityRepository = readThroughEntityRepository;
        this.readThroughEntityMapper = readThroughEntityMapper;
    }

    public List<ReadThroughEntity> findAll() {
        return readThroughEntityRepository.findAll();
    }

    @Override
    public Optional<ReadThroughEntity> findById(Long id) {
        return readThroughEntityRepository.findById(id);
    }

    public ReadThroughEntity save(ReadThroughEntityDTO readThroughEntityDTO) {

        // ReadThroughEntityDTO to ReadThroughEntity conversion
    	ReadThroughEntity readThroughEntity = readThroughEntityMapper.dtoToEntity(readThroughEntityDTO);
        return readThroughEntityRepository.save(readThroughEntity);
    }

    @Override
    public boolean existsById(Long id) {
        return readThroughEntityRepository.existsById(id);
    }

    @Override
    public boolean deleteById(Long id) {
        readThroughEntityRepository.deleteById(id);
        return true;
    }

}